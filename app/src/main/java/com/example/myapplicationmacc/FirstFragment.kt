package com.example.myapplicationmacc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val auctionlist=Constants.getAuctionData()
    lateinit var myAdapter: Adapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        //myAdapter = Adapter(
         //   auctionlist
        //)







    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment




        return inflater.inflate(R.layout.fragment_first_fragment, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // getting the employeelist
        //val auctionlist=Constants.getAuctionData()
        recyclerView = requireView().findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.activity);
        // Assign employeelist to ItemAdapter
        val itemAdapter= Adapter(auctionlist)

        // Set the LayoutManager that
        // this RecyclerView will use.

        recyclerView.layoutManager = LinearLayoutManager(context)

        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.adapter = itemAdapter
    }

    override fun onStart() {
        super.onStart()
        FirebaseStuff.FirebaseStoreOBJ.getCollectionReference().get()
            .addOnSuccessListener { queryDocumentSnapshots -> // QueryDocumentSnapshot: is an object that represents
                // a single document retrieved from a Firestore query
                // QueryDocumentSnapshot --> Document
                for (journals in queryDocumentSnapshots) {
                    // Convert the document into a custom Object (Auction)
                    val auction: Auction = journals.toObject(Auction::class.java)
                    auctionlist.add(auction)
                }

                // RecyclerView
                myAdapter = Adapter(
                    auctionlist
                )
                recyclerView.adapter = myAdapter
                myAdapter.notifyDataSetChanged()
            }.addOnFailureListener(OnFailureListener {
                Toast.makeText(
                    this.requireContext(),
                    "Opps! Something went wrong!",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }

}
