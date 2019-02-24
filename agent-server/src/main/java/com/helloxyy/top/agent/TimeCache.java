package com.helloxyy.top.agent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihao on 17/12/13.
 *
 * @author lihao
 * @date 2019-02-24
 */
public class TimeCache {

    public static Map<String, Long> sStartTime = new HashMap<>();
    public static Map<String, Long> sEndTime   = new HashMap<>();

    public static void setStartTime(String methodName, long time) {
        System.out.println("setStartTime," + time + ":methodName");
        sStartTime.put(methodName, time);
    }

    public static void setEndTime(String methodName, long time) {
        System.out.println("setEndTime," + time + ":methodName");

        sEndTime.put(methodName, time);
    }

    public static String getCostTime(String methodName) {
        long start = sStartTime.get(methodName);
        long end = sEndTime.get(methodName);
        return "method: " + methodName + " cost " + Long.valueOf(end - start) + " ns";
    }

}
