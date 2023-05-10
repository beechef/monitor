package Server;

import Server.EventDispatcher.Executable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntervalThread {
    private final float seconds;
    private Thread thread;

    private final List<Executable> actions = new ArrayList<>();

    public boolean isRunning;

    public IntervalThread(float seconds) {
        this.seconds = seconds;
    }

    public void addAction(Executable action) {
        actions.add(action);
    }

    public void removeAction(Executable action) {
        actions.remove(action);
    }

    public void start() {
        if (thread != null) thread.interrupt();

        thread = new Thread(this::run);
        thread.start();

        isRunning = true;
    }

    private void run() {
        while (true) {
            try {
                Thread.sleep((long) (seconds * 1000));
            } catch (InterruptedException ignored) {
            }

            if (thread.isInterrupted() || !isRunning) {
                thread.interrupt();
                return;
            }

            for (var action : actions) {
                try {
                    action.execute();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}
