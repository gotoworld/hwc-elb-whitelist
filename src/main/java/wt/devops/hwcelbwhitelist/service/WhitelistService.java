package wt.devops.hwcelbwhitelist.service;

import wt.devops.hwcelbwhitelist.controller.HuaweicloudContext;
import wt.devops.hwcelbwhitelist.pojo.WhitelistVO;

import java.util.List;

/**
 * Description
 *
 * @author uname.chen
 * @date 19-2-20
 */
public interface WhitelistService {

    /**
     *
     * @param context
     * @param isSwithOn
     */
    void updateWhitelist(HuaweicloudContext context,boolean isUpdateSwitch, boolean isSwithOn);


    /**
     *
     * @param context
     * @return
     */
    List<WhitelistVO> getWhitelist(HuaweicloudContext context);
}
