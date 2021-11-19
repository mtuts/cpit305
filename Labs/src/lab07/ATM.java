package lab07;

public class ATM extends Thread {

  private Bank bank;
  private int id;

  public ATM(Bank bank, int id) {
    this.bank = bank;
    this.id = id;
  }

  @Override
  public void run() {
    while (true) {
      int from = id, to = 0;
      for (int i = 0; i < bank.numberOfAccounts(); ++i) {
        if (i == id) continue;
        to = i;
        double amount = Math.random() * 1000.0;
  
        try {
          bank.transfer(from, to, amount);
        } catch (InterruptedException e) {
          System.err.println(e.getMessage());
        }
      }
    }
  }
}
