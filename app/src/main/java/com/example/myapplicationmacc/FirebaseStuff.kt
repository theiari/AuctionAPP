package com.example.myapplicationmacc

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseStuff {

    //Firebase Auth

    private var authStateListener: FirebaseAuth.AuthStateListener? = null
    private var currentUser: FirebaseUser? = null

    //Firebase Connection


    object FirebaseStorageOBJ{
        private var firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()

        fun getInstance(): FirebaseStorage{
            return this.firebaseStorage
        }

        fun getReference() : StorageReference{
            return this.firebaseStorage.getReference()
        }
    }


    object AuthOBJ{
        private var auth:FirebaseAuth = Firebase.auth

        fun getAuth(): FirebaseAuth {
            return this.auth
        }
    }

    object FirebaseAuthOBJ {
        private var firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()

        fun getFireBaseAuth(): FirebaseAuth? {
            return this.firebaseAuth
        }
    }


    object  FirebaseStoreOBJ{

        var db: FirebaseFirestore = Firebase.firestore
        private var collectionReference: CollectionReference = db.collection("Auctions")

        fun getFireBaseStoredb() : FirebaseFirestore? {
            return db
        }

        fun getCollectionReference() : CollectionReference{
            return collectionReference
        }
    }





}