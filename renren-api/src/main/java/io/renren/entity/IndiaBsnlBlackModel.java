package io.renren.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * ph bsnl黑名单表
 * @author GMP
 *
 */
@TableName("t_bsnl_blacklisted")
public class IndiaBsnlBlackModel implements Serializable {

    private static final long serialVersionUID = -4584281157388560580L;
    /**
     * 主键ID
     */
    @TableId
    private String Id;

    /**
     * 手机号
     */
    private String phoneNo;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}