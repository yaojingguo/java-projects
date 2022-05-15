- Java agent __does__ the class transformation before the main method. 
- Java agent __does__ the transformation no matter whether the transformed class will be used.

```bash
mvn package
MAVEN_OPTS=-javaagent:./target/javassist-code-0.0.1.jar \
  mvn exec:java -Dexec.mainClass="org.yao.application.PlainMain" -Dexec.args="3 2 5"

MAVEN_OPTS=-javaagent:./target/javassist-code-0.0.1.jar \
  mvn exec:java -Dexec.mainClass="org.yao.application.MyAtmApplication" -Dexec.args="3 2 5"
  
java -javaagent:./target/javassist-code-0.0.1.jar -cp ./target/classes org.yao.application.PlainMain
java -javaagent:./target/javassist-code-0.0.1.jar -cp ./target/classes org.yao.application.MyAtmApplication 3 2 5
```
