package android.backup.screens;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CalendarScreen extends Activity {	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        TextView textview = new TextView(this);
        textview.setText("This is the Calendar tab");
        setContentView(textview);        
		calenderDisplay();
	}

	private void calenderDisplay() {
		
		//ReadCalendarContents rcc = new ReadCalendarContents();		
		
	/*	//Cursor calenderCursor = this.managedQuery(ContactsContract.Contacts.CONTENT_URI,
				//null, null, null, null);
		String[] columnsToMap = new String[] {ContactsContract.Contacts.DISPLAY_NAME};		
		int[] mapTo = new int[] {R.id.TextView01};
		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this,R.layout.list,contactsCursor, columnsToMap, mapTo);			
		this.setListAdapter(mAdapter);			
		
		saveCalendar(outStream,contactsCursor);
		
		*/
		
	}
}
	/*
public void saveCalendar(Context context) throws IOException {
		
		String extStorageDirectory;
		extStorageDirectory = Environment.getExternalStorageDirectory().toString();		
		OutputStream outStream = null;
		File file = new File(extStorageDirectory, "calendar.txt");
		outStream = new FileOutputStream(file);	


		ContentResolver contentResolver = context.getContentResolver();

		// Fetch a list of all calendars synced with the device, their display names and whether the
		// user has them selected for display.

		final Cursor cursor = contentResolver.query(Uri.parse("content://calendar/calendars"),
				(new String[] { "_id", "displayName", "selected" }), null, null, null);

		HashSet<String> calendarIds = new HashSet<String>();

		while (cursor.moveToNext()) 
		{

			final String _id = cursor.getString(0);
			final String displayName = cursor.getString(1);
			final Boolean selected = !cursor.getString(2).equals("0");

			System.out.println("Id: " + _id + " Display Name: " + displayName + " Selected: " + selected);
			calendarIds.add(_id);
						
			byte[] buffer1 = _id.getBytes();
			outStream.write(buffer1);
			byte[] buffer2 = displayName.getBytes();				
			outStream.write(buffer2);
		}

		// For each calendar, display all the events from the previous week to the end of next week.		
		for (String id : calendarIds) 
		{
			Uri.Builder builder = Uri.parse("content://calendar/instances/when").buildUpon();
			long now = new Date().getTime();
			ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);
			ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);

			Cursor eventCursor = contentResolver.query(builder.build(),
					new String[] { "title", "begin", "end", "allDay"}, "Calendars._id=" + id,
					null, "startDay ASC, startMinute ASC");			

			while (eventCursor.moveToNext())
			{
				final String title = eventCursor.getString(0);
				final Date begin = new Date(eventCursor.getLong(1));
				final Date end = new Date(eventCursor.getLong(2));
				final Boolean allDay = !eventCursor.getString(3).equals("0");

				System.out.println("Title: " + title + " Begin: " + begin + " End: " + end +
						" All Day: " + allDay);
				outStream.write(id.getBytes());
				outStream.write(title.getBytes());
				outStream.write(begin.getDate()+ begin.getDay()+begin.getYear());
			}
		}		
		
		outStream.flush();
		outStream.close();
	}

}
*/