package hr.sztfr.sztfr_android.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.firebase.firestore.DocumentId

@Parcelize
data class User (
    @DocumentId
    var uid: String = "",
    var isAdmin: Boolean = false,
    var email: String = "",
) : Parcelable