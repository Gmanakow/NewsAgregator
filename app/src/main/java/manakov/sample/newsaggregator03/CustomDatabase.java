package manakov.sample.newsaggregator03;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UrlItem.class, RssItem.class, TimeOffset.class}, version = 1, exportSchema = false)
public abstract class CustomDatabase extends RoomDatabase {
    public abstract UrlItemDao urlItemDao();
    public abstract RssItemDao rssItemDao();
    public abstract TimeOffsetDao timeOffsetDao();
}
