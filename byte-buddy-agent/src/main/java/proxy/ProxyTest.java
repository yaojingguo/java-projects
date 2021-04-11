package proxy;

public class ProxyTest {
  public static void main(String[] args) {
    LiuDeHuaProxy proxy = new LiuDeHuaProxy();

    Person p = proxy.getProxy();

    //调用代理对象的sing方法
    String retValue = p.sing("冰雨");
    System.out.println(retValue);

    //调用代理对象的dance方法
    String value = p.dance("江南style");
    System.out.println(value);
  }
}
