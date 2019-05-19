package manakov.sample.newsaggregator03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class UrlRssItemDisplayActivity extends AppCompatActivity{
    private String TAG = this.getClass().getName();

    private TextView titleDisplayView;

    private String currentUrl;
    private int    currentId;

    private NewsAggApplication application;

    private RecyclerView recyclerView;
    private CustomRecyclerViewRssItemAdapter adapter;
    private ArrayList<RssItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_rss_item_display);

        application = (NewsAggApplication) getApplication();

        titleDisplayView = findViewById(R.id.titleDisplayView);
        titleDisplayView.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.urlfeedRecycleView);
        recyclerView.destroyDrawingCache();
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        currentUrl = intent.getExtras().getString ("Title");
        currentId  = intent.getExtras().getInt    ("ID" );

        titleDisplayView.setText(currentUrl);

        list = new ArrayList<>();
        list.clear();
        list.addAll(
                application
                .dataBase
                .rssItemDao()
                .getAllByUrlId(currentId)
        );
        adapter = new CustomRecyclerViewRssItemAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    public void onDeleteClick(View view){
        application
                .dataBase
                .urlItemDao()
                .deleteAllbyId(currentId);
        application
                .dataBase
                .rssItemDao()
                .deleteAllByUrlId(currentId);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        list.clear();
        list.addAll(
                application
                .dataBase
                .rssItemDao()
                .getAllByUrlId(currentId)
        );
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Last_Activity", NewsAggApplication.URL_RSS_ITEM_DISPLAY_ACTIVITY);
        editor.putString("last_title", currentUrl);
        editor.putInt   ("last_id", currentId);

        editor.apply();
    }
}
