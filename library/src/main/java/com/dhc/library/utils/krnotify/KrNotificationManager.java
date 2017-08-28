package com.dhc.library.utils.krnotify;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.dhc.library.data.SPHelper;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.logger.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  通知监听
 */

public class KrNotificationManager implements KrNotifySubject {
    private static KrNotificationManager instance;

    public static synchronized KrNotificationManager getInstance() {
        if (instance == null) {
            instance = new KrNotificationManager();
        }

        return instance;
    }

    private HashMap<String, KrNotifyMessage> manager = new HashMap<String, KrNotifyMessage>();

    private List<Observer> dailyObserverlist = new ArrayList<Observer>();

    /**
     * 注册消息监听
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
        dailyObserverlist.add(observer);
    }

    /**
     * 解绑消息监听
     * @param observer
     */
    @Override
    public void detach(Observer observer) {
        dailyObserverlist.remove(observer);
    }

    /**
     * 这个Tag为唯一标识
     * @param tag
     * @param dailyNotifMessage
     */
    @Override
    public void addMessage(String tag, KrNotifyMessage dailyNotifMessage) {
        if (dailyNotifMessage == null)
            return;
        dailyNotifMessage.setShowed(false);
        getMessages().put(tag, dailyNotifMessage);
        saveMessageToDisk();
        for (Observer observer : dailyObserverlist) {
            observer.addMessage(tag, dailyNotifMessage);
        }
    }

    public void  saveMessageToDisk() {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(getMessages());
        SPHelper.put(AppContext.get(), "teamMessage", s);
    }

    public HashMap<String, KrNotifyMessage> getMessages() {
        if (manager == null||manager.isEmpty()) {
            Gson gson = new GsonBuilder().create();
            String s = (String) SPHelper.get(AppContext.get(), "teamMessage", "");
            manager = gson.fromJson(s, new TypeToken<HashMap<String, KrNotifyMessage>>() {
            }.getType());
        }

        if (manager==null)
            manager= new HashMap<String, KrNotifyMessage>();
        return manager;
    }


    public void removeMessageDisk(String tag) {
        getMessages().remove(tag);
        saveMessageToDisk();
    }

    @Override
    public void removeMessage(String tag) {
        removeMessageDisk(tag);
        KLog.d("shiming remove");
        for (Observer observer : dailyObserverlist) {
            observer.removeMessage(tag);
        }
    }


    public boolean hasMessage(String teamId) {
        return getMessages().containsKey(teamId);
    }
   public KrNotifyMessage getMessage(String key){
       return getMessages().get(key);
   }

    public interface Observer {

        public void addMessage(String tag, KrNotifyMessage dailyNotifMessage);

        public void removeMessage(String teamId);

    }
}
