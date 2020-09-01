package com.hobbiton.posmtn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.hobbiton.posmtn.R;
import com.hobbiton.posmtn.services.IncomingSms;
import com.nbbse.mobiprint3.Printer;

import java.io.InputStream;

public class ProcessingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    View currentView;
    String receivedPayment;
    Printer printer;
    InputStream receipt_logo;
    IncomingSms receivedSMSReceiver;
    Bitmap bitmap;

    private Animation animationSlideOutBottom;
    private Animation animationSlideInBottom;
    MaterialButton printButton;
    ConstraintLayout mainLayout;
    ConstraintLayout printReceiptLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);
        receipt_logo = getResources().openRawResource(R.raw.mtn);
        printer = Printer.getInstance();
        currentView = findViewById(android.R.id.content);
        bitmap = BitmapFactory.decodeResource(getResources(), R.raw.mtn);
        initReceiver();

        animationSlideOutBottom = AnimationUtils.loadAnimation(ProcessingActivity.this, R.anim.slide_out);
        animationSlideInBottom = AnimationUtils.loadAnimation(ProcessingActivity.this, R.anim.slide_in);

        printButton = findViewById(R.id.print_receipt_button);

        mainLayout = findViewById(R.id.processing_main_layout);
        printReceiptLayout = findViewById(R.id.payment_details_layout);

        printButton.setOnClickListener(this);


//        printer.printText();
//        Snackbar.make(currentView,receivedPayment,Snackbar.LENGTH_LONG).show();
    }

    private void initReceiver() {
        receivedSMSReceiver = new IncomingSms() {
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                receivedPayment = receivedMessage;
                openPaymentRequestForm();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receivedSMSReceiver, new IntentFilter(SMS_RECEIVED));
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterReceiver(receivedSMSReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.print_receipt_button:
                printer.printBitmap(receipt_logo);
                printer.printText(receivedPayment);
                printer.printEndLine();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    closePaymentRequestForm();
                                }
                            });
                            Thread.sleep(750);
                            startActivity(new Intent(ProcessingActivity.this, HomeActivity.class));
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
    }

    private void openPaymentRequestForm() {
        mainLayout.setAnimation(animationSlideInBottom);
        animationSlideInBottom.start();
        mainLayout.setVisibility(View.GONE);

        printReceiptLayout.setAnimation(animationSlideOutBottom);
        animationSlideOutBottom.start();
        printReceiptLayout.setVisibility(View.VISIBLE);
    }

    private void closePaymentRequestForm() {
        printReceiptLayout.setAnimation(animationSlideInBottom);
        animationSlideInBottom.start();
        printReceiptLayout.setVisibility(View.GONE);

        mainLayout.setAnimation(animationSlideOutBottom);
        animationSlideOutBottom.start();
        mainLayout.setVisibility(View.VISIBLE);
    }
}
