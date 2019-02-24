package com.helloxyy.top.application;

/**
 * Created by lihao on 17/12/13.
 *
 * @author lihao
 * @date 2019-02-24
 */
public class Launcher {
    public static void main(String[] args) throws Exception {
        if(args != null && args.length > 0 && "LoadAgent".equals(args[0])) {
            new AgentLoader().run();
        }else{
            new MyApplication().run();
        }
    }
}
