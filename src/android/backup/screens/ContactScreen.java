package android.backup.screens;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ContactScreen extends ListActivity {
    ListAdapter mAdapter;
    private static final int DISPLAY_NAME = 10;
    private Cursor phones;
    private String phoneNumber;
    private String displayName = "";
    TextView t;
    Bundle bundle;
    static final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    @Override
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {

        bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        list.clear();
        setContentView(R.layout.customrow);

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                list,
                R.layout.contactrow,
                new String[]{"contactName", "contactNumber"},
                new int[]{R.id.text1, R.id.text2}
        );
        displayContacts();
        setListAdapter(adapter);
    }

    public void displayContacts() {

        if ((bundle != null) && (bundle.getBoolean("read") == true)) {

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File file = new File(extStorageDirectory, "contacts.txt");
            FileReader text = null;
            BufferedReader in = null;
            String line;
            try {
                text = new FileReader(file);
                in = new BufferedReader(text);
                while ((line = in.readLine()) != null) {
                    if (line.equals(""))
                        continue;
                    String[] contacts = line.split(",");
                    for (int i = 0; i < contacts.length; i++) {
                        String[] contact = contacts[i].split(" ");
                        if (contact.length != 0) {
                            phoneNumber = contact[contact.length - 1];
                            for (int j = contact.length - 2; j >= 0; j--) {
                                displayName = displayName + " " + contact[j];
                            }
                          pupulateList();
                            phoneNumber=displayName="" ;
                        }

                    }

                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File file = new File(extStorageDirectory, "contacts.txt");
            FileWriter text = null;
            BufferedWriter out = null;
            try {
                text = new FileWriter(file);
                out = new BufferedWriter(text);
                Cursor contactsCursor = this.managedQuery(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                SaveToFile(out, contactsCursor);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void SaveToFile(BufferedWriter outWriter, Cursor contactsCursor) {

        while (contactsCursor.moveToNext()) {
            String contactId = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID));
            displayName = contactsCursor.getString(DISPLAY_NAME);
            int hasPhoneNumber = contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            if (hasPhoneNumber == 1) {
                phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                try {
                    outWriter.write(displayName);
                    outWriter.write(" " + phoneNumber + " ");
                    outWriter.write(", ");
                    pupulateList();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                phones.close();
            }

        }
    }

    private void pupulateList() {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("contactName", displayName);
        temp.put("contactNumber", phoneNumber);
        list.add(temp);

    }
}


