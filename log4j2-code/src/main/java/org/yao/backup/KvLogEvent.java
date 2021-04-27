package org.yao.backup;

import org.apache.logging.log4j.core.AbstractLogEvent;
import org.apache.logging.log4j.message.Message;
import org.yao.StructuredMessage;

public class KvLogEvent extends AbstractLogEvent {
  private StructuredMessage message;
  private Throwable t;

  public KvLogEvent(StructuredMessage message, Throwable t) {
    this.message = message;
    this.t = t;
  }

  @Override
  public Message getMessage() {
    return message;
  }
}
