public class Main {
    public static void main(String[] args) {
        NumberPrinter printer = new NumberPrinter();
        int n = 6; // Specify the number up to which the sequence should be printed
        ThreadController controller = new ThreadController(printer, n);

        ZeroThread zeroThread = new ZeroThread(controller);
        OddThread oddThread = new OddThread(controller);
        EvenThread evenThread = new EvenThread(controller);

        zeroThread.start();
        oddThread.start();
        evenThread.start();

        try {
            zeroThread.join();
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}