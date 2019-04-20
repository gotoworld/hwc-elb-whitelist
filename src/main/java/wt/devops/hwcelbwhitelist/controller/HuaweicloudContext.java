package wt.devops.hwcelbwhitelist.controller;

import lombok.Data;
import wt.devops.hwcelbwhitelist.pojo.WhitelistVO;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 *
 * @author uname.chen
 * @date 19-2-20
 */
@Data
public class HuaweicloudContext implements Serializable {
  
    String region;
    String domain;
    String projectId;
    String ak;
    String sk;

    String whitelist;


    List<WhitelistVO> whitelistVoList;

}
