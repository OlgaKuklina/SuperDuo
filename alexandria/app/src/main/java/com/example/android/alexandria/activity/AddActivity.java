package com.example.android.alexandria.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.alexandria.R;
import com.example.android.alexandria.fragment.AddBookFragment;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragmentAdd = new AddBookFragment();
        fragmentAdd.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_add, fragmentAdd).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
