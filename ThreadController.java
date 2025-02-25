import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ThreadController {
    private final NumberPrinter printer;
    private final int n;
    private final Lock lock = new ReentrantLock();
    private final Condition zeroCondition = lock.newCondition();
    private final Condition oddCondition = lock.newCondition();
    private final Condition evenCondition = lock.newCondition();
    private boolean zeroTurn = true;
    private boolean oddTurn = false;
    private boolean evenTurn = false;
    private int counter = 1;

    public ThreadController(NumberPrinter printer, int n) {
        this.printer = printer;
        this.n = n;
    }

    public void printZero() throws InterruptedException {
        lock.lock();
        try {
            while (counter <= n) {
                if (!zeroTurn) {
                    zeroCondition.await();
                }
                if (counter > n) {
                    break;
                }
                printer.printZero();
                zeroTurn = false;
                if (counter % 2 == 1) {
                    oddTurn = true;
                    oddCondition.signal();
                } else {
                    evenTurn = true;
                    evenCondition.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void printOdd() throws InterruptedException {
        lock.lock();
        try {
            while (counter <= n) {
                if (!oddTurn) {
                    oddCondition.await();
                }
                if (counter > n) {
                    break;
                }
                printer.printOdd(counter);
                counter++;
                oddTurn = false;
                zeroTurn = true;
                zeroCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void printEven() throws InterruptedException {
        lock.lock();
        try {
            while (counter <= n) {
                if (!evenTurn) {
                    evenCondition.await();
                }
                if (counter > n) {
                    break;
                }
                printer.printEven(counter);
                counter++;
                evenTurn = false;
                zeroTurn = true;
                zeroCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}