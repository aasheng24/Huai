package com.android.huai.word;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.huai.utils.AppExecutorUtil;

@Database(entities = Word.class, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    abstract WordDao wordDao();

    private static volatile WordRoomDatabase sInstance;
    static WordRoomDatabase getDatabase(Context context) {
        if (sInstance == null) {
            synchronized (WordRoomDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),WordRoomDatabase.class,"word_database")
                            .build();
                }
            }
        }
        return sInstance;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            AppExecutorUtil.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    /*WordDao dao = INSTANCE.wordDao();
                    dao.deleteAll();

                    Word word = new Word("Hello");
                    dao.insert(word);
                    word = new Word("World");
                    dao.insert(word);*/
                }
            });
        }
    };
}
