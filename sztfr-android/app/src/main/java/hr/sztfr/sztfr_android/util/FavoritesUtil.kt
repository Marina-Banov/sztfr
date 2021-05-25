package hr.sztfr.sztfr_android.util

import android.util.Log
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.repository.FirestoreUser

fun handleClick(item: Filterable) {
    if (FirestoreUser.value != null) {
        addFavorite(item)
    }
}

fun addFavorite(item: Filterable) {
    Log.i("FavoritesUtil", "add favorite (" + item.documentId + ")")
}

fun removeFavorite(item: Filterable) {

}