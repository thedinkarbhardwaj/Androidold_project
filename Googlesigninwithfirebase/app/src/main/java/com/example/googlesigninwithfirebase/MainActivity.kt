package com.example.googlesigninwithfirebase

import android.content.Intent
import android.media.MediaPlayer.OnCompletionListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    lateinit var mgooglesigninclient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var googlesigninoption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mgooglesigninclient = GoogleSignIn.getClient(this,googlesigninoption)
    }

    fun signin(){
        var intent = mgooglesigninclient.signInIntent
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100){
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                var account = task.result

                Toast.makeText(this@MainActivity,account.email.toString(),Toast.LENGTH_LONG).show()
                // we donot need to firebaseauth function to get user data may be it is used to save cred in firebase
                firebaseauth(account.idToken)
            }catch (e:Exception){

            }

        }
    }

    private fun firebaseauth(idToken: String?) {

        var crediental = GoogleAuthProvider.getCredential(idToken,null)

        var auth = FirebaseAuth.getInstance()
        auth.signInWithCredential(crediental)
            .addOnCompleteListener(object :OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful){
                        var user = auth.currentUser
                        Toast.makeText(this@MainActivity,user?.displayName?.toString(),Toast.LENGTH_LONG).show()
                    }
                }

            })
    }

    fun googlesigninbtn(view: View) {
        signin()
    }
}