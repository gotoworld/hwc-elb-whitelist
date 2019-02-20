 /*******************************************************************************
 * 	Copyright 2018 Huawei Technologies Co.,Ltd.                                         
 * 	                                                                                 
 * 	Licensed under the Apache License, Version 2.0 (the "License"); you may not      
 * 	use this file except in compliance with the License. You may obtain a copy of    
 * 	the License at                                                                   
 * 	                                                                                 
 * 	    http://www.apache.org/licenses/LICENSE-2.0                                   
 * 	                                                                                 
 * 	Unless required by applicable law or agreed to in writing, software              
 * 	distributed under the License is distributed on an "AS IS" BASIS, WITHOUT        
 * 	WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the         
 * 	License for the specific language governing permissions and limitations under    
 * 	the License.                                                                     
 *******************************************************************************/
 package wt.devops.hwcejbwhitelist;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.huawei.openstack4j.api.OSClient;
import com.huawei.openstack4j.core.transport.Config;
import com.huawei.openstack4j.model.common.Identifier;
import com.huawei.openstack4j.model.network.ext.ListenerV2;
import com.huawei.openstack4j.openstack.OSFactory;
import org.junit.Assert;
import org.junit.BeforeClass;


import com.huawei.openstack4j.openstack.networking.domain.ext.NeutronWhitelist;
import com.huawei.openstack4j.openstack.networking.domain.ext.NeutronWhitelist.NeutronWhitelists;
import com.huawei.openstack4j.openstack.networking.domain.ext.NeutronWhitelistUpdate;
import org.junit.Test;


 public class LbWhitelistV2Test  {

	private  static OSClient.OSClientV3 osclient = null;
	private  static OSClient.OSClientAKSK osClientAKSK = null;

	private static final String W_WHITELIST = "58.247.134.234,58.250.241.14,58.250.250.110,116.6.64.238,116.205.13.12,116.228.161.58,140.206.70.66,180.167.41.198,183.62.219.125,183.238.198.16,202.104.136.198,202.170.131.22,202.170.134.64,202.170.134.65,202.170.134.66,202.170.134.67,203.166.220.2,210.75.22.64,210.75.22.65,210.75.22.66,210.75.22.67,218.13.10.130,218.17.161.132,219.134.64.76,221.235.37.138,223.255.43.11";


	@BeforeClass
	public static void initialClient() {
		// add override endpoint


		String region = "cn-south-1";
		String domain = "myhuaweicloud.com";
		String projectId = "0225be3a019486901";
		String ak = "0NOQ6HRWCGAPXC";
		String sk = "ncXygdg1E3IddkXjJYebDrLO";

		OSFactory.enableHttpLoggingFilter(true);

		Config config = Config.newConfig().withLanguage("zh-cn")
				.withSSLVerificationDisabled();

		osClientAKSK = OSFactory.builderAKSK().withConfig(config).credentials(ak, sk, region, projectId, domain).authenticate();

	}

	@Test
	 public void testListListenersV2() throws IOException {
		 List<? extends ListenerV2> list = osClientAKSK.networking().lbaasV2().listener().list();

		 Assert.assertTrue(list != null);
		 Assert.assertTrue(list.size() > 0);
	 }


	 @Test
	 public void testCreateOrUpdateAllWhitelist() throws IOException{
		 List<? extends ListenerV2> list = osClientAKSK.networking().lbaasV2().listener().list();
		 final String WHITELIST = W_WHITELIST;
		 final Boolean ISENABLE = Boolean.FALSE;

		 Assert.assertTrue(list != null);


		 NeutronWhitelists res = osClientAKSK.networking().lbaasV2().lbWhitelist().list();
		 List<NeutronWhitelist> whitelists  = res.getList();

		 Map<String,String> listenerIdSet = new HashMap<String,String>();
		 if(whitelists != null){
			 for (NeutronWhitelist whitelist1 : whitelists) {
				 listenerIdSet.put(whitelist1.getListenerId(),whitelist1.getId());
			 }

		 }


		 for (ListenerV2 listenerV2 : list) {

			 NeutronWhitelist response = null;
			 if (listenerIdSet != null  && listenerIdSet.containsKey(listenerV2.getId())) {//updateModel
				 NeutronWhitelistUpdate updateModel = NeutronWhitelistUpdate.builder().whitelist(WHITELIST).enableWhitelist(ISENABLE).build();

				 response = osClientAKSK.networking().lbaasV2().lbWhitelist().update(updateModel, listenerIdSet.get(listenerV2.getId()));
			 } else {// Create Model
				 NeutronWhitelist model = NeutronWhitelist.builder().listenerId(listenerV2.getId()).whitelist(WHITELIST).enableWhitelist(ISENABLE).build();

				 response = osClientAKSK.networking().lbaasV2().lbWhitelist().create(model);
			 }



//			 Assert.assertTrue(WHITELIST.equalsIgnoreCase(response.getWhitelist()));
			 Assert.assertTrue(ISENABLE == response.isEnableWhitelist());
		 }



	 }


	@Test
	public void testListWhitelist() throws IOException{
		NeutronWhitelists res = osClientAKSK.networking().lbaasV2().lbWhitelist().list();

		Assert.assertTrue(res.getList() != null);
		Assert.assertTrue(res.getList().isEmpty() == false);
	}


	@Test
	 public void testEnableWhitelist() throws IOException{
		 String whitelistId = "0c9d9cf0-a3b4-4fd8-9e10-3cf710f918d7";
		 String whiteList = W_WHITELIST;
		 NeutronWhitelistUpdate model = NeutronWhitelistUpdate.builder().whitelist(whiteList).enableWhitelist(Boolean.TRUE).build();

		 NeutronWhitelist res = osClientAKSK.networking().lbaasV2().lbWhitelist().update(model, whitelistId);

	 }

	 @Test
	 public void testDisableWhitelist() throws IOException{
		 String whitelistId = "0c9d9cf0-a3b4-4fd8-9e10-3cf710f918d7";
		 String whiteList = W_WHITELIST;
		 NeutronWhitelistUpdate model = NeutronWhitelistUpdate.builder().whitelist(whiteList).enableWhitelist(Boolean.FALSE).build();

		 NeutronWhitelist res = osClientAKSK.networking().lbaasV2().lbWhitelist().update(model, whitelistId);

	 }

}
