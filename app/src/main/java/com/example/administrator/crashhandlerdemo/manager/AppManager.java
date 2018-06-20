package com.example.administrator.crashhandlerdemo.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by Horrarndoo on 2017/4/5.
 * <p>
 * AppManager 管理Activity栈
 */

public class AppManager {
    private static Stack<Activity> activityStack;
    private static volatile AppManager instance;

    private AppManager() {
        activityStack = new Stack<Activity>();
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null)
            return;

        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        if (activityStack == null || activityStack.empty())
            return null;

        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        finishActivity(getCurrentActivity());
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activityStack == null || activityStack.empty() || activity == null)
            return;

        if (!activityStack.contains(activity))
            return;

        activityStack.remove(activity);
        activity.finish();
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null || activityStack.empty())
            return;

        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null || activityStack.empty())
            return;

        for (Activity activity : activityStack) {
            if (activity != null)
                activity.finish();
        }

        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context
                    .ACTIVITY_SERVICE);
            if (activityMgr != null)
                activityMgr.killBackgroundProcesses(context.getPackageName());
            //            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAppExit() {
        return activityStack == null || activityStack.empty();
    }
}
