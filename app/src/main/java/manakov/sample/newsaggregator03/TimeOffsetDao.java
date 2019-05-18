package manakov.sample.newsaggregator03;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TimeOffsetDao {

    @Query("Select * From timeoffset")
    List<TimeOffset> get();

    @Insert
    void put(TimeOffset timeOffset);

    @Query("Delete from timeoffset")
    void clear();


}
