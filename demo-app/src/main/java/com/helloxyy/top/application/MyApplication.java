package com.helloxyy.top.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lihao on 17/12/13.
 *
 * @author lihao
 * @date 2019-02-24
 */
public class MyApplication {

    private static Logger logger = LoggerFactory.getLogger(MyApplication.class);

    public static void run() throws Exception {
        logger.info("[Application] Starting My application");
        Runner runner = new Runner();
        for (; ; ) {
            runner.run();
        }
    }

}
