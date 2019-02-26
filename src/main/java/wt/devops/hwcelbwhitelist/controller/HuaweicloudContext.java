package wt.devops.hwcelbwhitelist.controller;

import lombok.Data;

/**
 * Description
 *
 * @author uname.chen
 * @date 19-2-20
 */
@Data
public class HuaweicloudContext {
    String region;
    String domain;
    String projectId;
    String ak;
    String sk;

    String whitelist;
}
