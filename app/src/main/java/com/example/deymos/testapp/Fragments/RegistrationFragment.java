package com.example.deymos.testapp.Fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deymos.testapp.Database.ContentTestingProvider;
import com.example.deymos.testapp.Database.TestingDatabase;
import com.example.deymos.testapp.R;
import com.example.deymos.testapp.TestsListActivity;

public class RegistrationFragment extends Fragment {
    private View fragmentView;
    private Button signUpButton, loginButton;
    private EditText login, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_registration, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUpButton = (Button) fragmentView.findViewById(R.id.sign_up_btn);
        loginButton = (Button) fragmentView.findViewById(R.id.login_btn);
        login = (EditText) fragmentView.findViewById(R.id.login);
        password = (EditText) fragmentView.findViewById(R.id.password);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Sign up");
                    View view = getActivity().getLayoutInflater().inflate(R.layout.sign_up, null);
                    builder.setView(view);
                    final EditText login = (EditText) view.findViewById(R.id.login_dialog);
                    final EditText password = (EditText) view.findViewById(R.id.password_dialog);
                    final EditText confirmPassword = (EditText) view.findViewById(R.id.confirm_password_dialog);
                    builder.setPositiveButton(R.string.sign_up_dialog_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String loginUsername = login.getText().toString();
                            String passwordText = password.getText().toString();
                            String confirmPass = confirmPassword.getText().toString();
                            if (loginUsername.matches("") || passwordText.matches("") || confirmPass.matches("")) {
                                Toast.makeText(getActivity(), R.string.empty_field, Toast.LENGTH_SHORT).show();
                            }
                            if (!passwordText.equals(confirmPass) || passwordText.matches("") || confirmPass.matches("")) {
                                Toast.makeText(getActivity(), R.string.pass_error, Toast.LENGTH_SHORT).show();
                            } else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(TestingDatabase.UsersTable.MAIL, loginUsername);
                                contentValues.put(TestingDatabase.UsersTable.PASSWORD, passwordText);

                                Uri uri = Uri.parse(ContentTestingProvider.CONTENT_URI + TestingDatabase.UsersTable.TABLE_NAME);
                                getActivity().getContentResolver().insert(uri, contentValues);
                                Toast.makeText(getActivity(), R.string.account_created, Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                                Cursor c = getActivity().getContentResolver().query(uri, null, null, null, null);
                                c.moveToFirst();
                                String text = c.getString(c.getColumnIndex(TestingDatabase.UsersTable.PASSWORD));
                                Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    builder.setNegativeButton(R.string.back_dialog_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginUsername = login.getText().toString();
                String passwordText = password.getText().toString();

                if (loginUsername.matches("") || passwordText.matches("")) {
                    Toast.makeText(getActivity(), R.string.empty_field, Toast.LENGTH_SHORT).show();
                } else {
                    Uri usersUri = Uri.parse(ContentTestingProvider.CONTENT_URI + TestingDatabase.UsersTable.TABLE_NAME);
                    String[] projection = {TestingDatabase.UsersTable.MAIL, TestingDatabase.UsersTable.PASSWORD};
                    String selection = TestingDatabase.UsersTable.MAIL + "=? AND " + TestingDatabase.UsersTable.PASSWORD + "=?";
                    String[] selectionAgrs = {loginUsername, passwordText};

                    Cursor c = getActivity().getContentResolver().query(usersUri,
                            projection,
                            selection,
                            selectionAgrs,
                            null);

                    if (c.getCount() == 1) {
                        //Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), TestsListActivity.class);
                        intent.putExtra(TestsListActivity.EXTRA_MESSAGE, loginUsername);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
