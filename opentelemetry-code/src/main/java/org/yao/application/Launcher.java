package org.yao.application;

public class Launcher {
  public static void main(String[] args) throws Exception {
    if(args[0].equals("StartMyAtmApplication")) {
      new MyAtmApplication().main(args);
    } else if(args[0].equals("LoadAgent")) {
      new AgentLoader().main(args);
    }
  }
}
