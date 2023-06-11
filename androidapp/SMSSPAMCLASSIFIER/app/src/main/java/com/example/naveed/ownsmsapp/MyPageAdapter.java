package com.example.naveed.ownsmsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                Inbox_Messages inbox_messages = new Inbox_Messages();
                return inbox_messages;
            case 1:
                Spam_Messages spam_messages = new Spam_Messages();
                return spam_messages;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "INBOX";

            case 1:
                return "SPAM";

            default:
                return null;
        }

    }
}
