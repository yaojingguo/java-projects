package org.yao;

public class Transfer {
  private String transactionId;
  private String sender;
  private String receiver;
  private Long amount;

  public Transfer(String transactionId, String sender, String receiver, long amount) {
    this.transactionId = transactionId;
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
  }

  public String getSender() {
    return sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public Long getAmount() {
    return amount;
  }
}