package com.example.stripefinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class MainActivity : AppCompatActivity() {

    lateinit var paymentSheet: PaymentSheet
    var client_secret = "pi_3O52DBSEujDHRSUh02zPREVq_secret_wKifqrkAh27RVsccvLlRSlQxH"
    var customeId = "cus_Osno9nX7WDwTGO"
    var ephemeralkey = "ek_test_YWNjdF8xTzRMQUpTRXVqREhSU1VoLG1EcjA5NWFvWWJpUTBMUnowY2dDQzFra01qVjJ0WVo_003NEq91R5"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PaymentConfiguration.init(applicationContext, "pk_test_51O4LAJSEujDHRSUhpojZPaKm0uydtDux3OJjouEBQo3TK2xu9uwRJhFAxEJL3AouPup88BdPneiw1d1x5Fmi74yA00IoGHopkt")
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {

        when (paymentSheetResult) {
            is PaymentSheetResult.Completed -> {
                Toast.makeText(this,"Payment complete", Toast.LENGTH_LONG).show()
            }
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(this,"Payment cancel", Toast.LENGTH_LONG).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(this,"Payment failed", Toast.LENGTH_LONG).show()

            }
        }

    }

    fun paybtn(view: View) {

        paymentSheet.presentWithPaymentIntent(
            client_secret,
            PaymentSheet.Configuration(
                "Dinkar",

                PaymentSheet.CustomerConfiguration(
                    customeId,ephemeralkey
                )
            ))
    }
}