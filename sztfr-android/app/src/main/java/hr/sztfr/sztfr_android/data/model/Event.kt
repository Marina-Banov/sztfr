package hr.sztfr.sztfr_android.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    @DocumentId
    override var documentId: String = "",
    var imgSrcUrl: String = "",
    override var title: String = "",
    var startTime: String = "",
    var location: String = "",
    var organisation: String = "",
    override var tags: List<String> = listOf(),
    var description: String = ""
) : Parcelable, Filterable