package com.example.myapplicationmacc

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.firestore.toObject

object Constants {
    // Arraylist and return the Arraylist
    fun getAuctionData():ArrayList<Auction>{


        // create an arraylist of type Auction class
        val auctionList=ArrayList<Auction>()
        //val emp1=Auction("Dario Mohapatra","chinmaya@gmail.com", 12.0f,"abc", Timestamp.now(),
           // "string.com")
        //auctionList.add(emp1)

        val db = Firebase.firestore
        db.collection("Auction").get().
            addOnSuccessListener { result ->
                for (auction in result){
                //auctionList.add(auction.toObject(Auction :: class.java))
                    //(auction.toObject(Auction :: class.java))
                    //Log.d("TAG1234", "${auction.id} => ${auction.data}")
                    //println("${auction.id} => ${auction.data}")
                    //Log.d("TAGBOOLEAN", auctionList.add(auction.toObject()).toString() )

            }


            }
        /*
        auctionList.add(emp2)
        val emp3=Auction("OMM Meheta","mehetaom@gmail.com", 12.0f,123, Timestamp.now(),
            "string.com")
        auctionList.add(emp3)
        val emp4=Auction("Hari Mohapatra","harim@gmail.com", 12.0f,123, Timestamp.now(),
            "string.com")
        auctionList.add(emp4)
        val emp5=Auction("Abhisek Mishra","mishraabhi@gmail.com", 12.0f,123, Timestamp.now(),
            "string.com")
        auctionList.add(emp5)
        val emp6=Auction("Sindhu Malhotra","sindhu@gmail.com", 12.0f,123, Timestamp.now(),
            "string.com")
        auctionList.add(emp6)
        val emp7=Auction("Anil sidhu","sidhuanil@gmail.com", 12.0f,123, Timestamp.now(),
            "string.com")
        auctionList.add(emp7)
        val emp8=Auction("Sachin sinha","sinhas@gmail.com", 12.0f,123, Timestamp.now(),
            "string.com")
        auctionList.add(emp8)
        val emp9=Auction("Amit sahoo","sahooamit@gmail.com", 12.0f,123, Timestamp.now(),
            "string.com")
        auctionList.add(emp9)
        val emp10=Auction("Raj kumar","kumarraj@gmail.com", 12.0f,123, Timestamp.now(),
            "string.com")
        auctionList.add(emp10)*/

        return auctionList
    }
}
