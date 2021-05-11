package hr.sztfr.sztfr_android.ui

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreRepository {

    val TAG = "FIREBASE_REPOSITORY"
    val storageURL = "gs://sztfr-7a759.appspot.com/"
    var firestoreDB = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance()
    var user = FirebaseAuth.getInstance().currentUser


    //get surveys from firebase firestore
    fun getSurveys() : CollectionReference{
        var collectionReference = firestoreDB.collection("surveys")
        return collectionReference
    }

    //get reference for a picture from firebase storage
    fun getImageReference(imageURL: String) : StorageReference{
        var imageReference = storage.getReferenceFromUrl(storageURL + imageURL)
        return imageReference
    }


}