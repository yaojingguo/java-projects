package org.yao.backup;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.message.Message;
import org.yao.StructuredMessage;

import java.io.Serializable;
import java.util.Map;

@Plugin(
    name = "Structured",
    category = Core.CATEGORY_NAME,
    elementType = Appender.ELEMENT_TYPE,
    printObject = true)
public class StructuredAppender extends AbstractAppender {
  private final Appender appender;

  public StructuredAppender(
      final String name,
      final Filter filter,
      final Layout<? extends Serializable> layout,
      final boolean ignoreExceptions,
      final Property[] properties,
      AppenderRef appenderRef,
      final Configuration config) {
    super(name, filter, null, ignoreExceptions, properties);
    Map<String, Appender> map = config.getAppenders();
    appender = map.get(appenderRef.getRef());
  }

  @Override
  public void append(LogEvent event) {
    Message msg = event.getMessage();
    if (msg instanceof StructuredMessage) {
      appender.append(event);
      return;
    }
    StructuredMessage structuredMessage =
        new StructuredMessage(msg.getFormat(), msg.getParameters());
    KvLogEvent kvLogEvent = new KvLogEvent(structuredMessage, event.getThrown());
    appender.append(kvLogEvent);
  }
}
