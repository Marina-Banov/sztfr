package hr.sztfr.sztfr_android.util

import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.repository.FirestoreUser
import hr.sztfr.sztfr_android.data.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun handleClick(item: Filterable) {
    if (FirestoreUser.value != null) {
        addFavorite(item)
    }
}

fun addFavorite(item: Filterable) {
    GlobalScope.launch {
        UserRepository().addFavorite(item)
    }
}

fun removeFavorite(item: Filterable) {

}