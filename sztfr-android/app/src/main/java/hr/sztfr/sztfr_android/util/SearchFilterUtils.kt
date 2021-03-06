package hr.sztfr.sztfr_android.util

import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.model.SurveyModel
import java.util.ArrayList

fun <T: Filterable> filterByTags(originalList: ArrayList<T>,
                                 tags: ArrayList<String>?): ArrayList<T> {
    if (tags == null || tags.size == 0) {
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
                           query: String?): ArrayList<T> {
    if (query == null || query.isEmpty()) {
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

fun filterByStatus(originalList: ArrayList<SurveyModel>, status: Boolean):
        ArrayList<SurveyModel> {
    val result = ArrayList<SurveyModel>()
    for (item in originalList) {
        if (item.published == status) {
            result.add(item)
        }
    }
    return result
}
