package com.example.devendratadiyal.paypal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {


    TextView t1,t2,t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_payment_details);

        t1 = (TextView) findViewById(R.id.textid);
                t2 = (TextView) findViewById(R.id.textamount);
                t3 = (TextView) findViewById(R.id.textstatus);

        try {
            t1.setText("Payment ID  "+MainActivity.b);
            t3.setText("Payment Status  "+MainActivity.c);
            t2.setText("Payment Amount $ "+MainActivity.amount);
        } catch (Exception e) {
            System.out.println("HI DEV SINGH TADIYAL");
            e.printStackTrace();
        }

    }

    private void showDetails(JSONObject response, String paymentAmount) {



    }

}
