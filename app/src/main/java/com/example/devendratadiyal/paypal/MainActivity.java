package com.example.devendratadiyal.paypal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devendratadiyal.paypal.Configure.Config;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
public static final int PAYPAL_REQUEST_CODE = 7171;
    public static String b,c;

    public static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.paypal_cliet_id);

    Button p;
    EditText et;
    public static String amount = "";

    @Override
    protected void onDestroy() {

        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        p = (Button) findViewById(R.id.pay);
        et = (EditText) findViewById(R.id.enter);

        p.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
               processPayment(); //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
            }
        });
    }

    private void processPayment() {

      amount = et.getText().toString();
        System.out.println("DEV TADIYAL "+amount);
        PayPalPayment paypalpayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD","devenderatadiyal@gmail.com",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,paypalpayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PAYPAL_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetails.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amount)
                        );
                       // System.out.println("DEVTADIYAL JSON VALUE "+paymentDetails);
                        JSONObject j = new JSONObject(paymentDetails);
                        String a = j.getString("response");
                      //  System.out.println("AAAAAAAAAAAa "+a);
                        JSONObject k = new JSONObject(a);
                         b = k.getString("id");
                         c = k.getString("state");
                        System.out.println(b+"    "+c);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
                else if(resultCode== Activity.RESULT_CANCELED)

                    Toast.makeText(this,"CANCEL",Toast.LENGTH_LONG).show();
                }
                else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID)

                Toast.makeText(this,"INVALID",Toast.LENGTH_LONG).show();
            }

            }



