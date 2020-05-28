package com.android.huai.word;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabtizedWords();

    @Insert
    void insert(Word word);

    @Delete
    void delete(Word word);
}
