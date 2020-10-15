package org.izolentiy.handlerthreadsample;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleWorker extends Thread {
    // Simpleorker implementation using

    private static final String TAG = "SimpleWorker";

    // thread completion flag
    private final AtomicBoolean isAlive = new AtomicBoolean(true);

    // Queue of tasks
    // Used thread-safe implementation of queue
    // because it is accessed from different threads
    private final ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

    public SimpleWorker() {
        super(TAG);
        start();  // Thread starts
    }

    @Override
    public void run() {
        // Eternal loop of message queue until quit() method call
        while(isAlive.get()) {
            Runnable task = queue.poll();
            if (task != null) {
                task.run();
            }
        }
        Log.d(TAG, "Simple worker terminated");  // method quit() called
    }

    public SimpleWorker execute(Runnable task) {
        queue.add(task);
        return this;
    }

    public void quit() {
        isAlive.set(false);
    }
}
