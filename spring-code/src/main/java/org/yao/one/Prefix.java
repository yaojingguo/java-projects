package org.yao.one;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class Prefix {
  private Collection<Monkey> monkeys;

  public String prefix() {
    return "roombox: " + monkeys + ": ";
  }

  @Autowired
  public void coll(Collection<Monkey> monkeys) {
    this.monkeys = monkeys;
    System.out.printf("collection: %s\n", monkeys);
  }

  @Autowired
  public void map(Map<String, Monkey> map) {
    System.out.printf("map: %s\n", map);
  }

  @Autowired
  public void op(ObjectProvider<Monkey> opm) {
    System.out.printf("object provider: %s\n", opm.stream().collect(Collectors.toList()));
  }

  public void init() {
    System.out.printf("initializing a prefix\n");
  }


}
