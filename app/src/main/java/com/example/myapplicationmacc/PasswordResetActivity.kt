package com.example.myapplicationmacc

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class PasswordResetActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

         var resetButton : Button = findViewById(R.id.resetPasswordButton)
         var openGmailButton : Button = findViewById(R.id.openInboxButton)
         var emailView : AutoCompleteTextView = findViewById(R.id.emailResetCompleteView)
         var backButton : ImageButton = findViewById(R.id.backToHomeButton)


        //ON CLICK THE BUTTON WILL OPEN GMAIL INBOX
        openGmailButton.setOnClickListener {
           //PackageManager pm = context.getPackageManager()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        //ON CLICK RETURN TO HOME
        backButton.setOnClickListener {
            val intent = Intent(this@PasswordResetActivity, MainActivity :: class.java)
            startActivity(intent)
        }

        resetButton.setOnClickListener{


            FirebaseStuff.AuthOBJ.getAuth().sendPasswordResetEmail(emailView.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "Reset mail sent!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }

                    else{
                        Toast.makeText(
                            baseContext,
                            "There was an error...",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

        }



    }

    override fun onBackPressed() {
        val intent= Intent(this@PasswordResetActivity,MainActivity:: class.java)
        startActivity(intent)
        super.onBackPressed()
    }

}