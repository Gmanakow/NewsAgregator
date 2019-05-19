package manakov.sample.newsaggregator03;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UrlListActivity extends AppCompatActivity {
    private String TAG = this.getClass().getName();

    private NewsAggApplication application;

    private RecyclerView recyclerView;
    private CustomRecyclerViewUrlItemAdapter adapter;
    private ArrayList<UrlItem> list;

    private View.OnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (NewsAggApplication) getApplication();

        list = new ArrayList<>();
        list.addAll(
                application
                        .dataBase
                        .urlItemDao()
                        .getAll()
        );

        recyclerView = findViewById(R.id.urlRecyclerView);
        recyclerView.destroyDrawingCache();
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(view.getContext(), UrlRssItemDisplayActivity.class);
                intent.putExtra("Title", list.get(position).getTitle());
                intent.putExtra("ID"   , list.get(position).getId());
                startActivityForResult(intent, NewsAggApplication.showUrlTag);
            }
        };

        SharedPreferences preferences = getSharedPreferences("X", MODE_PRIVATE);
        String last_activity = preferences.getString("Last_Activity", NewsAggApplication.URL_LIST_ACTIVITY);
        SharedPreferences.Editor editor = preferences.edit();

        switch (last_activity) {
            case "1":
                editor.clear();
                editor.apply();
                break;
            case "2":
                Intent intent = new Intent(this, UrlRssItemDisplayActivity.class);
                String last_title = null;
                int last_id = 0;
                last_title = preferences.getString("last_title", null);
                last_id = preferences.getInt("last_id", -1);
                editor.clear();
                editor.apply();
                if ((last_title != null) && (last_id != -1)) {
                    intent.putExtra("Title", last_title);
                    intent.putExtra("ID", last_id);
                    startActivityForResult(intent, NewsAggApplication.showUrlTag);
                }
                break;
            case "3":
                intent = new Intent(this, AddUrlActivity.class);
                intent.putExtra("Title_Input", preferences.getString("Title_Input", ""));
                intent.putExtra("Url_Input", preferences.getString("Url_Input", ""));
                editor.clear();
                editor.apply();
                startActivityForResult(intent, NewsAggApplication.addUrlTag);
                break;
            case "4":
                intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("Delay_Input", preferences.getString("Delay_Input", ""));
                editor.clear();
                editor.apply();
                startActivityForResult(intent, NewsAggApplication.settingTag);
                break;
        }

        adapter = new CustomRecyclerViewUrlItemAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onClickListener);
        setupDelay();
        refreshAll();
    }

    public void onAddClick(View view){
        Intent intent = new Intent(this, AddUrlActivity.class);
        intent.putExtra("Title_Input", "");
        intent.putExtra("Url_Input", "");
        startActivityForResult(intent, NewsAggApplication.addUrlTag);
    }

    public void onSettingsClick(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("Delay_Input", "");
        startActivityForResult(intent, NewsAggApplication.settingTag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            list.clear();
            list.addAll(
                    application
                    .dataBase
                    .urlItemDao()
                    .getAll()
            );
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        list.clear();
        list.addAll(
                application
                        .dataBase
                        .urlItemDao()
                        .getAll()
        );
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences preferences = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Last_Activity", NewsAggApplication.URL_LIST_ACTIVITY);
        editor.apply();
    }

    public void onSetUpClick(View view) {
        application
                .dataBase
                .urlItemDao()
                .clear();

        application
                .dataBase
                .urlItemDao()
                .insertAll(
                        new UrlItem(
                                "Mail main",
                                " https://news.mail.ru/rss/main/91/"
                        )
                );

        application
                .dataBase
                .urlItemDao()
                .insertAll(
                        new UrlItem(
                                "Economics",
                                " https://news.mail.ru/rss/economics/91/"
                        )
                );

        application
                .dataBase
                .urlItemDao()
                .insertAll(
                        new UrlItem(
                                "3d news hardware",
                                "https://3dnews.ru/news/rss/"
                        )
                );

        list.clear();
        list.addAll(
                application
                        .dataBase
                        .urlItemDao()
                        .getAll()
        );
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        refreshAll();
    }

    public void refreshAll(){
        Intent intent = new Intent(this, RssItemFisherService.class);
        startService(intent);
    }
    public void setupDelay(){
        application
                .dataBase
                .timeOffsetDao()
                .clear();

        application
                .dataBase
                .timeOffsetDao()
                .put(
                        new TimeOffset(10)
                );
    }
}
