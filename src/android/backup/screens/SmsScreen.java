package android.backup.screens;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class SmsScreen extends Activity {

	String extStorageDirectory ;

	public void onCreate(Bundle savedInstanceState) {    
		super.onCreate(savedInstanceState);
		try {
			smsDisplay();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
	}

	private void smsDisplay() throws IOException {		
		String[] data=new String[100];
		String[] sender=new String[100];

		for(int i=0;i<100;i++)
		{
			data[i]="null";
		}

		Uri mSmsQueryUri = Uri.parse("content://sms/inbox");

		Cursor SMScursor=this.managedQuery(mSmsQueryUri,new String[] { "_id", "thread_id", "address", "person", "date", "body" },null,null,null);
		startManagingCursor(SMScursor);
				
		extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		Toast.makeText(this, extStorageDirectory, Toast.LENGTH_LONG);
		OutputStream outStream = null;
		File file = new File(extStorageDirectory, "sms.txt");

		outStream = new FileOutputStream(file);			


		if(SMScursor!=null)
		{
			int count= SMScursor.getCount();          
			if(count>0)
			{
				for(int i=0;i<count;i++)
				{
					SMScursor.moveToPosition(i);        	  
					String address=SMScursor.getString(2);
					String body = SMScursor.getString(5);

					data[i]=body;
					sender[i]=address;             
					//osw.write(data[i]);
					byte []buffer = data[i].getBytes();
					outStream.write(buffer);


					Toast.makeText(this,address, Toast.LENGTH_LONG).show();
					Toast.makeText(this,body, Toast.LENGTH_LONG).show();

				}       

			}
			else
			{
				Toast.makeText(this,"NO MESSAGES IN INBOX",Toast.LENGTH_LONG).show();
			}

//			osw.flush();
	//		osw.close();
			outStream.flush();
			outStream.close();
			SMScursor.close();


		}

	}   
}