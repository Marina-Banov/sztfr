package hr.sztfr.sztfr_android.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
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

) : Parcelable{
    constructor():this("","","", "", "", false,"", arrayOf("").toList(), "")
}