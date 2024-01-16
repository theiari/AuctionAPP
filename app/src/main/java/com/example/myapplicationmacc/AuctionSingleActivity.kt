package com.example.myapplicationmacc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplicationmacc.databinding.ActivityAuctionSingleBinding
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp

class AuctionSingleActivity : AppCompatActivity() {


    lateinit var binding: ActivityAuctionSingleBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auction_single)
        binding = ActivityAuctionSingleBinding.inflate(layoutInflater)
        val title: TextView = findViewById<TextView>(R.id.auction_single_title)
        val description: TextView = findViewById<TextView>(R.id.auction_single_description)
        val currentPrice: TextView = findViewById<TextView>(R.id.auction_single_currentprice)
        val image: ImageView = findViewById(R.id.auction_imageView_single)
        val button: Button = findViewById(R.id.make_offer_button)
        val setPrice: EditText = findViewById(R.id.auction_single_setprice)
        val timestamp = intent.getLongExtra("timestamp", 0L)
        var temp: Auction
        var id : String = ""
        Log.d("AuctionSingle1234", timestamp.toString())

        FirebaseStuff.FirebaseStoreOBJ.getCollectionReference().get()
            .addOnSuccessListener { queryDocumentSnapshots -> // QueryDocumentSnapshot: is an object that represents
                // a single document retrieved from a Firestore query
                // QueryDocumentSnapshot --> Document
                for (journals in queryDocumentSnapshots) {
                    // Convert the document into a custom Object (Auction)
                    temp = journals.toObject(Auction::class.java)
                    if (temp.timeStamp.seconds == timestamp) {

                            id = journals.id
                            Log.d("test", temp.toString())
                            title.text = temp.title
                            description.text = temp.description
                            currentPrice.text = temp.highestBid.toString() + " €"

                            Glide.with(image.context)
                            .load(temp.imageURL)
                            .apply(RequestOptions().centerCrop().fitCenter())
                            .into(image)





                    }

                    button.setOnClickListener(){

                        if (setPrice.text.isBlank() || setPrice.text.isNullOrEmpty()){
                            Toast.makeText(
                                baseContext,
                                "Insert a number!" ,
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                        else if(setPrice.text.toString().toFloat() < temp.highestBid){

                            Toast.makeText(
                                baseContext,
                                "New offer should be higher!!" ,
                                Toast.LENGTH_SHORT,
                            ).show()
                    }
                        else{
                            FirebaseStuff.FirebaseStoreOBJ.getCollectionReference().document(id)
                                .update("highestBid", setPrice.text.toString().toFloat())
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        baseContext,
                                        "Offer registered!" ,
                                        Toast.LENGTH_LONG,
                                    ).show()
                                    val intent= Intent(this@AuctionSingleActivity,MainActivity:: class.java)
                                    startActivity(intent)

                                }
                                .addOnFailureListener{
                                    Toast.makeText(
                                        baseContext,
                                        "Something went wrong..." ,
                                        Toast.LENGTH_LONG,
                                    ).show()
                                }

                        }

                        }

                    }

            }
    }

    override fun onBackPressed() {
        val intent= Intent(this@AuctionSingleActivity,MainActivity:: class.java)
        startActivity(intent)
        super.onBackPressed()
        }



    }
