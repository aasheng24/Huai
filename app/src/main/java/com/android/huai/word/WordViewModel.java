package com.android.huai.word;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class WordViewModel extends ViewModel {
    private WordReposity mWordReposity;
    private LiveData<List<Word>> allWords;

    public WordViewModel(Application application){
        mWordReposity = new WordReposity(application);
        allWords = mWordReposity.getAllwords();
    }

    LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    void insert(Word word) {
        mWordReposity.insert(word);
    }
}
