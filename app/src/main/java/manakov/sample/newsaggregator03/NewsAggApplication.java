package manakov.sample.newsaggregator03;

import android.app.Application;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.room.Room;

public class NewsAggApplication extends Application {
    public CustomDatabase dataBase;

    public static SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    public static String           item   = "item";
    public static final int addUrlTag = 0;
    public static final int showUrlTag = 1;
    public static final int settingTag = 2;
    public static String URL_LIST_ACTIVITY             = "1";
    public static String URL_RSS_ITEM_DISPLAY_ACTIVITY = "2";
    public static String ADD_URL_ACTIVITY              = "3";
    public static String SETTINGS_ACTIVITY             = "4";

    @Override
    public void onCreate(){
        super.onCreate();

        dataBase = Room.databaseBuilder(getApplicationContext(), CustomDatabase.class, "new db")
                .allowMainThreadQueries()
                .build();

    }


}
