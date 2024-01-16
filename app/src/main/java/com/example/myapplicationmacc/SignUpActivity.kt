package com.example.myapplicationmacc

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class SignUpActivity : AppCompatActivity() {



    //Widgets

    lateinit var password_create: EditText
    lateinit var username_create: EditText
    lateinit var email_create: EditText
    lateinit var createBTN: Button
    lateinit var homeBTN: Button



    //Firebase Auth
    private var firebaseAuth: FirebaseAuth? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null
    private var currentUser: FirebaseUser? = null

    //Firebase Connection
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var collectionReference: CollectionReference = db.collection("Users")

    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        createBTN = findViewById(R.id.button4)
        password_create = findViewById(R.id.password2)
        username_create = findViewById(R.id.username)
        email_create = findViewById(R.id.email)
        homeBTN = findViewById<Button>(R.id.home)
        val i = Intent(this@SignUpActivity, MainActivity :: class.java)
        createBTN.setOnClickListener{
            auth.createUserWithEmailAndPassword(email_create.text.toString(), password_create.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext,
                            "Registration completed!",
                            Toast.LENGTH_SHORT,
                        ).show()

                        startActivity(i)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed: " +task.exception?.message.toString(),
                            Toast.LENGTH_LONG,
                        ).show()
                       // recreate()
                    }
                }
        }

        homeBTN.setOnClickListener{

            startActivity(i)
        }

        //Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
        auth = Firebase.auth

        //check for already signed in user and/or signed out one

        authStateListener = FirebaseAuth.AuthStateListener() {
            @Override
            fun onAuthChanged(firebaseAuth: FirebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser()


                if (currentUser != null) {
                    //user already logged in
                } else {
                    //user signed out
                }

            }
        }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //recreate()
        }
    }



    private fun CreateUserEmailAccount(email: String, pass: String, username: String) {

        if (!TextUtils.isEmpty(email)
            && !TextUtils.isEmpty(pass)
            && !TextUtils.isEmpty(username)
        ) {

            //firebaseAuth?.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(OnCompleteListener<AuthResult>()){

                    }
                }


    override fun onBackPressed() {
        val intent= Intent(this@SignUpActivity,MainActivity:: class.java)
        startActivity(intent)
        super.onBackPressed()
    }


        }





