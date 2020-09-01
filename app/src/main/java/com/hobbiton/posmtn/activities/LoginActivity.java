package com.hobbiton.posmtn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.hobbiton.posmtn.R;
import com.nbbse.mobiprint3.Printer;

import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {

    MaterialButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                if (printer.getPrinterStatus() == 1){
//                    printer.printText("    e-LEVY MARKETS RECEIPT", 1);
//                    printer.printText("-------------------------------");
//                    printer.printText("Date placed here", 1);
//                    printer.printText("MARKETEER: ");
//                    printer.printText("COUNCIL: ");
//                    printer.printText("MARKET: ");
//                    //printer.printText(" ");
//                    printer.printText("CATEGORY: ");
//                    printer.printText("STAND NUMBER: ");
//                    printer.printEndLine();
//                }else {
//                    Toast.makeText(LoginActivity.this, "Printer unavailable", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
