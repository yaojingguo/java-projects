package org.yao.agent;


import java.lang.instrument.Instrumentation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyAgent {
  private static Logger LOGGER = LogManager.getLogger(MyAgent.class);

  public static void premain(String agentArgs, Instrumentation inst) {
    LOGGER.info("[Agent] entered premain");

    String className = "org.yao.application.MyAtm";
    transformClass(className,inst);
    LOGGER.info("[Agent] exiting premain");
  }

  public static void agentmain(String agentArgs, Instrumentation inst) {
    LOGGER.info("[Agent] In agentmain method");

    String className = "org.yao.application.MyAtm";
    transformClass(className,inst);
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
      LOGGER.error("Class [{}] not found with Class.forName");
    }

    // otherwise iterate all loaded classes and find what we want
    for(Class<?> clazz: instrumentation.getAllLoadedClasses()) {
      if(clazz.getName().equals(className)) {
        targetCls = clazz;
        targetClassLoader = targetCls.getClassLoader();
        transform(targetCls, targetClassLoader, instrumentation);
        return;
      }
    }
    throw new RuntimeException("Failed to find class [" + className + "]");
  }

  private static void transform(Class<?> clazz, ClassLoader classLoader, Instrumentation instrumentation) {
    AtmTransformer dt = new AtmTransformer(clazz.getName(), classLoader);
    instrumentation.addTransformer(dt, true);
    try {
      // retransformClasses does the class retransformation
      instrumentation.retransformClasses(clazz);
    } catch (Exception ex) {
      throw new RuntimeException("Transform failed for class: [" + clazz.getName() + "]", ex);
    }
  }

}
