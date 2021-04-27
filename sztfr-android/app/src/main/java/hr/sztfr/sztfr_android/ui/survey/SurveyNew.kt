package hr.sztfr.sztfr_android.ui.survey
import java.util.ArrayList

class SurveyNew() {
    companion object {
        fun createListOfItems(num: Int) : ArrayList<SurveyNew> {
            val item = ArrayList<SurveyNew>()

            for (i in 1..num) {
                item.add(SurveyNew())
            }
            return item
        }
    }
}