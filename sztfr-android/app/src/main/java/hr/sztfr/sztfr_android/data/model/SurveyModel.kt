package hr.sztfr.sztfr_android.data.model

import com.google.firebase.firestore.DocumentId

data class SurveyModel (

    @DocumentId
    val documentId: String,

    var title: String,

    var image: String,

    var shortDescription: String,

    var description: String,

    var published: Boolean,

    var resultDescription: String,

    var resultImages: List<String>,

    var googleFormsURL: String

){
    constructor():this("","","", "", "", false,"", arrayOf("").toList(), "")
}