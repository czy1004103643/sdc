package com.zgczx.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 教师基本信息表
 *
 * @author  jason
 */

@Entity
@Data
@DynamicUpdate
public class TeaBase {

    /**
     * 教师工号
     */
    @Id
    private String teaCode;
    /**
     * 教师姓名
     */
    private String teaName;
    /**
     * 教师科目
     */
    private String teaSubject;
    /**
     * 教师绑定微信号
     */
    private String teaOpenid;
    /**
     * 教师密码
     */
    private String teaPasswd;
    /**
     * 教师昵称
     */
    private String teaNickname;
    /**
     * 教师头像地址
     */
    private String teaHeadimgurl;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public String toString() {
        return "TeaBase{" + "teaCode='" + teaCode + '\'' + ", teaName='" + teaName + '\'' + ", teaSubject='" + teaSubject + '\'' + ", teaOpenid='" + teaOpenid + '\'' + ", teaPasswd='" + teaPasswd + '\'' + ", teaNickname='" + teaNickname + '\'' + ", teaHeadimgurl='" + teaHeadimgurl + '\'' + ", createTime=" + createTime + ", updateTime=" + updateTime + '}';
    }
}
