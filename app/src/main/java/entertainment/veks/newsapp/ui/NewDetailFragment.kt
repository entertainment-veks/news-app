package entertainment.veks.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import entertainment.veks.newsapp.BASE_URL
import entertainment.veks.newsapp.R
import entertainment.veks.newsapp.cache.NewsItem
import java.util.*

const val ITEMS_KEY: String = "all_items_key"
const val POSITION_KET: String = "position_key"

class DetailVPFragment : Fragment() {

    companion object {
        fun newInstance(allItems : List<NewsItem>, position : Int) : DetailVPFragment {
            val bundle = Bundle().apply {
                this.putStringArrayList(ITEMS_KEY, allItems.map { it.url } as ArrayList<String>)
                this.putInt(POSITION_KET, position)
            }
            val fragment = DetailVPFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        if (activity is FragmentManagerActivity) {
            (activity as FragmentManagerActivity).getAppBar().hide()
        }

        val v = inflater.inflate(R.layout.fragment_new_detail, container, false)

        val vp = v.findViewById<ViewPager2>(R.id.fvd_viewpager)
        vp.adapter = arguments?.getStringArrayList(ITEMS_KEY)?.let { VPAdapter(it) }
        vp.currentItem = arguments?.getInt(POSITION_KET) ?: 0

        return v
    }

    class VPAdapter(
        private val items : List<String>
    ) : RecyclerView.Adapter<VPViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VPViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_vp_page, parent, false)
            return VPViewHolder(itemView)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: VPViewHolder, position: Int) = holder.itemView.run {
            holder.wv.loadUrl(BASE_URL + items[position])
        }
    }

    class VPViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wv: WebView = itemView.findViewById(R.id.fnd_webview)
    }
}