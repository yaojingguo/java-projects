public class Code {
  void spin() {
    int i;
    for (i = 0; i < 100; i++) {
      ; // Empty
    }
  }

  void sspin() {
    short i;
    for (i = 0; i < 100; i++) {
      ; // Empty
    }
  }

  void dspin() {
    double i;
    for (i = 0; i < 100.0; i++) {
      ; // Empty
    }
  }

  void useManyNumeric() {
    int i = 100;
    int j = 1000000;
    long l1 = 1;
    long l2 = 0xFFFFFFFF;
    double d = 2.2;
    // Do some calculations
  }

  int addTwo(int i, int j) {
    return i + j;
  }

  static int addTwoStatic(int i, int j) {
    return i + j;
  }

  int add12and13() {
    return addTwo(12, 13);
  }
  
  int add12and13_static() {
    return addTwoStatic(12, 13);
  }

  Object create() {
    return new Object();
  }

  int chooseNear(int i) {
    switch (i) {
      case 0: return 0;
      case 1: return 1;
      case 2: return 2;
      default: return -1; 
    }
  }
}

class Near {
  int it;

  public int getItNear() {
    return getIt();
  }

  private int getIt() {
    return it;
  }

  void setIt(int value) {
    it = value;
  }
}

class Far extends Near {
  int getItFar() {
    return super.getItNear();
  }
}
