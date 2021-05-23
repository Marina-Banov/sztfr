package hr.sztfr.sztfr_android.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SurveyModel (
    @DocumentId
    val documentId: String = "",
    override var title: String = "",
    var image: String = "",
    override var tags: List<String> = listOf(),
    var shortDescription: String = "",
    var description: String = "",
    var published: Boolean = false,
    var resultDescription: String = "",
    var resultImages: List<String> = listOf(),
    var googleFormsURL: String = ""
) : Parcelable, Filterable