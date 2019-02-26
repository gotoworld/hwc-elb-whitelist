package wt.devops.hwcelbwhitelist.pojo;

import lombok.Data;

import java.util.List;

/**
 * Description
 *
 * @author uname.chen
 * @date 19-2-26
 */
@Data
public class ResponseVO<T> {

    private String code;
    private String msg;
    private int count;
    private List<T> data;

    //
    public ResponseVO() {
    }

    public ResponseVO(String code, String msg, int count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
}
