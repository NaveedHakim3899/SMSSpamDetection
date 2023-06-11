package com.example.naveed.ownsmsapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private Toolbar mainactivitytoolbar;
    private ViewPager mainPager;
    private MyPageAdapter mainPagerAdapter;
    private TabLayout mainTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainactivitytoolbar= (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mainactivitytoolbar);
        //setActionBar(mainactivitytoolbar);
        getSupportActionBar().setTitle("SMS Application");
        mainPager= (ViewPager) findViewById(R.id.main_tabPager);
        //view pager adapter
        mainPagerAdapter= new MyPageAdapter(getSupportFragmentManager());
        mainPager.setAdapter(mainPagerAdapter);
        //
        mainTab= (TabLayout) findViewById(R.id.main_tabs);
        mainTab.setupWithViewPager(mainPager);

       Intent i = new Intent(MainActivity.this,newservice.class);
        startService(i);
    }
}
