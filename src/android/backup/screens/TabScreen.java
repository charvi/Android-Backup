package android.backup.screens;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabScreen extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        TabHost tabHost = getTabHost();  // The activity TabHost
        decideWhatToShow(tabHost);

    }

    private void decideWhatToShow(TabHost tabHost) {
        Bundle bundle = getIntent().getExtras();
        Intent intent;

        if (bundle.getBoolean("contacts")) {
            intent = new Intent().setClass(this, ContactScreen.class);
            createTab(tabHost, intent, "Contacts");
        }
        if (bundle.getBoolean("sms")) {
            intent = new Intent().setClass(this, SmsScreen.class);
            createTab(tabHost, intent, "SMS");
        }
        if (bundle.getBoolean("calender")) {
            intent = new Intent().setClass(this, CalendarScreen.class);
            createTab(tabHost, intent, "Calender");
        }
        if (bundle.getBoolean("bookmarks")) {
            intent = new Intent().setClass(this, BookmarkScreen.class);
            createTab(tabHost, intent, "Bookmarks");
        }

    }

    private void createTab(TabHost tabHost, Intent intent, String tabName) {

        TabHost.TabSpec spec;
        Bundle bundle = getIntent().getExtras();

        // Initialize a TabSpec for each tab and add it to the TabHost
        if (bundle.getBoolean("read") == true) {
            intent.putExtra("read",true);
        }
        spec = tabHost.newTabSpec(tabName).setIndicator(tabName).setContent(
                intent);
        tabHost.addTab(spec);
    }
}
