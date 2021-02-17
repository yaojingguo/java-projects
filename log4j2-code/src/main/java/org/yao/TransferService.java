package org.yao;

public abstract class TransferService {
  public boolean transfer(long amount) {
    // connects to the remote service to actually transfer money
    if (true) {
      throw new RuntimeException();
    }
    System.out.println("transferring");
    beforeTransfer(amount);
    System.out.printf("transferred %d\n", amount);
    afterTransfer(amount, true);
    return true;
  }

  abstract protected void beforeTransfer(long amount);

  abstract protected void afterTransfer(long amount, boolean outcome);
}
