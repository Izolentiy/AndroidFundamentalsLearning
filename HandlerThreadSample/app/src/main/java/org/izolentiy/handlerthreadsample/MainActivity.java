package org.izolentiy.handlerthreadsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        // Вызывается в главном потоке
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // Отображает результат
            text.setText((String) msg.obj);
        }
    };

    private TextView text;
    // Наш фоновый поток для выполнения задач
    private HandlerThread worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инциализируем текстовое поле для вывода результата
        text = findViewById(R.id.text);
        text.setText("Task 1 in progress");

        // Создаём фоновый поток
        worker = new HandlerThread("Worker");
        worker.start();
        final Handler workerHandler = new Handler(worker.getLooper());

        // Инициируем выполнение задач в фоновом потоке
        workerHandler.post(
                new Runnable() {
                    // Вызывается в фоновом потоке
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Передаём результат в главный (UI) поток через handler
                        Message message = Message.obtain();
                        message.obj = "Task 1 completed";
                        handler.sendMessage(message);
                    }
                });
        workerHandler.post(
                new Runnable() {
                    // Вызывается в фоновом потоке
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Передаём результат в главный (UI) поток через handler
                        Message message = Message.obtain();
                        message.obj = "Task 2 completed";
                        handler.sendMessage(message);
                    }
                });
        workerHandler.post(
                new Runnable() {
                    // Вызывается в фоновом потоке
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Передаём результат в главный (UI) поток через handler
                        Message message = Message.obtain();
                        message.obj = "Task 3 completed";
                        handler.sendMessage(message);
                    }
                });
        workerHandler.post(
                new Runnable() {
                    // Вызывается в фоновом потоке
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Передаём результат в главный (UI) поток через handler
                        Message message = Message.obtain();
                        message.obj = "Task 4 completed";
                        handler.sendMessage(message);
                    }
                });
        workerHandler.post(
                new Runnable() {
                    // Вызывается в фоновом потоке
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Передаём результат в главный (UI) поток через handler
                        Message message = Message.obtain();
                        message.obj = "Task 5 completed";
                        handler.sendMessage(message);
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        worker.quit();
    }

}
