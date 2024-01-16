package com.example.myapplicationmacc

class Bid {

    private var bidder: String? =""
    private var bidderID: String? =""
    private var value: Float? = 0f


    constructor(bidder: String?, bidderID: String?, value: Float?) {
        this.bidder = bidder
        this.bidderID = bidderID
        this.value = value
    }
}
