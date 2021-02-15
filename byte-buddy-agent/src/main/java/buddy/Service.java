package buddy;

public class Service {
  public int foo(int value) {
    System.out.printf("foo: %d\n", value);
    return value;
  }

  @Log
  public int bar(int value) {
    System.out.printf("bar: %d\n", value);
    return value;
  }
}
