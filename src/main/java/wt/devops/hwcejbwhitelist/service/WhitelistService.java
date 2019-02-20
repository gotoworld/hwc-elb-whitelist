package wt.devops.hwcejbwhitelist.service;

import wt.devops.hwcejbwhitelist.controller.HuaweicloudContext;

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

}
