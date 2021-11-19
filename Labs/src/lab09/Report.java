package lab09;

public class Report extends Thread {
  private Bank bank;

  public Report(Bank bank) {
    this.bank = bank;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        System.err.println(e.getMessage());
      }

      System.out.println("\n\n------------------- REPORT ----------------------");
      System.out.printf("Bank total balance: %.2f\n", bank.getTotalBalance());
      bank.listAllAccounts();
    }
  }
}
