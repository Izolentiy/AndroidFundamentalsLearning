package org.izolentiy.roomwordssample;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NewWordDialog.DialogListener{
    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new NewWordDialog();
                dialog.show(getSupportFragmentManager(), "NewWordDialog");
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        // Add a functionality to swipe items in the
        // RecyclerView to delete that item.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.
                SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Word myWord = adapter.getWordAtPosition(position);

                // Delete the word
                mWordViewModel.delete(myWord);
            }
        });
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new WordListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Word word = adapter.getWordAtPosition(position);
                Bundle args = new Bundle();
                args.putString("WORD_KEY", word.getWord());
                args.putInt("ID_KEY", word.getId());
                DialogFragment dialog = new NewWordDialog();
                dialog.setArguments(args);
                Log.d("DEBUG_TAG", "adapter.setOnClickListener");
                dialog.show(getSupportFragmentManager(), "NewWordDialog");
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
        switch (item.getItemId()) {
            case R.id.action_deleteAll:
                // Add a Toast for info.
                Toast.makeText(this, "Clearing the data...",
                        Toast.LENGTH_SHORT).show();
                mWordViewModel.deleteAll();
                return true;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(NewWordDialog dialog) {
        // Extract data from dialog's arguments
        Bundle args = dialog.getArguments();
        String word_data = args.getString("WORD_KEY", "lol");
        int id = args.getInt("ID_KEY", -1);

        Word word = new Word(word_data);
        if(word_data.length() == 0)
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
        else
            if (id != -1) {
                Log.d("DEBUG_TAG", "update: " + word.getWord());
                mWordViewModel.update(new Word(word_data, id));
            } else {
                Log.d("DEBUG_TAG", "insert: " + word.getWord());
                mWordViewModel.insert(word);
            }
    }
}
