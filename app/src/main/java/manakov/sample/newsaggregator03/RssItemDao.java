package manakov.sample.newsaggregator03;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RssItemDao {
    @Query("SELECT * FROM rssitem")
    List<RssItem> getAll();

    @Query("Select * FROM rssitem WHERE id IN (:itemIds)")
    List<RssItem> loadAllByIds(int[] itemIds);

    @Query("Select * FROM rssitem WHERE urlId = :urlId")
    List<RssItem> getAllByUrlId(int urlId);

    @Query("Delete FROM rssitem WHERE urlID = :urlId")
    void deleteAllByUrlId(int urlId);

    @Insert
    void insertAll(RssItem... items);

    @Delete
    void delete(RssItem item);

    @Query("DELETE FROM rssitem")
    public void clear();
}
