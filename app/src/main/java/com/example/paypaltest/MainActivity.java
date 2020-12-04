package com.example.paypaltest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private Button paymentBtn;

    private int PayPal_REQ_CODE = 12;

    private  static PayPalConfiguration paypalConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(paypalClientID.paypal_Client_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paymentBtn = findViewById(R.id.paymentBtn);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaypalPayment();
            }
        });
    }


    private void PaypalPayment(){

        PayPalPayment payment = new PayPalPayment(new BigDecimal(100), "USD",
                "Test payment", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PayPal_REQ_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PayPal_REQ_CODE){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "payment made successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Payment Unsuccessful", Toast.LENGTH_LONG ).show();
            }
        }
    }

    @Override
    protected void onDestroy() {

        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}