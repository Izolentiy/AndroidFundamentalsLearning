package org.izolentiy.roomwordssample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    public abstract WordDao wordDao();
    private static WordRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                // Create database here
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        WordRoomDatabase.class, "word_database")
                        // Wipes and rebuilds instead of migrating
                        // if no migration object.
                        // Migration is not part of this practical.
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback).build();
            }
        }
        return INSTANCE;
    }



    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final WordDao mDao;
        String[] words = {"tiger", "dolphin", "elephant", "lion"};

        private PopulateDbAsync(WordRoomDatabase db) {
            this.mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            // mDao.deleteAll();
            if (mDao.getAnyWord().length < 1){
                for (int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i]);
                    mDao.insert(word);
                }
            }
            return null;
        }
    }

}
