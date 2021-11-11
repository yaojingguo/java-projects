package org.yao.lookup;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public abstract class CommandManager {

  public Object process(Object commandState) {
    Command command = createCommand();
    command.setState(commandState);
    return command.execute();
  }

//  @Lookup("myCommand")
  @Lookup
  protected abstract Command createCommand();
}