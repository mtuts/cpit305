import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
  private double[] accounts;
  private Lock[] lock;
  private Lock mutex;
  private Condition[] con;

  public Bank(int num_of_accounts, double balance) {
    mutex = new ReentrantLock();
    lock = new ReentrantLock[num_of_accounts];
    con = new Condition[num_of_accounts];
    for (int i = 0; i < num_of_accounts; ++i) {
      lock[i] = new ReentrantLock();
      con[i] = lock[i].newCondition();
    }

    accounts = new double[num_of_accounts];
    for (int i = 0; i < num_of_accounts; ++i) {
      accounts[i] = balance;
    }
  }

  public int numberOfAccounts() {
    return accounts.length;
  }

  public void transfer(int from, int to, double amount) throws InterruptedException {
    mutex.lock();
    try {
      lock[from].lock();  // 0  | 1
      lock[to].lock();    // 1  | 0
    } finally {
      mutex.unlock();
    }
    try {
      while (accounts[from] < amount){
        con[from].await();
      }
      accounts[from] -= amount;
      accounts[to] += amount;

      con[to].signalAll();
    } finally {
      mutex.lock();
      try {
        lock[from].unlock();
        lock[to].unlock();
      } finally {
        mutex.unlock();
      }
    }
  }

  public double getTotalBalance() {
    double sum = 0;
    // lock.lock();
    try {
      for (int i = 0; i < accounts.length; ++i) {
        sum += accounts[i];
      }
    } finally {
      // lock.unlock();
    }

    return sum;
  }

  public void listAllAccounts() {

    for (int i = 0; i < accounts.length; ++i) {
      System.out.printf("%4d: \t %.2f\n", i, accounts[i]);
    }
  }
}
