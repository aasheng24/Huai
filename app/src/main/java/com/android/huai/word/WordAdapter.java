package com.android.huai.word;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.huai.R;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater inflater;
    private List<Word> words;

    public WordAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.word_recycler_item, parent,false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (words != null) {
            Word word = words.get(position);
            holder.textView.setText(word.getWord());
        } else {
            holder.textView.setText("no word");
        }

    }

    @Override
    public int getItemCount() {
        if (words != null) {
            return words.size();
        }
        return 0;
    }

    /**
     * update data
     * @param word
     */
    public void setWords(List<Word> word) {
        words = word;
        notifyDataSetChanged();
    }
}
