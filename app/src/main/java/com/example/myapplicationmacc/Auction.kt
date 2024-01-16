package com.example.myapplicationmacc

import com.google.firebase.Timestamp

data class Auction (

     val title: String,
     val description: String,
     val highestBid: Float,
     val ownerID: String,
     val timeStamp:  Timestamp,
     val imageURL: String,
    // val bids: MutableList<Bid>
){
     // Constructor without parameters
     constructor() : this("", "", 0f, "", Timestamp.now(), "")
}


      // Default no-arg constructor

    /*constructor(
        title: String,
        description: String,
        highestBid: Float,
        ownerID: Int,
        timeStamp: Timestamp,
        imageURL: String
    ) {
        this.title = title
        this.description = description
        this.highestBid = highestBid
        this.ownerID = ownerID
        this.timeStamp = timeStamp
        this.imageURL = imageURL
    }
*/


