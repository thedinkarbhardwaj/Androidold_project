package com.example.phoneverificationwithfirebase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var storedVerificationId:String = ""
    var otp:String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance();

        var btnphone = findViewById<Button>(R.id.phonenumberbtn)
        btnphone.setOnClickListener{
            phonenumber()
        }

        var editText = findViewById<EditText>(R.id.edittext)
        var verifyotp = findViewById<Button>(R.id.verifyotp)

        verifyotp.setOnClickListener {
            var ed = editText.text.toString()

            val credential = PhoneAuthProvider.getCredential(storedVerificationId, ed)

            // Call the signInWithCredential method to verify the OTP
            signInWithCredential(credential)

//            if (ed == otp){
//                Toast.makeText(this@MainActivity,"jdsk Success",Toast.LENGTH_LONG).show()
//
//            }else{
//                Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
//
//            }
        }
    }

    fun phonenumber(){

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+919876543210") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        auth.setLanguageCode("en")
    }

    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.

             otp = credential.getSmsCode().toString();
            Log.d("TAG", "onVerificationCompleted:$credential")
            //signInWithCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {

            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("TAG", "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("TAG", "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            var resendToken = token

            Toast.makeText(this@MainActivity,"j " + resendToken.toString(),Toast.LENGTH_LONG).show()

        }

    }

    fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        auth.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    Log.d("SUCCESS",task.toString())
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    Toast.makeText(
                        this@MainActivity,
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }


}