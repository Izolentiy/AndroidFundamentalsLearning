package org.izolentiy.roomwordssample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Delete
    void delete(Word word);

    @Query("delete from word_table")
    void deleteAll();

    @Query("select * from word_table order by word asc")
    LiveData<List<Word>> getAllWords();

    @Query("select * from word_table limit 1")
    Word[] getAnyWord();

}
