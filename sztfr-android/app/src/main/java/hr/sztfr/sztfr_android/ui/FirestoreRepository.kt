package hr.sztfr.sztfr_android.ui

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreRepository {

    val TAG = "FIREBASE_REPOSITORY"
    val storageURL = "gs://sztfr-7a759.appspot.com/"
    var firestoreDB = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance()
    var user = FirebaseAuth.getInstance().currentUser


    //get published surveys from firebase firestore
    fun getPublishedSurveys() : Query {
        var query= firestoreDB.collection("surveys").whereEqualTo(
                "published",
                true
        )
        return query
    }

    //get unpublished surveys from firebase firestore
    fun getUnpublishedSurveys() : Query {
        var query= firestoreDB.collection("surveys").whereEqualTo(
                "published",
                false
        )
        return query
    }

    //get reference for a picture from firebase storage
    fun getImageReference(imageURL: String) : StorageReference{
        var imageReference = storage.getReferenceFromUrl(storageURL + imageURL)
        return imageReference
    }

    //get reference array for result images
    fun getResultImageReferences(imageURLs: List<String>) : List<StorageReference>{
        var imageReferences : MutableList<StorageReference> = mutableListOf()
        for (img in imageURLs){
            var ref = storage.getReferenceFromUrl(storageURL + img)
            imageReferences.add(ref)
        }

        return imageReferences
    }


}