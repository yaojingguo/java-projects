package org.yao;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;

public class StructuredMessageFactory extends AbstractMessageFactory {
  @Override
  public Message newMessage(String message, Object... params) {
    return new StructuredMessage(message, params);
  }
}
