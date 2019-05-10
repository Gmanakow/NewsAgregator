package manakov.sample.newsagg02;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements FishersCat {
    private String   TAG = "Main";
    private FishNet  fishNet;
    private EditText inputURL;

    private RssItemPack         rssItemPack = new RssItemPack();
    private RssItemArrayAdapter adapter     = null;
    private ListView            rssListView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputURL    = findViewById(R.id.inputURL);
        rssListView = findViewById(R.id.rssListView);
        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, Integer.toString(position));
                Intent intent = new Intent(parent.getContext(), ItemDisplayerActivity.class);
                intent.putExtra(Boat.curio, rssItemPack.get(position));
                startActivity(intent);
            }
        });
        addNet();

        adapter = new RssItemArrayAdapter(this, rssItemPack);
        rssListView.setAdapter(adapter);
        onClick(null);
    }

    public void onClick(View view){
        Log.d(TAG, "onClick called");
        try {
            Intent intent = new Intent(this, RssFisher.class);
            intent.putExtra(Boat.url, inputURL.getText().toString());
            startService(intent);
        } catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    public void addNet(){
        this.fishNet = new FishNet();
        this.fishNet.setCat(this);
        try{
            IntentFilter fishFilter = new IntentFilter();
            fishFilter.addAction(Boat.fish);
            registerReceiver(fishNet, fishFilter);
        } catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    public void claim(RssItemPack items){
        Log.d(TAG, "claimed");
        rssItemPack.clear();
        rssItemPack.addAll(items);
        Log.d(TAG, Integer.toString(rssItemPack.size()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(fishNet);
    }
}
