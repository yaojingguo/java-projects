public class Test {

  static int n = 100_000_000;
  static int[] all_string = new int[n*1*5];
  static int all_strings_index = 0;



  public static void main(String[] args) {

    while(true) {

      all_strings_index = 0;

      int s1 = (int) System.nanoTime();
      int s2 = (int) System.nanoTime();
      int s3 = (int) System.nanoTime();
      int s4 = (int) System.nanoTime();
      int s5 = (int) System.nanoTime();

      long t = System.currentTimeMillis();


      t = System.currentTimeMillis();
      for (int i = 0; i < n; i++) {
        var(s1, s2, s3, s4, s5);
      }
      System.err.println("varargs    "+(System.currentTimeMillis() - t));

      all_strings_index = 0;

      t = System.currentTimeMillis();
      for (int i = 0; i < n; i++) {
        par(s1, s2, s3, s4, s5);
      }
      System.err.println("parameters "+(System.currentTimeMillis() - t));


      all_strings_index = 0;

      int[] arr = new int[] {s1, s2, s3, s4, s5};

      t = System.currentTimeMillis();
      for (int i = 0; i < n; i++) {
        var2(arr);
      }
      System.err.println("array      "+(System.currentTimeMillis() - t));
      System.err.println();

    }

  }

  static void par(int a1, int a2, int a3, int a4, int a5) {
    all_string[all_strings_index++] = a1;
    all_string[all_strings_index++] = a2;
    all_string[all_strings_index++] = a3;
    all_string[all_strings_index++] = a4;
    all_string[all_strings_index++] = a5;
  }

  static void var(int... a) {
    for (int s : a) {
      all_string[all_strings_index++] = s;
    }
  }

  static void var2(int[] a) {
    for (int s : a) {
      all_string[all_strings_index++] = s;
    }
  }
}
