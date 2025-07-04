package com.example.phoneverificationwithfirebase

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.internal.Utility
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays


class FacebookLogin : AppCompatActivity() {

    lateinit var loginButton:Button
     var loginManager: LoginManager? = null
     var callbackManager: CallbackManager? = null

    private lateinit var auth: FirebaseAuth

    private val EMAIL = "email"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_login)

        auth = Firebase.auth

        printHashKey()

//        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(this)

        loginButton = findViewById<Button>(R.id.button_facebook)

        loginButton.setOnClickListener {
            loginManager?.logInWithReadPermissions(
                this@FacebookLogin,
                Arrays.asList(
                    "email",
                    //"user_phone_numbers",
                    "public_profile"
                )
            );

            facebookLogin()

        }
    }


    fun facebookLogin() {
        loginManager = LoginManager.getInstance()
        callbackManager = CallbackManager.Factory.create()
        loginManager?.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // Callback when the Facebook login is successful
                    val request = GraphRequest.newMeRequest(
                        loginResult.accessToken
                    ) { `object`, response ->
                        if (`object` != null) {
                            try {

                                Log.d("DinkarLog",response.toString())
                                val name = `object`.getString("name")
                                val email = `object`.getString("email")
                                val fbUserID = `object`.getString("id")
                                val phoneno = `object`.getString("phonenumber")

                                // Here, you have obtained user information such as name, email, and ID.
                                // You can now handle this data, for example, by passing it to Firebase Authentication.
                                // Make sure to validate and handle the data securely.

                                // You can also call handleFacebookAccessToken(loginResult.accessToken) here
                                // if you want to integrate this with Firebase Authentication.


                                Log.d("DataDinkar", "$name $email $fbUserID")

                                // After obtaining user data, you can perform further actions or API calls.

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            } catch (e: NullPointerException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "id, name, email, gender, birthday"
                    )
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    Log.v("LoginScreen", "---onCancel")
                    // Handle the cancellation of the Facebook login process here.
                }

                override fun onError(error: FacebookException) {
                    // Handle errors that may occur during the Facebook login process.
                    Log.v(
                        "LoginScreen", "----onError: "
                                + error.message
                    )
                }
            })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       // const val FB_REQUEST_CODE = 64206  // This is a common request code for Facebook login
            callbackManager?.onActivityResult(requestCode, resultCode, data)


    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("TAG", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser

                    Toast.makeText(
                        baseContext,
                        "Authentication succcess " + user?.email.toString(),
                        Toast.LENGTH_SHORT,
                    ).show()
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }



    fun printHashKey() {

        // Add code to print out the key hash
        try {
            val info = packageManager.getPackageInfo(
                "com.example.phoneverificationwithfirebase",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d(
                    "KeyHash:",
                    Base64.encodeToString(
                        md.digest(),
                        Base64.DEFAULT
                    )
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }
}