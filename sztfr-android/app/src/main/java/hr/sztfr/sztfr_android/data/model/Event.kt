package hr.sztfr.sztfr_android.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Event(var id: String,
            val imgSrcUrl: String,
            val title: String,
            val startTime: String,
            val location: String,
            val organisation: String,
            val tags: List<String>,
            val description: String) : Parcelable