package cn.zzuzl.xblog.model.vo;

import java.util.Date;

public class TokenVerifyResult {
    private Integer userId;
    private Date exp;
    private boolean valid;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
