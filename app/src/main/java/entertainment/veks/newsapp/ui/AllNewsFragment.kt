package entertainment.veks.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import entertainment.veks.newsapp.BASE_URL
import entertainment.veks.newsapp.R
import entertainment.veks.newsapp.VISIBLE_THRESHOLD
import entertainment.veks.newsapp.cache.NewsItem
import org.koin.android.ext.android.inject

class AllNewsFragment : Fragment() {

    private val viewModel: AllNewsViewModel by inject()

    private val adapter = AllNewsAdapter()
    private val lm = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    companion object {
        fun newInstance() = AllNewsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        if (activity is FragmentManagerActivity) {
            (activity as FragmentManagerActivity).getAppBar().show()
            (activity as FragmentManagerActivity).getAppBar().title = "News from dev.by"
        }

        val v = inflater.inflate(R.layout.fragment_all_news, container, false)

        v.findViewById<RecyclerView>(R.id.fan_recycler).apply {
            adapter = this@AllNewsFragment.adapter
            layoutManager = lm
            setOnScrollChangeListener(AllNewsListener(lm))
        }

        viewModel.allNewsDataList.observe(viewLifecycleOwner, { quantityList ->
            adapter.insertData(quantityList)
        })

        return v
    }

    inner class AllNewsAdapter : RecyclerView.Adapter<AllNewsAdapter.AllNewsViewHolder>() {
        private var items: List<NewsItem> = listOf()

        fun insertData(adapterList: List<NewsItem>) {
            val oldLength = items.size
            items = adapterList
            notifyItemRangeChanged(oldLength + 1, adapterList.size - oldLength)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNewsViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_new, parent, false)
            return AllNewsViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: AllNewsViewHolder, position: Int) {
            holder.title.text = items[position].title
            holder.item.setOnClickListener {
                if (activity is FragmentManagerActivity) {
                    (activity as FragmentManagerActivity).showNewDetailFragment(items, position)
                }
            }
            Glide.with(holder.icon.context)
                .load(BASE_URL + items[position].iconUrl)
                .into(holder.icon)
        }

        override fun getItemCount(): Int = items.size

        inner class AllNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.in_title)
            val icon: ImageView = itemView.findViewById(R.id.in_icon)
            val item: View = itemView
        }
    }

    inner class AllNewsListener(private val lm: LinearLayoutManager) :
        View.OnScrollChangeListener {

        override fun onScrollChange(v: View?, x: Int, y: Int, oldX: Int, oldY: Int) {
            if (lm.findFirstVisibleItemPosition() + lm.childCount >= lm.itemCount) {
                viewModel.downloadMore()
            }
        }
    }
}