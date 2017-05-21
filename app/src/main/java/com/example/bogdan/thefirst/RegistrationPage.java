package com.example.bogdan.thefirst;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;

import static com.example.bogdan.thefirst.R.*;

public class RegistrationPage extends AppCompatActivity {

    private AccountManager accountManager = AccountManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        initComponents();
    }

    private void initComponents() {
        final EditText emailTextField = (EditText) findViewById(id.registrPageEmailTxtField);
        final EditText passField = (EditText) findViewById(id.registrPagePassField);
        final EditText confirmPassField = (EditText) findViewById(id.registrPageConfirmPassField);
        Button regBtn = (Button) findViewById(id.registrPageRegBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = passField.getText().toString();
                String confirmationPass = confirmPassField.getText().toString();
                String email = emailTextField.getText().toString();

                switch (accountManager.addAccount(email, pass, confirmationPass)) {
                    case SUCCESS:
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case INVALID_EMAIL_OR_PASSWORD:
                        showMessage("Некорректный логин и/или пароль");
                        break;
                    case PASSWORDS_DO_NOT_MATCH:
                        showMessage("Пароли не совпадают");
                        break;
                    case LOGIN_IS_EXIST:
                        showMessage("Аккаунт с таким адресом уже существует");
                        break;
                    default:
                        showMessage("Ошибка регистрации");
                }
            }
        });
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
