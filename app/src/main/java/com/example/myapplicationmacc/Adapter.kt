package com.example.myapplicationmacc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.NonDisposableHandle.parent

class Adapter(private val emplist: ArrayList<Auction>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    // This method creates a new ViewHolder object for each item in the RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for each item and return a new ViewHolder object
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)





        return MyViewHolder(itemView)
    }

    // This method returns the total
    // number of items in the data set
    override fun getItemCount(): Int {
        return emplist.size
    }

    // This method binds the data to the ViewHolder object
    // for each item in the RecyclerView
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentEmp = emplist[position]
        val bundle = Bundle()
        holder.title.text = currentEmp.title
        holder.description.text = currentEmp.description
        holder.price.text = currentEmp.highestBid.toString() + " €"


        Glide.with(holder.photo.context)
            .load(currentEmp.imageURL)
            .apply(RequestOptions().centerCrop())
            .into(holder.photo)

        holder.cardView.setOnClickListener(){



            val intent= Intent(holder.cardView.context ,  AuctionSingleActivity():: class.java)
            Log.d("Adapter", currentEmp.timeStamp.seconds.toString())
            bundle.putLong("timestamp", currentEmp.timeStamp.seconds)
            intent.putExtras(bundle)
            holder.cardView.context.startActivity(intent)

        }

    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.auctionTitle)
        val description: TextView = itemView.findViewById(R.id.auctionDescription)
        val price: TextView = itemView.findViewById(R.id.auctionPrice2)
        val photo: ImageView = itemView.findViewById(R.id.auctionPhoto)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }
}
