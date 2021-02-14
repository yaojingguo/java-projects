package org.yao;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class MyAgent {
  // JVM will try this method first.
  public static void premain(String agentArgs, Instrumentation inst) {
    System.out.println("this is my agent：" + agentArgs);

    AgentBuilder.Transformer transformer =
        (builder, typeDescription, classLoader, javaModule) -> {
          return builder
              .method(ElementMatchers.any()) // intercept any methods
              .intercept(MethodDelegation.to(MethodCostTime.class)); // delegate
        };

    AgentBuilder.Listener listener =
        new AgentBuilder.Listener() {
          @Override
          public void onDiscovery(
              String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {}

          @Override
          public void onTransformation(
              TypeDescription typeDescription,
              ClassLoader classLoader,
              JavaModule javaModule,
              boolean b,
              DynamicType dynamicType) {}

          @Override
          public void onIgnored(
              TypeDescription typeDescription,
              ClassLoader classLoader,
              JavaModule javaModule,
              boolean b) {}

          @Override
          public void onError(
              String s,
              ClassLoader classLoader,
              JavaModule javaModule,
              boolean b,
              Throwable throwable) {}

          @Override
          public void onComplete(
              String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {}
        };

    new AgentBuilder.Default()
        .type(ElementMatchers.nameStartsWith("org.yao.trace")) // 指定需要拦截的类
        .transform(transformer)
        .with(listener)
        .installOn(inst);
  }

  // If agent class does not implement the above method, JVM will try to invoke this method.
  public static void premain(String agentArgs) {}
}
