class ZeroThread extends Thread {
    private final ThreadController controller;

    public ZeroThread(ThreadController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            controller.printZero();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class OddThread extends Thread {
    private final ThreadController controller;

    public OddThread(ThreadController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            controller.printOdd();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class EvenThread extends Thread {
    private final ThreadController controller;

    public EvenThread(ThreadController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            controller.printEven();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}