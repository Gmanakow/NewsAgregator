package manakov.sample.newsagg02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import manakov.sample.newsagg02.Rss.RssItemPack;

public class FishNet extends BroadcastReceiver {
    private String TAG = "FishNet";
    private FishersCat cat = null;

    public void setCat(FishersCat cat) {
        this.cat = cat;
    }

    @Override
    public void onReceive(Context context, Intent intent){
        try{
            Log.d(TAG, "onReceive called");
            if (this.cat != null){
                Bundle bundle = intent.getExtras();
                RssItemPack items = bundle.getParcelable(Boat.fish);
                this.cat.claim(items);
            }

        } catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        }
    }
}
