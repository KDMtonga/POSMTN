package com.hobbiton.posmtn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.hobbiton.posmtn.R;
import com.hobbiton.posmtn.adapters.PaymentBottomSheet;

import java.util.concurrent.Delayed;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    CardView paymentInitiationCardView;
    CardView transactionHistoryCardView;
    private Animation animationSlideOutBottom;
    private Animation animationSlideInBottom;
    ImageButton closePRFButton;
    MaterialButton processPRButton;
    ConstraintLayout mainLayout;
    ConstraintLayout paymentRequestForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        animationSlideOutBottom = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.slide_out);
        animationSlideInBottom = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.slide_in);

        closePRFButton = findViewById(R.id.close_button);
        processPRButton = findViewById(R.id.payment_request_button);

        mainLayout = findViewById(R.id.main_layout);
        paymentRequestForm = findViewById(R.id.payment_request_layout);

        paymentInitiationCardView = findViewById(R.id.payment_cardView);
        transactionHistoryCardView = findViewById(R.id.transacations_cardView);

        processPRButton.setOnClickListener(this);
        closePRFButton.setOnClickListener(this);
        transactionHistoryCardView.setOnClickListener(this);
        paymentInitiationCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_cardView:
//                PaymentBottomSheet paymentBottomSheet = new PaymentBottomSheet();
//                paymentBottomSheet.show(getSupportFragmentManager(), "Payment");
                openPaymentRequestForm();
                break;

            case R.id.transacations_cardView:

                break;

            case R.id.close_button:
                closePaymentRequestForm();
                break;

            case R.id.payment_request_button:
                performValidation();
                break;
        }
    }

    private void openPaymentRequestForm() {
        mainLayout.setAnimation(animationSlideInBottom);
        animationSlideInBottom.start();
        mainLayout.setVisibility(View.GONE);

        paymentRequestForm.setAnimation(animationSlideOutBottom);
        animationSlideOutBottom.start();
        paymentRequestForm.setVisibility(View.VISIBLE);
    }

    private void closePaymentRequestForm() {
        paymentRequestForm.setAnimation(animationSlideInBottom);
        animationSlideInBottom.start();
        paymentRequestForm.setVisibility(View.GONE);

        mainLayout.setAnimation(animationSlideOutBottom);
        animationSlideOutBottom.start();
        mainLayout.setVisibility(View.VISIBLE);
    }

    private void performValidation() {
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
                    startActivity(new Intent(HomeActivity.this, ProcessingActivity.class));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }
}
