package manakov.sample.newsaggregator03;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddUrlActivity extends AppCompatActivity {
    private String TAG = this.getClass().getName();
    private NewsAggApplication application;

    private EditText urlTitleInputView;
    private EditText urlInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url);

        urlTitleInputView = findViewById(R.id.urlTitleInputView);
        urlInputView      = findViewById(R.id.urlInputView     );

        urlTitleInputView .setVisibility(View.VISIBLE);
        urlInputView      .setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        urlTitleInputView.setText(intent.getExtras().getString("Title_Input"));
        urlInputView     .setText(intent.getExtras().getString("Url_Input")  );

        application = (NewsAggApplication) getApplication();
    }

    public void onConfirmClick(View view){
        application
            .dataBase
                .urlItemDao()
                    .insertAll(
                        new UrlItem(
                            urlTitleInputView.getText().toString(),
                            urlInputView     .getText().toString()
                        )
                    );
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences preferences = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Last_Activity", NewsAggApplication.ADD_URL_ACTIVITY);
        editor.putString("Title_Input", urlTitleInputView.getText().toString());
        editor.putString("Url_Input"  , urlInputView     .getText().toString());
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
