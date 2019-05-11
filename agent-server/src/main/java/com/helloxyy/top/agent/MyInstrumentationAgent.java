package com.helloxyy.top.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * Created by lihao on 17/12/13.
 *
 * @author lihao
 * @date 2019-02-24
 */
public class MyInstrumentationAgent {

    private static Logger logger = LoggerFactory.getLogger(MyInstrumentationAgent.class);

    public static void agentmain(String agentArgs, Instrumentation inst) {
        logger.info("[Agent] In agentmain method");

        //需要监控的类
        String className = "com.helloxyy.top.application.Runner";
        transformClass(className, inst);
    }

    private static void transformClass(String className, Instrumentation instrumentation) {
        Class<?> targetCls = null;
        ClassLoader targetClassLoader = null;
        // see if we can get the class using forName
        try {
            targetCls = Class.forName(className);
            targetClassLoader = targetCls.getClassLoader();
            transform(targetCls, targetClassLoader, instrumentation);
            return;
        } catch (Exception ex) {
            logger.error("Class [{}] not found with Class.forName", className);
        }
        throw new RuntimeException("Failed to find class [" + className + "]");
    }

    private static void transform(Class<?> clazz, ClassLoader classLoader, Instrumentation instrumentation) {
        MyTransformer dt = new MyTransformer(clazz.getName(), classLoader);
        instrumentation.addTransformer(dt, true);
        try {
            instrumentation.retransformClasses(clazz);
        } catch (Exception ex) {
            throw new RuntimeException("Transform failed for class: [" + clazz.getName() + "]", ex);
        }
    }

}
