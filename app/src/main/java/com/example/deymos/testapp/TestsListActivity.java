package com.example.deymos.testapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class TestsListActivity extends ActionBarActivity {
    public static final String EXTRA_MESSAGE = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_list);
    }


}
