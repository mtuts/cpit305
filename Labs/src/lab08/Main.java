package lab08;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    Bank bank = new Bank(10, 1000);
    System.out.printf("Bank total balance: %.2f\n", bank.getTotalBalance());
    bank.listAllAccounts();

    Report report = new Report(bank);
    report.start();


    ATM[] atm = new ATM[bank.numberOfAccounts()];
    for (int i = 0; i < atm.length; i++) {
      atm[i] = new ATM(bank, i);
      atm[i].start();
    }
    
    Monitor monitor = new Monitor(atm);
    monitor.start();

    for (int i = 0; i < atm.length; ++i) {
      atm[i].join();
    }
  }
}
