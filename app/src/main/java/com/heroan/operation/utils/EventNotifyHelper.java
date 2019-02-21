package com.heroan.operation.utils;

import com.heroan.operation.OperationApplication;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import zuo.biao.library.util.Log;

/**
 * 事件总线
 */
public class EventNotifyHelper {

    private static String TAG = "EventNotifyHelper";
    private volatile static EventNotifyHelper instance = null;
    final private Map<Integer, List<Object>> observers = new ConcurrentHashMap<Integer, List<Object>>();
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static EventNotifyHelper getInstance() {
        if (instance == null) {
            synchronized (EventNotifyHelper.class) {
                if (instance == null) {
                    instance = new EventNotifyHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 主线程执行，用于刷新UI
     */
    public void postUiNotification(final int id, final Object... args) {
        final List<Object> objects = observers.get(id);
        if (objects != null) {
            OperationApplication.applicationHandler.post(new Runnable() {
                @Override
                public void run() {
                    for (Object obj : objects) {
                        try {
                            ((NotificationCenterDelegate) obj).didReceivedNotification(id, args);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }
            });
        }
    }

    /**
     * 同步执行，在调用线程中执行
     */
    public void postNotification(int id, Object... args) {
        List<Object> objects = observers.get(id);
        if (objects != null) {
            for (Iterator iterator = objects.iterator(); iterator.hasNext(); ) {
                try {
                    Object object = iterator.next();
                    ((NotificationCenterDelegate) object).didReceivedNotification(id, args);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
    }

    /**
     * 独立线程队列执行
     */
    public void postNotificationThread(final int id, final Object... args) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Object> objects = observers.get(id);
                if (objects != null) {
                    for (Iterator iterator = objects.iterator(); iterator.hasNext(); ) {
                        try {
                            Object object = iterator.next();
                            ((NotificationCenterDelegate) object).didReceivedNotification(id, args);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }
            }
        });
    }

    public void addObserver(Object observer, int id) {
        List<Object> objects = observers.get(id);
        if (objects == null) {
            observers.put(id, (objects = new CopyOnWriteArrayList<Object>()));
        }
        if (objects.contains(observer)) {
            return;
        }
        objects.add(observer);
    }

    /**
     * 方法说明 :删除所有
     *
     * @Date 2015-12-17
     */
    public void removeAllObserver() {
        for (Map.Entry<Integer, List<Object>> entry : observers.entrySet()) {
            List<Object> objects = entry.getValue();
            if (objects != null) {
                objects.clear();
            }
        }
        observers.clear();
    }

    public void removeObserver(Object observer, int id) {
        List<Object> objects = observers.get(id);
        if (objects != null) {
            objects.remove(observer);
            if (objects.size() == 0) {
                observers.remove(id);
            }
        }
    }

    public interface NotificationCenterDelegate {
        void didReceivedNotification(int id, Object... args);
    }
}
