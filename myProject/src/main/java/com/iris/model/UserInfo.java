package com.iris.model;

import java.util.Date;

/**
 * Created by lwrong on 2018/2/2.
 */
public class UserInfo {
/**
 `ID` bigint(8) unsigned NOT NULL AUTO_INCREMENT,
 `USER_NAME` varchar(50) NOT NULL COMMENT '用户名-作为唯一的区分',
 `ALIAS` varchar(300) DEFAULT NULL COMMENT '昵称',
 `PASSWORD` varchar(50) NOT NULL COMMENT '后续用MD5加密',
 `ROLE_KEY` varchar(500) DEFAULT NULL COMMENT '角色控制',
 `USR_DESC` varchar(200) DEFAULT NULL COMMENT '用户描述',
 `EMAIL` varchar(50) DEFAULT NULL COMMENT '电子邮件地址',
 `MOBIL` varchar(30) DEFAULT NULL COMMENT '手机号',
 `ONLINE_STATE` varchar(10) DEFAULT NULL COMMENT '判断是否在线',
 `CREATE_DATE` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
 `UPDATE_DATE` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '控制密码过期时期',
 `LAST_LOG_TIME` timestamp NULL DEFAULT NULL COMMENT '最后一次登录时间',
 `LOG_COUNT` int(4) DEFAULT NULL COMMENT '登录系统次数',
 `SCORE` int(8) DEFAULT NULL COMMENT '积分系统，方便后续扩展',
 `FLAG` int(2) DEFAULT NULL COMMENT '后续考虑',
 */
    private long id;
    private String username;
    private String alias;
    private String password;
    private String role;
    private String email;
    private String mobil;
    private int onlineStatus;
    private Date createTime;
    private Date updateDate;
    private Date lastLoginTime;
    private int logCount;
    private int score;
    private int flag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLogCount() {
        return logCount;
    }

    public void setLogCount(int logCount) {
        this.logCount = logCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }



}
