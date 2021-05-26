package hr.sztfr.sztfr_android.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.data.model.SurveyModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

enum class SurveyFilter(val value: Int) {
    ALL(0),
    PUBLISHED(1),
    UNPUBLISHED(2),
}

class SurveysRepository {
    companion object {
        private const val COLLECTION_NAME = "surveys"
        private const val PUBLISHED = "published"
    }

    private val db = FirebaseFirestore.getInstance()
    private val surveysCollection = db.collection(COLLECTION_NAME)

    suspend fun get(filter: SurveyFilter) = withContext(Dispatchers.IO) {
        try {
            val querySnapshot = if (filter == SurveyFilter.ALL) {
                surveysCollection.get().await()
            } else {
                val filterValue = (filter == SurveyFilter.PUBLISHED)
                surveysCollection.whereEqualTo(PUBLISHED, filterValue)
                    .get()
                    .await()
            }
            val result = ArrayList<SurveyModel>()
            for (survey in querySnapshot) {
                result.add(survey.toObject(SurveyModel::class.java))
            }
            result
        } catch (e: Exception) { ArrayList() }
    }

    suspend fun get(id: String) = withContext(Dispatchers.IO) {
        try {
            surveysCollection.document(id)
                .get()
                .await()
                .toObject(SurveyModel::class.java)
        } catch (e: Exception) { SurveyModel() }
    }
}