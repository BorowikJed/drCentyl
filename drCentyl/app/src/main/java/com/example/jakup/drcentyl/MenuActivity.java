package com.example.jakup.drcentyl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//TODO właściwie to do wyjebania
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button buttonMain = (Button)findViewById(R.id.buttonMain);
        Button buttonAbout = (Button)findViewById(R.id.buttonAbout);

        buttonMain.setTextColor(getResources().getColor(R.color.white));
        buttonAbout.setTextColor(getResources().getColor(R.color.white));

        buttonMain.setBackgroundColor(getResources().getColor(R.color.purple));
        buttonAbout.setBackgroundColor(getResources().getColor(R.color.purple));
    }

    public void toMainApp(View v)
    {
        Intent newIntent = new Intent(this, InputActivity.class);
        startActivity(newIntent);
    }

    public void toAbout(View v)
    {
        Intent newIntent = new Intent(this, AboutActivity.class);
        startActivity(newIntent);
    }


}
