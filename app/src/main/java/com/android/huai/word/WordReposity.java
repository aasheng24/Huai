package com.android.huai.word;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.huai.utils.AppExecutorUtil;

import java.util.List;

public class WordReposity {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    public WordReposity(Application application){
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        wordDao = db.wordDao();
        allWords = wordDao.getAlphabtizedWords();
    }

    LiveData<List<Word>> getAllwords(){
        return allWords;
    }

    void insert(final Word word) {
        AppExecutorUtil.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                wordDao.insert(word);
            }
        });
    }

}
