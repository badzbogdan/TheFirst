package com.example.bogdan.thefirst;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.bogdan.thefirst.R.*;

public class MainPage extends AppCompatActivity {

    private AccountManager accountManager = AccountManager.getInstance();

    private EditText emailTextField;
    private EditText passField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.main_page);
        initComponents();

        if (accountManager.sessionIsActive()) {
            logIn();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        clearTextFields();
    }

    private void clearTextFields() {
        emailTextField.setText("");
        passField.setText("");
    }

    private void initComponents() {
        emailTextField = (EditText) findViewById(id.startPageEmailTxtField);
        passField = (EditText) findViewById(id.startPagePasswdTxtField);
        Button loginBtn = (Button) findViewById(id.startPageLoginBtn);
        Button regBtn = (Button) findViewById(id.startPageRegistrBtn);

        accountManager.openDB(new DBHelper(this));
        accountManager.setPreferences(getPreferences(MODE_PRIVATE));

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextField.getText().toString();
                String pass = passField.getText().toString();
                if (accountManager.logIn(email, pass)) {
                    logIn();
                } else {
                    showMessage("Неверный логин и/или пароль");
                }
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegistrationPage.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void logIn() {
        Intent intent = new Intent(this, LoginPage.class);
        startActivityForResult(intent, 0);
    }

    private void showMessage(String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle("Error")
            .setMessage(message)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

}
