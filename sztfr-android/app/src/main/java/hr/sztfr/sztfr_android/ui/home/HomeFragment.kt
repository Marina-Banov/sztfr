package hr.sztfr.sztfr_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.*
// import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.R
// import hr.sztfr.sztfr_android.ui.details.DetailsFragment


class HomeFragment : Fragment() {
    private lateinit var items: ArrayList<HomeRow>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<View>(R.id.home_recycler_view) as RecyclerView
        items = HomeRow.createListOfItems(5)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = HomeAdapter(items)
            HomeAdapter(items).onBtnClick = { items
                replaceFragment()
            }
        }
    }

    private fun replaceFragment() {
    /*    val fragmentTransition = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransition?.replace(R.id.fragmentContainer, DetailsFragment())?.addToBackStack(Fragment::class.java.simpleName)
            ?.commit()*/
    }
}



