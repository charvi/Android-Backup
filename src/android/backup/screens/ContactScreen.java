package android.backup.screens;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ContactScreen extends Activity {
    ListAdapter mAdapter;
    private static final int DISPLAY_NAME = 10;
    private Cursor phones;
    private String phoneNumber;
    private String displayName;
    TextView t;

    @Override
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.contactlist);
        TextView contactcnumber = (TextView)findViewById(R.id.ContactNumber) ;



        try {
            displayContacts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayContacts() throws IOException {


        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, "contacts.txt");
        FileWriter text = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(text);


        Cursor contactsCursor = this.managedQuery(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        String[] columnsToMap = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
        int[] mapTo = new int[]{R.id.ContactName};
        mAdapter = new SimpleCursorAdapter(this, R.layout.contactlist, contactsCursor, columnsToMap, mapTo);
       // this.setListAdapter(mAdapter);

        SaveToFile(out, contactsCursor);

        out.flush();
        out.close();
    }

    private void SaveToFile(BufferedWriter outWriter, Cursor contactsCursor) {

        while (contactsCursor.moveToNext()) {

            String contactId = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID));

            displayName = contactsCursor.getString(DISPLAY_NAME);

            int hasPhone = contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            if (hasPhone == 1) {
                // You know have the number so now query it like this
                phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null);

                while (phones.moveToNext()) {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }


                try {
                    outWriter.write(displayName);
                    outWriter.write(phoneNumber);
                    outWriter.write(", ");


                    Handler mHandler = new Handler();

                    updateUI uithread = new updateUI((TextView)findViewById(R.id.ContactNumber),phoneNumber);
                    mHandler.post(uithread);        

                    updateUI uithread2 = new updateUI((TextView)findViewById(R.id.ContactName),displayName);
                    mHandler.post(uithread2);

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

class updateUI implements Runnable {

     TextView t;
    private String number;

    updateUI(TextView t,String number)
    {
        this.t=t;
        this.number=number;
    }
    public void run() {
         this.t.append("\n " +number);

    }
}
