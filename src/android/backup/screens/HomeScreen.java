package android.backup.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class HomeScreen extends Activity {
    /**
     * Called when the activity is first created.
     */

    CheckBox Contacts, Settings, Sms, Calender, Bookmarks;
    ContactScreen displayBackup = new ContactScreen();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Contacts = (CheckBox) findViewById(R.id.CheckBox01);
        Sms = (CheckBox) findViewById(R.id.CheckBox03);
        Calender = (CheckBox) findViewById(R.id.CheckBox04);
        Bookmarks = (CheckBox) findViewById(R.id.CheckBox05);

    }

    public void SubmitClickHandler(View views) {
        Intent i = new Intent(this, TabScreen.class);
        i.putExtra("contacts", Contacts.isChecked());
        i.putExtra("sms", Sms.isChecked());
        i.putExtra("calender", Calender.isChecked());
        i.putExtra("bookmarks", Bookmarks.isChecked());
        startActivity(i);
    }

    public void RestoreClickHandler(View views) {
        Intent i = new Intent(this, TabScreen.class);
        i.putExtra("contacts", Contacts.isChecked());
        i.putExtra("sms", Sms.isChecked());
        i.putExtra("calender", Calender.isChecked());
        i.putExtra("bookmarks", Bookmarks.isChecked());
        i.putExtra("read", true);
        startActivity(i);
    }

}

