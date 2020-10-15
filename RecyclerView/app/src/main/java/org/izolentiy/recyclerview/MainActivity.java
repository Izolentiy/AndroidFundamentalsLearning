package org.izolentiy.recyclerview;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private final LinkedList<String> wordList = new LinkedList<>();
    private RecyclerView recyclerView;
    private WordListAdapter adapter;
    private int initCount = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create a data set
        for (int i = 0; i < initCount; i++) {
            wordList.addLast("Word " + i);
        }

        // Get a handle to the RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and the data to be displayed
        adapter = new WordListAdapter(this, wordList);
        // Connect the adapter with the RecyclerView
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set a ClickListener on recyclerView
        /*
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wordListSize = wordList.size();
                // Add a new word to teh wordList
                wordList.addLast("+ Word " + wordListSize);
                // Notify the adapter, that the data has changed
                recyclerView.getAdapter().notifyItemInserted(wordListSize);
                // Scroll to the bottom
                recyclerView.smoothScrollToPosition(wordListSize);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            // Clear a data set
            wordList.clear();
            // Fill a data set again
            for (int i = 0; i < initCount; i++) {
                wordList.addLast("Word " + i);
            }
            recyclerView.getAdapter().notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
