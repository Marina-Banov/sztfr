package hr.sztfr.sztfr_android.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Event(var id: String = "",
            val imgSrcUrl: String = "",
            override val title: String = "",
            val startTime: String = "",
            val location: String = "",
            val organisation: String = "",
            override val tags: List<String> = listOf(),
            val description: String = "") : Parcelable, Filterable