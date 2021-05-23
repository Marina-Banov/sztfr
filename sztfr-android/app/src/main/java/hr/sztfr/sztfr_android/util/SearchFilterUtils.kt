package hr.sztfr.sztfr_android.util

import hr.sztfr.sztfr_android.data.model.Filterable
import java.util.ArrayList

fun <T: Filterable> filterByTags(originalList: ArrayList<T>,
                                 tags: ArrayList<String>): ArrayList<T> {
    if (tags.size == 0) {
        return originalList
    }

    val result = ArrayList<T>()
    for (item in originalList) {
        for (tag in tags) {
            if (item.tags.contains(tag)){
                result.add(item)
                break
            }
        }
    }
    return result
}

fun <T: Filterable> search(originalList: ArrayList<T>,
                           query: String): ArrayList<T> {
    if (query.isEmpty()) {
        return originalList
    }

    val result = ArrayList<T>()
    for (item in originalList) {
        if (item.title.toLowerCase().contains(query.toLowerCase())) {
            result.add(item)
        }
    }
    return result
}
