package com.diy.travelers.PauUMoney;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.diy.travelers.R;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

public class PayUMoneyActivity extends AppCompatActivity {
    // Invoke the following function to open the checkout page.
//PayUmoneyFlowManager.startPayUMoneyFlow(PayUmoneySdkInitializer.PaymentParam paymentParam, Activity context,int style, boolean isOverrideResultScreen);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_umoney);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(100.00)                          // Payment amount
                .setTxnId("sdyreioeiiowiureowfs646")                                             // Transaction ID
                .setPhone("9451764976")                                           // User Phone number
                .setProductName("Lunch")                   // Product Name or description
                .setFirstName("Abhinav")                              // User First name
                .setEmail("abhinavextreme.as@gmail.com")              // User Email ID
                /*.setsUrl(appEnvironment.surl())                    // Success URL (surl)
                .setfUrl(appEnvironment.furl())                     //Failure URL (furl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)*/
                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                .setKey("YYYYYYYY")// Test Merchant key
                .setMerchantId("XXXXXX");             // Merchant ID
    }


}


/*Card Type: Visa

Card Name: Test

Card Number: 4012001037141112

Expiry Date : 05/20

CVV : 123

Card Type: Master

Card Name: Test

Card Number: 5123456789012346

Expiry Date : 05/20

CVV : 123*/
