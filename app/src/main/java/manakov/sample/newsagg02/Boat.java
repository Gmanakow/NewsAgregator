package manakov.sample.newsagg02;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Boat extends Application {
    public static String           fish   = "fish";
    public static SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    public static String           url    = "url";
    public static String           item   = "item";
    public static String           curio  = "curio";

}
