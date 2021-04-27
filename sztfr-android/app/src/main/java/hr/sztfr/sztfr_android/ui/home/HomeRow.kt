package hr.sztfr.sztfr_android.ui.home
import java.util.ArrayList

class HomeRow(val title: String, val time: String, val location: String) {

    companion object {
        private var lastItemId = 0
        fun createListOfItems(num: Int) : ArrayList<HomeRow> {
            val item = ArrayList<HomeRow>()

            for (i in 1..num) {
                item.add(HomeRow("title" + (lastItemId++), "sub, 11.03", "tu"))
            }
            return item
        }
    }
}