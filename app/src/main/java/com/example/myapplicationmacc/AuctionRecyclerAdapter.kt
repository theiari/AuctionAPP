package com.example.myapplicationmacc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationmacc.databinding.AuctionRowBinding
import com.example.myapplicationmacc.databinding.FragmentDashboardBinding

class AuctionRecyclerAdapter(val context: Context, var auctionsList: List<Auction>) :
    RecyclerView.Adapter<AuctionRecyclerAdapter.MyViewHolder>() {

    lateinit var binding: AuctionRowBinding


    inner class MyViewHolder(var binding: AuctionRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(auction: Auction) {
            binding.auction = auction
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = AuctionRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    //SET THE CORRECT DATA AND WIDGETS
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val auction = auctionsList[position]
        holder.bind(auction)
    }


    override fun getItemCount(): Int {
        return auctionsList.size
    }


}