package com.helloxyy.top.agent;

import com.sun.tools.attach.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

/**
 * Created by lihao on 17/12/13.
 *
 * @author lihao
 * @date 2019-02-24
 */
public class AgentLoader {

    private static Logger logger = LoggerFactory.getLogger(AgentLoader.class);
    private static String dir    = System.getProperty("user.dir")+"/agent-server";

    public static void run() {
        String agentFilePath = dir + "/target/myAgent-jar-with-dependencies.jar";

        //需要attach的进程标识
        String applicationName = "demo-app";

        //查到需要监控的进程
        Optional<String> jvmProcessOpt = Optional.ofNullable(VirtualMachine.list()
                                                                           .stream()
                                                                           .filter(jvm -> {
                                                                               logger.info("jvm:{}", jvm.displayName());
                                                                               return jvm.displayName().contains(
                                                                                       applicationName);
                                                                           })
                                                                           .findFirst().get().id());

        if (!jvmProcessOpt.isPresent()) {
            logger.error("Target Application not found");
            return;
        }
        File agentFile = new File(agentFilePath);
        try {
            String jvmPid = jvmProcessOpt.get();
            logger.info("Attaching to target JVM with PID: " + jvmPid);
            VirtualMachine jvm = VirtualMachine.attach(jvmPid);
            jvm.loadAgent(agentFile.getAbsolutePath());
            jvm.detach();
            logger.info("Attached to target JVM and loaded Java agent successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
