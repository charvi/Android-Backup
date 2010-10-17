package android.backup.screens;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabScreen extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);			
		TabHost tabHost = getTabHost();  // The activity TabHost	    
	    decideWhatToShow(tabHost) ;

	}

	private void decideWhatToShow(TabHost tabHost) {
		Bundle bundle = getIntent().getExtras();
		Intent intent;

		if (bundle.getBoolean("contacts")) {
			intent = new Intent().setClass(this, ContactScreen.class);
			createTab(tabHost, intent, "Contacts");
		} 
		if (bundle.getBoolean("settings")) {
			intent = new Intent().setClass(this, SettingsScreen.class);
			createTab(tabHost, intent, "Settings");
		} 
		if (bundle.getBoolean("sms")) {
			intent = new Intent().setClass(this, SmsScreen.class);
			createTab(tabHost, intent, "SMS");
		} 

	}

	private void createTab(TabHost tabHost, Intent intent, String tabName) {

		TabHost.TabSpec spec;
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec(tabName).setIndicator(tabName).setContent(
				intent);
		tabHost.addTab(spec);
	}
}
