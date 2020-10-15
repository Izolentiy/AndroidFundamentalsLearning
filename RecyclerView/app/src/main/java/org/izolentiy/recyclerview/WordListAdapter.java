package org.izolentiy.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private final LinkedList<String> wordList;
    private LayoutInflater inflater;

    public WordListAdapter(Context context, LinkedList<String> wordList) {
        inflater = LayoutInflater.from(context);
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String current = wordList.get(position);
        holder.wordItemView.setText(current);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        final TextView wordItemView;
        final WordListAdapter adapter;

        WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked
            int position = getLayoutPosition();
            // Use that to access the affected item in wordList
            String element = wordList.get(position);
            // Change the word in the wordList
            wordList.set(position, "Clicked! " + element);
            // Notify the adapter that the data has changed so it can
            // update the RecyclerView to display the data
            adapter.notifyDataSetChanged();
        }
    }

}
