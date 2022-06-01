import java.util.concurrent.*;

// in this example we use class Semaphore for release one server and many threads

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(200);

        Connection connection = Connection.getConnection(); // no constructor for connection, only give back existing connection

        for (int i = 0; i < 200; i++) { // give jobs to 200 threads
            executorService.submit(new Runnable() { // anonymous class with implementation interface Runnable
                @Override
                public void run() {
                    try {
                        connection.work(); // thread do some work
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown(); // disable all threads;
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
