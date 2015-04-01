package com.example.deymos.testapp.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deymos.testapp.R;

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
                //TODO sign up activity
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
                                Toast.makeText(getActivity(), "Empty Fields" ,Toast.LENGTH_SHORT).show();
                            }
                            if (!passwordText.equals(confirmPass) || passwordText.matches("") || confirmPass.matches("")) {
                                Toast.makeText(getActivity(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                            } else {
                                //TODO save loginUsername and password to db
                                Toast.makeText(getActivity(), "Account created you can login now", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
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
                    Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO get password from db using loginUsername
                    String passwordFromDb = "password from db";
                    if (passwordText.equals(passwordFromDb)) {
                        Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_SHORT).show();
                        //TODO go to new fragment or activity
                    } else {
                        Toast.makeText(getActivity(), "Login or Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
