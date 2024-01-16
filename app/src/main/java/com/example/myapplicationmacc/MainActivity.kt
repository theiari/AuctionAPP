package com.example.myapplicationmacc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplicationmacc.databinding.ActivityMainBinding
import com.example.myapplicationmacc.ui.dashboard.DashboardFragment
import com.example.myapplicationmacc.ui.home.HomeFragment
import com.example.myapplicationmacc.ui.addnew.AddNewFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.appcheck.internal.util.Logger.TAG
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bottomNav : BottomNavigationView

    override fun onStart() {
        super.onStart()
        //bottomNav.selectedItemId = R.id.navigation_dashboard

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        //bottomNav.selectedItemId = R.id.navigation_dashboard
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Constants.getAuctionData()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val signUPButton: Button = findViewById<Button>(R.id.createAccountButton)
        val signInButton: Button = findViewById<Button>(R.id.loginButton)
        val email: AutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.emailLogin)
        val password: TextView = findViewById<TextView>(R.id.passwordLogin)
        val resetButton: Button = findViewById(R.id.resetPassword)

        val intent= Intent(this@MainActivity,MainActivity:: class.java)

        bottomNav= findViewById(R.id.nav_view_main)
        bottomNav.visibility = View.INVISIBLE
        bottomNav.selectedItemId = R.id.navigation_dashboard //setup default selected item to be the dashboard one
        bottomNav.setOnItemSelectedListener {

            when(it.itemId){

                R.id.navigation_home -> {
                    //replaceFragment(HomeFragment())
                    FirebaseStuff.AuthOBJ.getAuth().signOut()
                    startActivity(intent)
                    true}
                R.id.navigation_dashboard -> {
                    //so the same code used in login button listener I guess

                    if (FirebaseStuff.AuthOBJ.getAuth().currentUser != null){
                        replaceFragment(FirstFragment())
                        Constants.getAuctionData()
                    }
                    else {
                        startActivity(intent)
                    }
                        true
                }
                R.id.navigation_add_item ->{
                    if (FirebaseStuff.AuthOBJ.getAuth().currentUser != null)
                    replaceFragment(AddNewFragment())
                    true
                }

                else ->{

                    //something else

                }

            }

            true



        }




        val dashboard = Intent(this@MainActivity, Dashboard :: class.java)
        signUPButton.setOnClickListener {
            //signUpTapped()
            val intent = Intent(this@MainActivity, SignUpActivity :: class.java)
            startActivity(intent)
        }

        resetButton.setOnClickListener {
            val reset = Intent(this@MainActivity, PasswordResetActivity :: class.java)
            startActivity(reset)
        }

        if (FirebaseStuff.AuthOBJ.getAuth().currentUser!= null){
            replaceFragment(FirstFragment())

            bottomNav.visibility = View.VISIBLE
            binding.mainPageLayout.visibility = View.INVISIBLE
        }

        signInButton.setOnClickListener {
            if (email.text.toString().isNotBlank() || password.text.toString().isNotBlank()) {
                FirebaseStuff.AuthOBJ.getAuth()
                    .signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            //FirstFragment.newInstance("helo","halo")
                            replaceFragment(FirstFragment())

                            bottomNav.visibility = View.VISIBLE
                            binding.mainPageLayout.visibility = View.INVISIBLE
                            /*
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = FirebaseStuff.AuthOBJ.getAuth().currentUser //maybe useless
                        startActivity(dashboard)
                        */
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed: " + task.exception?.message.toString(),
                                Toast.LENGTH_LONG,
                            ).show()

                        }
                    }
            }



        }

    }


    // This method replaces the current fragment
    // with a new fragment
    private fun replaceFragment(fragment: Fragment) {
        // Get a reference to the FragmentManager
        val fragmentManager = supportFragmentManager

        // Start a new FragmentTransaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the current fragment with the new fragment

        fragmentTransaction.replace(R.id.frame_layout, fragment)
        setContentView(binding.root)


        // Commit the FragmentTransaction
        fragmentTransaction.commit()


    }



    private fun signUpTapped(){
        //load the new activity
            val intent = Intent(this@MainActivity, SignUpActivity :: class.java)
            startActivity(intent)



    }
}