package cn.qiniu.util;

import java.util.Map;

/**
 * Created by roderickWang on 6/24/16.
 */
public class ManagerForm {
    private String dictType;
    private Map<String, String> loginUser;

    public Map<String, String> getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Map<String, String> loginUser) {
        this.loginUser = loginUser;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
}
