package com.example.myapplicationmacc.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationmacc.Auction
import com.example.myapplicationmacc.AuctionRecyclerAdapter
import com.example.myapplicationmacc.FirebaseStuff
import com.example.myapplicationmacc.R
import com.example.myapplicationmacc.databinding.ActivityDashboardBinding
import com.example.myapplicationmacc.databinding.AuctionRowBinding
import com.example.myapplicationmacc.databinding.FragmentDashboardBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference

class DashboardFragment : Fragment() {

    //private var _binding: FragmentDashboardBinding? = null


    lateinit var auctionList: MutableList<Auction>
    lateinit var adapter: AuctionRecyclerAdapter
    var  collectionReference: CollectionReference = FirebaseStuff.FirebaseStoreOBJ.getCollectionReference()
    lateinit var binding: FragmentDashboardBinding

    lateinit var noPost:TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private var binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_dashboard)
        FirebaseStuff.AuthOBJ.getAuth()
        FirebaseStuff.FirebaseStoreOBJ.getFireBaseStoredb()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this.requireContext())
        auctionList = arrayListOf<Auction>()



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        //binding = DataBindingUtil.setContentView(
        //this.requireActivity(), R.layout.auction_row)


        //val dashboardViewModel =
         //   ViewModelProvider(this)[DashboardViewModel::class.java]
        //val root: View = binding.root

       //recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //val linearLayoutFragment : LinearLayout = requireView().findViewById<LinearLayout>(R.id.linearLayoutAuction)
       /* val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

       // val parent:ViewGroup? = view?.parent as? ViewGroup
       // parent?.removeView(view)

        //return inflater.inflate(R.layout.fragment_dashboard, container, false)
        return binding.root
    }





    override fun onDestroyView() {
        super.onDestroyView()
        //binding.root = null
    }

    override fun onStart() {
        super.onStart()

        collectionReference.get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    it.forEach{
                        //convert snapshots to auction object
                        var auction = it.toObject(Auction::class.java)

                        auctionList.add(auction)
                    }

                    //recyclerView
                   adapter = AuctionRecyclerAdapter(
                       this.requireContext(), auctionList
                    )

                    binding.recyclerView.setAdapter(adapter)

                    adapter.notifyDataSetChanged()

                }

            }
            .addOnFailureListener{
                Toast.makeText(
                    this.context,
                    "Something went wrong!",
                    Toast.LENGTH_SHORT
                )
            }
    }


}