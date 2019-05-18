package manakov.sample.newsaggregator03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private TextView delayTextView;
    private EditText delayInputView;
    private NewsAggApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        application = (NewsAggApplication) getApplication();

        delayTextView  = findViewById(R.id.delayTextView);
        delayInputView = findViewById(R.id.delayInputView);

        Intent intent = getIntent();

        delayInputView.setText(intent.getExtras().getString("Delay_Input"));
        delayTextView.setVisibility(View.VISIBLE);
    }

    public void onConfirmSettingsClick(View view){
        Double delay = null;
        try {
            delay = Double.parseDouble(delayInputView.getText().toString());
        } catch (Exception e){
            Log.e("SettingsActivity", e.getLocalizedMessage(), e);
        }
        if (delay != null){
            application
                    .dataBase
                    .timeOffsetDao()
                    .clear();

            application
                    .dataBase
                    .timeOffsetDao()
                    .put(
                            new TimeOffset(delay)
                    );
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences preferences = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Last_Activity", NewsAggApplication.SETTINGS_ACTIVITY);
        editor.putString("Delay_Input", delayInputView.getText().toString());
        editor.apply();
    }
}
