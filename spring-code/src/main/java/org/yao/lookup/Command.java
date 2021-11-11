package org.yao.lookup;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Command {
  private Object state;

  void setState(Object state) {
    this.state = state;
  }

  public String execute() {
    return String.format("command: %s, object: %s", this, state);
  }
}
