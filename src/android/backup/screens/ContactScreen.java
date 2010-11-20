package android.backup.screens;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ContactScreen extends ListActivity {
	ListAdapter mAdapter;	
	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			displayContacts();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void displayContacts() throws IOException
	{
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		OutputStream outStream = null;
		File file = new File(extStorageDirectory, "contacts.txt");
		outStream = new FileOutputStream(file);

		Cursor contactsCursor = this.managedQuery(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);

		String[] columnsToMap = new String[] {ContactsContract.Contacts.DISPLAY_NAME};
		int[] mapTo = new int[] {R.id.ContactName};
		mAdapter = new SimpleCursorAdapter(this,R.layout.contactlist,contactsCursor, columnsToMap, mapTo);
		this.setListAdapter(mAdapter);			
		SaveToFile(outStream,contactsCursor);
		outStream.flush();
		outStream.close();
	}

	private void SaveToFile(OutputStream outStream , Cursor contactsCursor) 
	{		
		while (contactsCursor.moveToNext())
		{ 
			String contactId = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID)); 
			String Dname = ContactsContract.Contacts.DISPLAY_NAME;
			int hasPhone = contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)); 
			if (hasPhone ==1) 
			{ 
				// You know have the number so now query it like this
				Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, 
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, 
						null, null);
				String phoneNumber = null;
				while (phones.moveToNext()) 
				{
					phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				}

				byte[] data1 = Dname.getBytes();
				byte[] data2 = phoneNumber.getBytes();
				try {
					outStream.write(data1);
					outStream.write(data2);
				} catch (IOException e) {
					e.printStackTrace();
				}		

				phones.close(); 
			}

			/////////////emails/////////////////////////////////////////////////////////////

			Cursor emails = getContentResolver().query( 
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
					null, 
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, 
					null, null); 
			while (emails.moveToNext()) { 
				 //This would allow you get several email addresses 
				/*String emailAddress = emails.getString( 
						emails.getColumnIndex( 
								ContactsContract.CommonDataKinds.CommonDataColumns.DATA));
				 */ 
			} 
			emails.close(); 
		} 
	}
}