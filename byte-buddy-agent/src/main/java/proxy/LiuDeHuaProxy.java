package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LiuDeHuaProxy {
  private Person ldh = new LiuDeHua();


  public Person getProxy() {
    return (Person) Proxy.newProxyInstance(LiuDeHuaProxy.class
            .getClassLoader(), ldh.getClass().getInterfaces(),
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method,
                               Object[] args) throws Throwable {
            if (method.getName().equals("sing")) {
              System.out.println("我是他的经纪人，要找他唱歌得先给十万块钱！！");
              return method.invoke(ldh, args); //代理对象调用真实目标对象的sing方法去处理用户请求
            }

            if (method.getName().equals("dance")) {
              System.out.println("我是他的经纪人，要找他跳舞得先给二十万块钱！！");
              return method.invoke(ldh, args);//代理对象调用真实目标对象的dance方法去处理用户请求
            }
            return null;
          }
        });
  }
}
