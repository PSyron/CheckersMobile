package pl.checkersmobile.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.checkersmobile.R;

public class BaseAppBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppBarStyle); 
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
