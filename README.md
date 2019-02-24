# agent-demo

1. 打包
mvn clean package

2. 运行业务程序
java -jar demo-app/target/demo-app-jar-with-dependencies.jar


3. 运行agent程序
java -Djava.ext.dirs=${JAVA_HOME}/lib -jar agent-server/target/myAgent-jar-with-dependencies.jar
idea 启动 不需要加 -Djava.ext.dirs 参数，但请注意修改agentFilePath路径
jar包启动 根据jar的名字attach，idea启动根据applicationName attach
 
```text
20:26:20.845 [main] INFO com.helloxyy.top.application.Runner - completed in:655 millis!
20:26:21.613 [main] INFO com.helloxyy.top.application.Runner - run in [764] millis!
20:26:21.614 [main] INFO com.helloxyy.top.application.Runner - completed in:768 millis!
20:26:22.005 [main] INFO com.helloxyy.top.application.Runner - run in [387] millis!
20:26:22.006 [main] INFO com.helloxyy.top.application.Runner - completed in:392 millis!
20:26:22.441 [main] INFO com.helloxyy.top.application.Runner - run in [435] millis!
20:26:22.441 [main] INFO com.helloxyy.top.application.Runner - completed in:435 millis!

```
