package org.izolentiy.simpleasynctask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Integer, String>{
    private WeakReference<TextView> mTextView;
    private ProgressBar mProgressBar;

    SimpleAsyncTask (ProgressBar progressBar, TextView textView) {
        mTextView = new WeakReference<>(textView);
        mProgressBar = progressBar;
    }

    @Override
    protected String doInBackground(Void... voids) {

        // Generate a random number between 0 and 10
        Random r = new Random();
        int n = r.nextInt(11);

        // Make the task take a long enough pause that we have time
        // to rotate the phone while it is running
        int s = (n + 5) * 300;

        // Sleep for the random amount of time
        try {
            // Send the time in milliseconds our thread will sleep
            publishProgress(s);
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return a String result
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        String message = "I'm going to sleep %d milliseconds";
        mTextView.get().setText(String.format(message, values[0]));
        mTextView.get().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    @Override
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
        mProgressBar.setVisibility(View.GONE);
    }
}
