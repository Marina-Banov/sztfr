package hr.sztfr.sztfr_android.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Event(
    @DocumentId
    override var documentId: String = "",
    var image: String = "",
    override var title: String = "",
    var startTime: Date = Date(),
    var location: String = "",
    var organisation: String = "",
    override var tags: List<String> = listOf(),
    var description: String = ""
) : Parcelable, Filterable