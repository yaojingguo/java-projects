package buddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

public class Entry {
  public static void main(String[] args) throws Exception {
    Service service =
        new ByteBuddy()
            .subclass(Service.class)
            .method(ElementMatchers.any())
            .intercept(Advice.to(LoggerAdvisor.class))
            .make()
            .load(Service.class.getClassLoader())
            .getLoaded()
            .getDeclaredConstructor()
            .newInstance();
    service.foo(123);
    service.bar(456);
  }
}