package hr.sztfr.sztfr_android.data.model

import android.graphics.drawable.Drawable

class Event(val id: String,
            val imgSrcUrl: Drawable,
            val title: String,
            val startTime: String,
            val location: String,
            val organisation: String,
            val tags: List<String>,
            val description: String)