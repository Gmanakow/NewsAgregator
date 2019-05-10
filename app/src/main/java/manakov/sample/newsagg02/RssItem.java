package manakov.sample.newsagg02;

import android.nfc.Tag;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RssItem implements Parcelable{
    private String TAG = "RssItem";

    private String  title;
    private String  description;
    private Date    date;
    private String  link;

    public String            getTitle()       { return title;       }
    public String            getDescription() { return description; }
    public Date              getDate()        { return date;        }
    public String            getLink()        { return link;        }

    public RssItem(String date, String title, String link, String description) {
        try {
            this.title = title;
            this.description = description;
            this.date = Boat.format.parse(date);
            this.link = link;
        } catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeString(Boat.format.format(date));
    }

    protected RssItem(Parcel in){
        title       = in.readString();
        description = in.readString();
        link        = in.readString();
        try {
            date = Boat.format.parse(in.readString());
        } catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    public static final Parcelable.Creator<RssItem> CREATOR = new Parcelable.Creator<RssItem>() {
        @Override
        public RssItem createFromParcel(Parcel in){
            return new RssItem(in);
        }

        @Override
        public RssItem[] newArray(int size) {
            return new RssItem[size];
        }
    };
}
