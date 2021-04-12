package org.yao;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.DefaultMethodCall;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.assign.primitive.PrimitiveTypeAwareAssigner;
import org.junit.Test;
import org.yao.custom.StringValueBinder;
import org.yao.custom.ToStringInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.not;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;
import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class ByteTest {
  @Test
  public void testByteBuddy() throws IllegalAccessException, InstantiationException {
    Class<?> dynamicType =
        new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString"))
            .intercept(FixedValue.value("Hello World!"))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
    assertThat(dynamicType.newInstance().toString()).isEqualTo("Hello World!");
  }

  @Test
  public void testCreateClass() {
    {
      DynamicType.Unloaded<?> dynamicType = new ByteBuddy().subclass(Object.class).make();
      System.out.printf("dynamic type: %s\n", dynamicType);
    }
    {
      DynamicType.Unloaded<?> dynamicType =
          new ByteBuddy().subclass(Object.class).name("example.Type").make();
      System.out.printf("dynamic type: %s\n", dynamicType);
    }
    {
      DynamicType.Unloaded<?> dynamicType =
          new ByteBuddy()
              .with(
                  new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription superClass) {
                      return "i.love.ByteBuddy." + superClass.getSimpleName();
                    }
                  })
              .subclass(Object.class)
              .make();
      System.out.printf("dynamic type: %s\n", dynamicType);
    }
  }

  @Test
  public void testClassLoading() {
    Class<?> type =
        new ByteBuddy()
            .subclass(Object.class)
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
    // Null means the bootstrap class loader.
    System.out.printf("bootstrap class loader: %s\n", Object.class.getClassLoader());
    System.out.printf("system class loader: %s\n", getClass().getClassLoader());
  }

  @Test
  public void testClassReloading() {}

  @Test
  public void testFieldAndMethod() throws Exception {
    //    {
    //      String toString =
    //          new ByteBuddy()
    //              .subclass(Object.class)
    //              .name("example.Type")
    //              .make()
    //              .load(getClass().getClassLoader())
    //              .getLoaded()
    //              .newInstance() // Java reflection API
    //              .toString();
    //      System.out.printf("toString: %s\n", toString);
    //      System.out.printf("Object.toString: %s\n", new Object().toString());
    //    }
    {
      String fixedValue = "Hello World!";
      String toString =
          new ByteBuddy()
              .subclass(Object.class)
              .name("example.Type")
              .method(named("toString"))
              .intercept(FixedValue.value(fixedValue))
              .make()
              .load(getClass().getClassLoader())
              .getLoaded()
              .newInstance()
              .toString();
      assertThat(toString).isEqualTo(fixedValue);
    }
  }

  @Test
  public void testSuperCall() throws IllegalAccessException, InstantiationException {
    MemoryDatabase loggingDatabase =
        new ByteBuddy()
            .subclass(MemoryDatabase.class)
            .method(named("load"))
            .intercept(MethodDelegation.to(LoggerInterceptor.class))
            .make()
            .load(MemoryDatabase.class.getClassLoader())
            .getLoaded()
            .newInstance();
    System.out.printf("load: %s\n", loggingDatabase.load("jingguo"));
    ;
  }

  @Test
  public void testDefaultMethod() {
    new ByteBuddy(ClassFileVersion.JAVA_V8)
        .subclass(Object.class)
        .implement(First.class)
        //        .implement(Second.class)
        .method(named("qux"))
        .intercept(DefaultMethodCall.prioritize(First.class))
        .make();
  }

  @Test
  public void testUsage() throws IllegalAccessException, InstantiationException {
    String helloWorld =
        new ByteBuddy()
            .subclass(Source.class)
            .method(named("hello"))
            .intercept(MethodDelegation.to(Target.class))
            .make()
            .load(ByteTest.class.getClassLoader())
            .getLoaded()
            .newInstance()
            .hello("World");
    System.out.printf("%s\n", helloWorld);
  }

  @Test
  public void testSpecificMethod() throws Exception {
    new ByteBuddy()
        .subclass(Object.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
        .defineConstructor(Visibility.PUBLIC)
        .withParameters(int.class)
        .intercept(MethodCall.invoke(Object.class.getDeclaredConstructor()))
        .make();
  }

  @Test
  public void testFieldAccess() throws Exception {
    Class<? extends UserType> dynamicUserType =
        new ByteBuddy()
            .subclass(UserType.class)
            .method(not(isDeclaredBy(Object.class)))
            .intercept(MethodDelegation.toField("interceptor"))
            .defineField("interceptor", Interceptor.class, Visibility.PRIVATE)
            .implement(InterceptionAccessor.class)
            .intercept(FieldAccessor.ofBeanProperty())
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();

    InstanceCreator factory =
        new ByteBuddy()
            .subclass(InstanceCreator.class)
            .method(not(isDeclaredBy(Object.class)))
            .intercept(MethodDelegation.toConstructor(dynamicUserType))
            .make()
            .load(dynamicUserType.getClassLoader())
            .getLoaded()
            .newInstance();

    UserType userType = (UserType) factory.makeInstance();
    ((InterceptionAccessor) userType).setInterceptor(new HelloWorldInterceptor());
    System.out.printf("result: %s\n", userType.doSomething());
  }

  @Test
  public void testAnnotation() {
    new ByteBuddy().subclass(Object.class).annotateType(new RuntimeDefinitionImpl()).make();

    new ByteBuddy()
        .subclass(Object.class)
        .annotateType(new RuntimeDefinitionImpl())
        .method(named("toString"))
        .intercept(SuperMethodCall.INSTANCE)
        .annotateMethod(new RuntimeDefinitionImpl())
        .defineField("foo", Object.class)
        .annotateField(new RuntimeDefinitionImpl());
  }

  @Test
  public void testAssigner() {
    new ByteBuddy()
        .subclass(Object.class)
        .method(named("toString"))
        .intercept(
            FixedValue.value(42)
                .withAssigner(
                    new PrimitiveTypeAwareAssigner(ToStringAssigner.INSTANCE),
                    Assigner.Typing.STATIC))
        .make();
  }

  @Test
  public void testBinder() throws Exception {
    Object o =
    new ByteBuddy()
        .subclass(Object.class)
        .method(named("toString"))
        .intercept(MethodDelegation.withDefaultConfiguration()
            .withBinders(StringValueBinder.INSTANCE)
            .to(ToStringInterceptor.class))
        .make()
        .load(ByteTest.class.getClassLoader())
        .getLoaded()
        .newInstance();
    System.out.printf("toString: %s\n", o);
  }
}
