package manakov.sample.newsagg02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ItemDisplayerActivity extends AppCompatActivity {
    private TextView dateView;
    private TextView titleView;
    private TextView linkView;
    private TextView descriptionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_displayer);

        Intent intent = getIntent();
        RssItem item = intent.getParcelableExtra(Boat.curio);


        dateView        = findViewById(R.id.dateView       ); dateView        .setVisibility(View.VISIBLE); dateView        .setText( Boat.format.format(item.getDate())      );
        titleView       = findViewById(R.id.titleView      ); titleView       .setVisibility(View.VISIBLE); titleView       .setText( item.getTitle()                         );
        linkView        = findViewById(R.id.linkView       ); linkView        .setVisibility(View.VISIBLE); linkView        .setText( item.getLink()                          );
        descriptionView = findViewById(R.id.descriptionView); descriptionView .setVisibility(View.VISIBLE); descriptionView .setText( item.getDescription()                   );
    }
}
