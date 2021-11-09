package org.yao.five;

public class TransferServiceImpl implements TransferService {
  private AccountRepository accountRepository;

  public TransferServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public void transfer(final double amount, final String from, final String to) {
    System.out.printf("transferred %f from %s to %s\n", amount, from, to);
  }
}
