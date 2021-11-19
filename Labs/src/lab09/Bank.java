package lab09;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank {
  private double[] accounts;
  private ReentrantReadWriteLock rw;
  private Lock readLock;
  private Lock writeLock;
  private Condition con;

  public Bank(int num_of_accounts, double balance) {
    rw = new ReentrantReadWriteLock();
    readLock = rw.readLock();
    writeLock = rw.writeLock();
    con = writeLock.newCondition();

    accounts = new double[num_of_accounts];
    for (int i = 0; i < num_of_accounts; ++i) {
      accounts[i] = balance;
    }
  }

  public int numberOfAccounts() {
    return accounts.length;
  }

  public void transfer(int from, int to, double amount) throws InterruptedException {
    writeLock.lock();
    try {
      while (accounts[from] < amount){
        con.await();
      }
      accounts[from] -= amount;
      accounts[to] += amount;

      con.signalAll();
    } finally {
      writeLock.unlock();
    }
  }

  public double getTotalBalance() {
    double sum = 0;
    readLock.lock();
    try {
      for (int i = 0; i < accounts.length; ++i) {
        sum += accounts[i];
      }
    } finally {
      readLock.unlock();
    }

    return sum;
  }

  public void listAllAccounts() {

    readLock.lock();
    try {
      for (int i = 0; i < accounts.length; ++i) {
        System.out.printf("%4d: \t %.2f\n", i, accounts[i]);
      }
    } finally {
      readLock.unlock();
    }
  }
}
