package wt.devops.hwcelbwhitelist.service;

import com.huawei.openstack4j.api.OSClient;
import com.huawei.openstack4j.core.transport.Config;
import com.huawei.openstack4j.model.network.ext.ListenerV2;
import com.huawei.openstack4j.model.network.ext.LoadBalancerV2;
import com.huawei.openstack4j.openstack.OSFactory;
import com.huawei.openstack4j.openstack.networking.domain.ext.ListItem;
import com.huawei.openstack4j.openstack.networking.domain.ext.NeutronWhitelist;
import com.huawei.openstack4j.openstack.networking.domain.ext.NeutronWhitelistUpdate;
import org.springframework.stereotype.Service;
import wt.devops.hwcelbwhitelist.controller.HuaweicloudContext;
import wt.devops.hwcelbwhitelist.pojo.WhitelistVO;

import java.util.*;

/**
 * Description
 *
 * @author uname.chen
 * @date 19-2-20
 */
@Service
public class WhitelistServiceImpl implements WhitelistService {


    /**
     *
     * @param context
     * @return
     */
    @Override
    public List<WhitelistVO> getWhitelist(HuaweicloudContext context){

        // 1. init huaweicloud SDK client
        OSClient.OSClientAKSK osClient = initialClient(context);



        // 2. compose whitelistVO with ELB
        Map<String, WhitelistVO> map = new HashMap<String, WhitelistVO>();


        List<? extends LoadBalancerV2> elbList = osClient.networking().lbaasV2().loadbalancer().list();
        if (elbList == null || elbList.isEmpty()) {
            return new ArrayList<WhitelistVO>();
        }

        for (LoadBalancerV2 lb : elbList) {
            List<ListItem>  listenerItems = lb.getListeners();
            if(listenerItems != null){
                for (ListItem listItem : listenerItems) {
                      map.put(listItem.getId(), new WhitelistVO(lb.getId(),lb.getName()));
                }
            }
        }

        //3. compose whitelistVO with Listener
        List<? extends ListenerV2> listenerList = osClient.networking().lbaasV2().listener().list();

        if (listenerList != null) {
            for (ListenerV2 listenerV2 : listenerList) {
                WhitelistVO vo = map.get(listenerV2.getId());
                vo.setListenerId(listenerV2.getId());
                vo.setListenerName(listenerV2.getName());

                vo.setId(listenerV2.getId()); //
            }
        }


        // 4. compose whitelistVO with Whitelist
        NeutronWhitelist.NeutronWhitelists res = osClient.networking().lbaasV2().lbWhitelist().list();
        List<NeutronWhitelist> whitelists  = res.getList();

        if(whitelists != null){
            for (NeutronWhitelist whitelist1 : whitelists) {
                WhitelistVO vo = map.get(whitelist1.getListenerId());
                vo.setWhitelist(whitelist1.getWhitelist());
                vo.setWhitelistId(whitelist1.getId());
                vo.setEnableWhitelist(whitelist1.isEnableWhitelist());

//                vo.setId(whitelist1.getId());
            }
        }


        //5. sorting
        List<WhitelistVO> list = new ArrayList<WhitelistVO>(map.values());
        Collections.sort(list,new Comparator<WhitelistVO>(){
            public int compare(WhitelistVO arg0, WhitelistVO arg1) {
                return arg0.getElbName().compareTo(arg1.getElbName());
            }
        });

        return list;
    }


    /**
     *
     * @param context
     * @param isSwithOn
     */
    @Override
    public void updateWhitelist(HuaweicloudContext context, boolean isUpdateSwitch, boolean isSwithOn) {

        String whitelist = context.getWhitelist();


        // 1. init huaweicloud SDK client
        OSClient.OSClientAKSK osClient = initialClient(context);



        // 2. fetch all ELB listener
        List<? extends ListenerV2> list = osClient.networking().lbaasV2().listener().list();

        if (list == null || list.isEmpty()) {
            return;
        }



        // 3. fetch all existing ELB whitelists
        NeutronWhitelist.NeutronWhitelists res = osClient.networking().lbaasV2().lbWhitelist().list();
        List<NeutronWhitelist> whitelists  = res.getList();

        Map<String,String> listenerIdSet = new HashMap<String,String>();
        if(whitelists != null){
            for (NeutronWhitelist whitelist1 : whitelists) {
                listenerIdSet.put(whitelist1.getListenerId(),whitelist1.getId());
            }

        }


        // 4. update or create whithlist and it's switch updated by #isSwithOn
        for (ListenerV2 listenerV2 : list) {
            NeutronWhitelist response = null;
            if (listenerIdSet != null  && listenerIdSet.containsKey(listenerV2.getId())) {//updateModel
                NeutronWhitelistUpdate updateModel = getNeutronWhitelistUpdate(isUpdateSwitch, isSwithOn, whitelist);

                response = osClient.networking().lbaasV2().lbWhitelist().update(updateModel, listenerIdSet.get(listenerV2.getId()));
            } else {// Create Model
                NeutronWhitelist model = getNeutronWhitelist(isUpdateSwitch, isSwithOn, whitelist, listenerV2);

                response = osClient.networking().lbaasV2().lbWhitelist().create(model);
            }
        }

    }


    /**
     *
     * @param isUpdateSwitch
     * @param isSwithOn
     * @param whitelist
     * @param listenerV2
     * @return
     */
    private NeutronWhitelist getNeutronWhitelist(boolean isUpdateSwitch, boolean isSwithOn, String whitelist, ListenerV2 listenerV2) {
        NeutronWhitelist model = null;
        if(isUpdateSwitch){
            model = NeutronWhitelist.builder().listenerId(listenerV2.getId()).whitelist(whitelist).enableWhitelist(isSwithOn).build();
        }else {
            model = NeutronWhitelist.builder().listenerId(listenerV2.getId()).whitelist(whitelist).build();
        }
        return model;
    }

    /**
     *
     * @param isUpdateSwitch
     * @param isSwithOn
     * @param whitelist
     * @return
     */
    private NeutronWhitelistUpdate getNeutronWhitelistUpdate(boolean isUpdateSwitch, boolean isSwithOn, String whitelist) {
        NeutronWhitelistUpdate updateModel = null;
        if(isUpdateSwitch){
            updateModel  = NeutronWhitelistUpdate.builder().whitelist(whitelist).enableWhitelist(isSwithOn).build();
        }else {
            updateModel  = NeutronWhitelistUpdate.builder().whitelist(whitelist).build();
        }
        return updateModel;
    }


    /**
     *
     * @param context
     * @return
     */
    private OSClient.OSClientAKSK initialClient(HuaweicloudContext context) {
        OSFactory.enableHttpLoggingFilter(true);

        Config config = Config.newConfig().withLanguage("zh-cn")
                .withSSLVerificationDisabled();

        return OSFactory.builderAKSK().withConfig(config).credentials(context.getAk(), context.getSk(),
                context.getRegion(), context.getProjectId(), context.getDomain()).authenticate();

    }
}
