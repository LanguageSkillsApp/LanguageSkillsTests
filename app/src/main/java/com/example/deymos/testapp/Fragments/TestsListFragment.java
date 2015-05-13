package com.example.deymos.testapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.deymos.testapp.Database.ContentTestingProvider;
import com.example.deymos.testapp.Database.TestingDatabase;
import com.example.deymos.testapp.ListAdapter;
import com.example.deymos.testapp.QuestionActivity;
import com.example.deymos.testapp.R;

public class TestsListFragment extends Fragment {
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater listInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View fragmentView = listInflater.inflate(R.layout.fragment_tests_list, null);
        listView = (ListView) fragmentView.findViewById(R.id.listView);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Uri tableTests = Uri.parse(ContentTestingProvider.CONTENT_URI + TestingDatabase.TestsTable.TABLE_NAME);
        Cursor cursor = getActivity().getContentResolver().query(tableTests, null, null, null, null);
        ListAdapter adapter = new ListAdapter(getActivity(), cursor, 0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                intent.putExtra(QuestionActivity.TEST_NUMBER, position+1);
                startActivity(intent);
            }
        });

    }
}
