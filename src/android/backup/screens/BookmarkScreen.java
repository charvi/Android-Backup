package android.backup.screens;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.widget.SimpleCursorAdapter;

public class BookmarkScreen extends ListActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        String[] projection = new String[] {Browser.BookmarkColumns._ID, 
                                     Browser.BookmarkColumns.TITLE, 
                                     Browser.BookmarkColumns.URL};
        String[] displayFields = new String[] {Browser.BookmarkColumns.TITLE, 
                                     Browser.BookmarkColumns.URL};
        int[] displayViews = new int[] { R.id.bmark_title, 
                                     R.id.bmark_url };

        Cursor cur = managedQuery(android.provider.Browser.BOOKMARKS_URI, 
                       projection, null, null, null);
        setListAdapter(new SimpleCursorAdapter(this, 
                       R.layout.bookmark, cur, 
                       displayFields, displayViews));
   }
}

