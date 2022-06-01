import java.util.concurrent.Semaphore;

// We use pattern Singleton in this class Connection
public class Connection {

    private static final Connection connection = new Connection(); // release pattern 'Singleton'
    private int connectionsCount; // count of connections
    // we have 200 threads, but in one moment only 10 threads have access to 'connection.work()'
    private final Semaphore semaphore = new Semaphore(10);

    // prevent the user from using the constructor
    private Connection() {
    }

    // return a reference to an already created object
    public static Connection getConnection() {
        return connection;
    }

    // we wrap the simulation of work in the methods of the 'semaphore' class
    public void work() throws InterruptedException {
        semaphore.acquire(); // take one permit for one thread
        try {
            doWork(); // thread do some work
        } finally {
            semaphore.release(); // give back one permit, always be called in final block
        }
    }

    // simulation some work
    private void doWork() throws InterruptedException {
        synchronized (this) {
            connectionsCount++; // increment the counter
            System.out.println(connectionsCount); // show counter value
        }
        Thread.sleep(5000); // sleep
        synchronized (this) {
            connectionsCount--; // decrement the counter
            System.out.println(connectionsCount); // show counter value
        }
    }
}
