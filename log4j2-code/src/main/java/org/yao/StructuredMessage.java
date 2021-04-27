package org.yao;

import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;

public class StructuredMessage implements Message  {
  private String formattedMessage;
  private String message;
  private ArrayList<Kv> kvList;

  public StructuredMessage(String message, Object... kvs) {
    kvList = new ArrayList<>();
    for (int i = 0; i < kvs.length; i += 2) {
      Kv kv = new Kv(kvs[i].toString(), kvs[i+1].toString());
      kvList.add(kv);
    }
    // TODO: handle the case when kvs.length is odd
    StringBuilder sb = new StringBuilder(message);
    sb.append(" ");
    for (Kv kv: kvList) {
      sb.append(kv.k());
      sb.append("=");
      sb.append(kv.v());
    }
    formattedMessage = sb.toString();
  }

  @Override
  public String getFormattedMessage() {
    return formattedMessage;
  }

  @Override
  public String getFormat() {
    return null;
  }

  @Override
  public Object[] getParameters() {
    return new Object[0];
  }

  @Override
  public Throwable getThrowable() {
    return null;
  }
}
