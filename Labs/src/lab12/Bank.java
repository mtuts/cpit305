import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
<<<<<<< HEAD
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
=======
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
>>>>>>> eb04447459beb92eeb20b9e8b4eb535d97acabad

    accounts = new double[num_of_accounts];
    for (int i = 0; i < num_of_accounts; ++i) {
      accounts[i] = balance;
    }
  }

  public int numberOfAccounts() {
    return accounts.length;
  }

  public void transfer(int from, int to, double amount) throws InterruptedException {
<<<<<<< HEAD
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
=======
<<<<<<< HEAD:305/in-class/lab10/src/Bank.java
    mutex.lock();
    try {
      //System.out.printf("from: %2d, to: %2d\n", from, to);
=======
//    mutex.lock();
//    try {
>>>>>>> eb04447459beb92eeb20b9e8b4eb535d97acabad:305/in-class/lab12/src/Bank.java
      lock[from].lock();  // 0  | 1
      lock[to].lock();    // 1  | 0
//    } finally {
//      mutex.unlock();
//    }
    try {
      // while (accounts[from] < amount){
      //   con[from].await();
      // }
      accounts[from] -= amount;
      accounts[to] += amount;

      // con[to].signal();
    } finally {
      lock[from].unlock();
      lock[to].unlock();
>>>>>>> eb04447459beb92eeb20b9e8b4eb535d97acabad
    }
  }

  public double getTotalBalance() {
    double sum = 0;
<<<<<<< HEAD
    readLock.lock();
=======
    // lock.lock();
>>>>>>> eb04447459beb92eeb20b9e8b4eb535d97acabad
    try {
      for (int i = 0; i < accounts.length; ++i) {
        sum += accounts[i];
      }
    } finally {
<<<<<<< HEAD
      readLock.unlock();
=======
      // lock.unlock();
>>>>>>> eb04447459beb92eeb20b9e8b4eb535d97acabad
    }

    return sum;
  }

  public void listAllAccounts() {

<<<<<<< HEAD
    readLock.lock();
    try {
      for (int i = 0; i < accounts.length; ++i) {
        System.out.printf("%4d: \t %.2f\n", i, accounts[i]);
      }
    } finally {
      readLock.unlock();
=======
    for (int i = 0; i < accounts.length; ++i) {
      System.out.printf("%4d: \t %.2f\n", i, accounts[i]);
>>>>>>> eb04447459beb92eeb20b9e8b4eb535d97acabad
    }
  }
}
