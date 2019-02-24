package com.helloxyy.top.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lihao on 17/12/13.
 *
 * @author lihao
 * @date 2019-02-24
 */
public class Runner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    public void run() throws InterruptedException {
        long sleep = (long) (Math.random() * 1000 + 200);
        Thread.sleep(sleep);
        logger.info("run in [" + sleep + "] millis!");
    }
}
