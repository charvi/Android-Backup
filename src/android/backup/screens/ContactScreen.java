package android.backup.screens;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class ContactScreen extends ListActivity {
	CursorAdapter mAdapter;
	
	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		displayContacts();		
	}

	public void displayContacts()
	{
			Cursor contactsCursor = this.managedQuery(ContactsContract.Contacts.CONTENT_URI,
					null, null, null, null);
			String[] columnsToMap = new String[] {ContactsContract.Contacts.DISPLAY_NAME};			
			int[] mapTo = new int[] {R.id.TextView01};
			mAdapter = new SimpleCursorAdapter(this,R.layout.list,contactsCursor, columnsToMap, mapTo);
			this.setListAdapter(mAdapter);
	}
}



