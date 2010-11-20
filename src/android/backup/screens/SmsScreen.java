package android.backup.screens;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SmsScreen extends ListActivity {

    String extStorageDirectory;
    ArrayList<String> smsTextList = new ArrayList<String>();
    ArrayList<String> senderList = new ArrayList<String>();
    private List<HashMap<String,String>> list= new ArrayList<HashMap<String,String>>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.clear();
        setContentView(R.layout.customrow);

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                list,
                R.layout.contactrow,
                new String[]{"smsNumber", "smsText"},
                new int[]{R.id.text1, R.id.text2}
        );
        try {
            smsDisplay();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         setListAdapter(adapter);
    }

    private void smsDisplay() throws IOException {

        Uri mSmsQueryUri = Uri.parse("content://sms/inbox");
        Cursor SMScursor = this.managedQuery(mSmsQueryUri, new String[]{"_id", "thread_id", "address", "person", "date", "body"}, null, null, null);
        startManagingCursor(SMScursor);
        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        Toast.makeText(this, extStorageDirectory, Toast.LENGTH_LONG);
        File file = new File(extStorageDirectory, "sms.txt");
        FileWriter text =  new FileWriter(file);
        BufferedWriter out = new BufferedWriter(text);

        if (SMScursor != null) {
            int count = SMScursor.getCount();
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    SMScursor.moveToPosition(i);
                    String address = SMScursor.getString(2);
                    String body = SMScursor.getString(5);

                    smsTextList.add(i, body);
                    senderList.add(i, address);

                    out.write(senderList.get(i)+" "+smsTextList.get(i)+ ", ");
                   populateList(senderList.get(i),smsTextList.get(i));

                }
            } else {
                Toast.makeText(this, "NO MESSAGES IN INBOX", Toast.LENGTH_LONG).show();
            }
            out.flush();
            out.close();
            SMScursor.close();
        }


    }
    private void populateList(String smsNumber,String smsText)
    {
          HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("smsNumber", smsNumber);
        temp.put("smsText", smsText);
        list.add(temp);
    }
}
