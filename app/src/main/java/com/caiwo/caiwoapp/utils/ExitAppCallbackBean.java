package com.caiwo.caiwoapp.utils;

/**
 * Created by Administrator on 2018/5/21.
 */

public class ExitAppCallbackBean {

    private boolean isExit;//是否退出
    private String quit;//退出
    private String clearMsg;//消息清除
    private String headImage;//头像修改
    private String headImageType;
    private String refreshMine;//刷新我页面
    private String refreshMessage;//刷新消息页面
    private String messageDelete;
    private String group_id;
    private String notify;
    private String beizhu;
    private String login;
    private String page;

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getRefreshMessage() {
        return refreshMessage;
    }

    public void setRefreshMessage(String refreshMessage) {
        this.refreshMessage = refreshMessage;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getNotify() {
        return notify;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getMessageDelete() {
        return messageDelete;
    }

    public void setRefreshMine(String refreshMine) {
        this.refreshMine = refreshMine;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getRefreshMine() {
        return refreshMine;
    }

    public void setMessageDelete(String messageDelete) {
        this.messageDelete = messageDelete;
    }

    public void setHeadImageType(String headImageType) {
        this.headImageType = headImageType;
    }

    public String getHeadImageType() {
        return headImageType;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setClearMsg(String clearMsg) {
        this.clearMsg = clearMsg;
    }

    public String getClearMsg() {
        return clearMsg;
    }

    public void setQuit(String quit) {
        this.quit = quit;
    }

    public String getQuit() {
        return quit;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setIsExit(boolean isExit) {
        this.isExit = isExit;
    }
}
