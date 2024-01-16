package com.example.myapplicationmacc.ui.addnew

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Half.toFloat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationmacc.Auction
import com.example.myapplicationmacc.FirebaseStuff
import com.example.myapplicationmacc.FirstFragment
import com.example.myapplicationmacc.MainActivity
import com.example.myapplicationmacc.R
import com.example.myapplicationmacc.SignUpActivity
import com.example.myapplicationmacc.databinding.ActivityAddAuctionListBinding
import com.example.myapplicationmacc.databinding.ActivityMainBinding
import com.example.myapplicationmacc.databinding.FragmentNotificationsBinding
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.sql.Timestamp

class AddNewFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    lateinit var binding: ActivityAddAuctionListBinding
    private lateinit var imageURI: Uri
    lateinit var mtakePhoto: ActivityResultLauncher<String>
    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageURI = Uri.parse("")
        binding = ActivityAddAuctionListBinding.inflate(layoutInflater)
        binding.auctionProgressBar.visibility = View.INVISIBLE

        FirebaseStuff.FirebaseStoreOBJ.getCollectionReference()

        FirebaseStuff.AuthOBJ.getAuth()




        binding.auctionCameraButton.setOnClickListener{

           //mtakePhoto.launch("image/*")
            dispatchTakePictureIntent()
        }


        mtakePhoto = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            fun (result: Uri?){
                if (result != null){
                    binding.auctionImageView.setImageURI(result)
                    imageURI = result
                }
            }
        )



    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, 2000)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2000 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val uri = getImageUri(requireContext(), imageBitmap)
            imageURI = uri
            binding.auctionImageView.setImageURI(uri)
        }
    }




        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                // Handle the Intent
                //do stuff here
            }
        }


    fun getImageUri(context: Context, image: Bitmap): Uri {
        val contentResolver = context.contentResolver
        val imageFileName = "auction_image.jpg"
        val storageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "auction_images")

        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val imageFile = File(storageDir, imageFileName)
        try {
            val stream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()

            val uri = Uri.fromFile(imageFile)
            return uri
        } catch (e: IOException) {
            e.printStackTrace()
            return Uri.EMPTY
        }
    }






    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addNewViewModel =
            ViewModelProvider(this)[AddNewViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textNotifications
        addNewViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        //return root

        binding.auctionSaveAuctionButton.setOnClickListener{
            if (binding.auctionTitleEt.text.isNotEmpty() && binding.auctionDescriptionEt.text.isNotEmpty()
                && binding.auctionPrice.text.isNotEmpty()){

                Toast.makeText(
                    activity,
                    "Sending data, please wait",
                    Toast.LENGTH_LONG,
                ).show()

                saveAuction()

                //var binding_activity_main:ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
                //binding_activity_main.navViewMain.selectedItemId = R.id.navigation_dashboard

            }
            else{
                Toast.makeText(
                    activity,
                    "Please check your input again!",
                    Toast.LENGTH_LONG,
                ).show()
            }



        }


    // return inflater.inflate(R.layout.activity_add_auction_list, container, false)
    return  binding.root
    }

    private fun saveAuction() {




        var title: String  = binding.auctionTitleEt.text.toString().trim()
        var description : String = binding.auctionDescriptionEt.text.toString().trim()
        var price : Float = binding.auctionPrice.text.toString().toFloat()

        binding.auctionProgressBar.visibility = View.VISIBLE

        if (title.isNotEmpty() && description.isNotEmpty() && imageURI.toString() != "") {

            Toast.makeText(
                activity,
                "entering firebase stuff",
                Toast.LENGTH_LONG,
            ).show()

            //saving image on firestorage
            val filePath: StorageReference = FirebaseStuff.FirebaseStorageOBJ
                .getReference().child("auction_images")
                .child("my_image_" + com.google.firebase.Timestamp.now().seconds)

            //upload images
            filePath.putFile(imageURI)
                .addOnSuccessListener()
                {
                    filePath.downloadUrl.addOnSuccessListener {
                        var imageURI: String = it.toString()

                        //Creating the object of Auction

                        var auction: Auction = Auction(
                            title,
                            description,
                            price,
                            FirebaseStuff.AuthOBJ.getAuth().currentUser!!.uid.trim(),
                            com.google.firebase.Timestamp.now(),
                            imageURI,
                        )
                        FirebaseStuff.FirebaseStoreOBJ.getCollectionReference().add(auction)
                            .addOnSuccessListener {
                                binding.auctionProgressBar.visibility = View.INVISIBLE
                                //loadFragment()
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                activity?.finish()

                            }


                    }

                }
                .addOnFailureListener {
                    binding.auctionProgressBar.visibility = View.INVISIBLE
                }

        }else{
            binding.auctionProgressBar.visibility = View.INVISIBLE
        }





    }

    private fun loadFragment() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, FirstFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()





    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 1 && resultCode == RESULT_OK){
            if (data !=null){
                imageURI =data.data!! //getting the actual image path
                binding.auctionImageView.setImageURI(imageURI) //showing the image
            }
        }

    }

     */


    override fun onStart() {
        super.onStart()

        FirebaseStuff.FirebaseAuthOBJ.getFireBaseAuth()?.currentUser
    }

    override fun onStop() {
        super.onStop()
        if ( FirebaseStuff.AuthOBJ.getAuth() != null){
            //TODO stuff
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}