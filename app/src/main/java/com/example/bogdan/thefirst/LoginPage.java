package com.example.bogdan.thefirst;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.bogdan.thefirst.R.*;

public class LoginPage extends AppCompatActivity {

    private AccountManager accountManager = AccountManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        initComponents();
    }

    private void initComponents() {
        TextView logInEmailText = (TextView) findViewById(id.logInEmailText);
        logInEmailText.setText(accountManager.getCurrentEmail());

        Button logInBtn = (Button) findViewById(id.logInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountManager.clearCurrentEmail();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
