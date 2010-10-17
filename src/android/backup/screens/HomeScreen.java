package android.backup.screens;

import android.app.Activity;
import android.backup.screens.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class HomeScreen extends Activity {
	/** Called when the activity is first created. */

	CheckBox Contacts,Settings,Sms;
	ContactScreen displayBackup = new ContactScreen();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Contacts = (CheckBox)findViewById(R.id.CheckBox01);
		Settings = (CheckBox)findViewById(R.id.CheckBox02);
		Sms = (CheckBox)findViewById(R.id.CheckBox03);
	}

	public void OnClickHandler(View views)
	{
		Intent i = new Intent(this, TabScreen.class);
		i.putExtra("contacts",Contacts.isChecked() );
		i.putExtra("settings",Settings.isChecked() );
		i.putExtra("sms",Sms.isChecked() );
		Contacts.setChecked(false);
		Settings.setChecked(false);
		Sms.setChecked(false);
		startActivity(i);
	}

}

