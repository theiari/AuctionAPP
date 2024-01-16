package com.example.myapplicationmacc

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplicationmacc.databinding.ActivityDashboardBinding
import com.example.myapplicationmacc.ui.dashboard.DashboardFragment
import com.example.myapplicationmacc.ui.home.HomeFragment
import com.example.myapplicationmacc.ui.addnew.AddNewFragment

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    lateinit var auctionList: MutableList<Auction>
    //private lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        

        binding = ActivityDashboardBinding.inflate(layoutInflater)




        setContentView(binding.root)
        replaceFragment(DashboardFragment())


        val bottomNav: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNav.selectedItemId = R.id.navigation_dashboard //setup default selected item to be the dashboard one
        bottomNav.setOnItemSelectedListener {

            when(it.itemId){

                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    true}
                R.id.navigation_dashboard -> {
                    renderDashboard()
                    true
                }
                R.id.navigation_add_new ->{
                    replaceFragment(AddNewFragment())
                    true
                }

                else ->{

                //something else

                }

            }

            true



        }



    }
    //FUNCTION THAT CREATES ALL THE ELEMENTS IN THE DASHBOARD FRAGMENT
    private fun renderDashboard() {
        replaceFragment(DashboardFragment())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)


        //mBottomNavigationView.menu.findItem(R.id.navigation_dashboard).isChecked = true



        return super.onCreateOptionsMenu(menu)
    }

    private  fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_dashboard, fragment)
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId){
            R.id.action_add -> if (FirebaseStuff.AuthOBJ.getAuth() != null){
                val intent = Intent(this, AddAuctionActivity:: class.java)
                startActivity(intent)
            }

            R.id.action_sign_out -> if (FirebaseStuff.AuthOBJ.getAuth().currentUser !=null){
                FirebaseStuff.AuthOBJ.getAuth().signOut()
                val intent = Intent(this, MainActivity :: class.java)
                startActivity(intent)
            }


        }

        return super.onOptionsItemSelected(item)
    }

    
}