package com.example.deymos.testapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.deymos.testapp.Database.TestingDatabase;


public class ListAdapter extends CursorAdapter{

    public ListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater mLayout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return mLayout.inflate(R.layout.lists_element, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView testTitle = (TextView) view.findViewById(R.id.test_title);
        TextView testDescription = (TextView) view.findViewById(R.id.test_description);

        String title = cursor.getString(cursor.getColumnIndex(TestingDatabase.TestsTable.TITLE));
        String description = cursor.getString(cursor.getColumnIndex(TestingDatabase.TestsTable.TIME_RESTRICTION));

        if (cursor.moveToFirst()) {
            testTitle.setText(title);
            testDescription.setText(description);
        }
    }
}
