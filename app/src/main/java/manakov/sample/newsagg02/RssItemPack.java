package manakov.sample.newsagg02;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;


public class RssItemPack implements Parcelable {
    private ArrayList<RssItem> list = null;

    public RssItemPack(){
        this.list = new ArrayList<RssItem>();
    }
    public int size(){
        return list.size();
    }
    public void add(RssItem item){
        list.add(item);
    }
    public void addAll(RssItemPack items){
        for (int i= 0; i< items.size(); i++){
            this.list.add(items.get(i));
        }
    }
    public RssItem get(int i){
        return list.get(i);
    }
    public ArrayList<RssItem> getList(){
        return this.list;
    }
    public void clear(){
        list.clear();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.size());
        RssItem item;
        for (int i = 0; i< this.size(); i++){
            item = this.get(i);
            dest.writeString( Boat.format.format(item.getDate())       );
            dest.writeString(                    item.getTitle()       );
            dest.writeString(                    item.getLink()        );
            dest.writeString(                    item.getDescription() );
        }
    }

    private RssItemPack(Parcel in){
        this.list = new ArrayList<RssItem>();
        int size = in.readInt();
        for (int i = 0; i<size; i++){
            this.list.add(new RssItem(
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString()
            ));
        }
    }

    public static final Parcelable.Creator<RssItemPack> CREATOR = new Parcelable.Creator<RssItemPack>(){
        public RssItemPack createFromParcel(Parcel in){
            return new RssItemPack(in);
        }

        public RssItemPack[] newArray(int size){
            return new RssItemPack[size];
        }
    };
}
