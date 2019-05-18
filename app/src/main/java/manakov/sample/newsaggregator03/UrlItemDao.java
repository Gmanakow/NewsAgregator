package manakov.sample.newsaggregator03;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UrlItemDao {
    @Query("SELECT * FROM urlitem")
    List<UrlItem> getAll();

    @Query("DELETE FROM urlitem WHERE id = :id")
    void deleteAllbyId(int id);

    @Insert
    void insertAll(UrlItem... items);

    @Delete
    void delete(UrlItem item);

    @Query("DELETE FROM urlItem")
    public void clear();
}
