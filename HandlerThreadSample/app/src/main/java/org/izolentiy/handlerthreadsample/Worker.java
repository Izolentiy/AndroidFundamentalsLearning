package org.izolentiy.handlerthreadsample;

import android.os.Handler;
import android.os.HandlerThread;

public class Worker extends HandlerThread {
    private static final String TAG = "Worker";
    private final Handler handler;

    public Worker() {
        super(TAG);
        start();
        // Creating new Handler for task/message sending in background thread
        handler = new Handler(getLooper());
    }

    public Worker execute(Runnable task) {
        handler.post(task);
        return this;
    }
}
