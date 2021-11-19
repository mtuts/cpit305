package lab09;

public class Monitor extends Thread {
    private Thread[] threads;

    public Monitor(Thread[] threads) {
        this.threads = threads;
    }

    @Override
    public void run() {
        while (true) {
            int c = 0;
            for (int i = 0; i < threads.length; ++i) {
                if (threads[i].getState() == State.WAITING || threads[i].getState() == State.BLOCKED) {
                    c++;
                }
            }
            if (c == threads.length) {
                System.out.println("Deadlock detected!");
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

        }
    }
    
}
