package com.example.phoneverificationwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInWithFirebase : AppCompatActivity() {

    lateinit var mgooglesigninclient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in_with_firebase)

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


    private fun firebaseauth(idToken: String?) {

        var crediental = GoogleAuthProvider.getCredential(idToken,null)

        var auth = FirebaseAuth.getInstance()
        auth.signInWithCredential(crediental)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful){
                        var user = auth.currentUser
                        Toast.makeText(this@GoogleSignInWithFirebase,user?.displayName?.toString(),Toast.LENGTH_LONG).show()
                    }
                }

            })
    }

    fun gooleSignIn(view: View) {
        signin()
    }
}