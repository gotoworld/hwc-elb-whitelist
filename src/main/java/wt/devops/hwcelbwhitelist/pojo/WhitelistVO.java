package wt.devops.hwcelbwhitelist.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author uname.chen
 * @date 19-2-26
 */
@Data
public class WhitelistVO implements  Serializable{

    String id;

    String elbId;
    String elbName;

    String listenerId;
    String listenerName;

    String whitelistId;
    String whitelist;
    String whitelistNoCommaSplit;
    boolean enableWhitelist;

    //
    public WhitelistVO(String elbId, String elbName) {
        this.elbId = elbId;
        this.elbName = elbName;
    }


    public String getWhitelistNoCommaSplit(){
        if (whitelist == null) {
            return null;
        } else {
            return whitelist.replace(',', ';');
        }
    }
}
