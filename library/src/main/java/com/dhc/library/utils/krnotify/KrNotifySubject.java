package com.dhc.library.utils.krnotify;

public interface KrNotifySubject {
    /**
     * 增加订阅者
     * @param observer
     */
    public void attach(KrNotificationManager.Observer observer);
    /**
     * 删除订阅者
     * @param observer
     */
    public void detach(KrNotificationManager.Observer observer);
    /**
     * 通知订阅者添加消息
     */
    public void addMessage(String teamId,KrNotifyMessage dailyNotifMessage);

    /**
     * 通知订阅者剔除消息
     */
    public void removeMessage(String teamId);
}