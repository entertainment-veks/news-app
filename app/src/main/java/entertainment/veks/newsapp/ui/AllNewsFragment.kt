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
import entertainment.veks.newsapp.FragmentManagerActivity
import entertainment.veks.newsapp.R
import entertainment.veks.newsapp.item.AllNewsItem
import org.koin.android.ext.android.inject

const val BASE_URL: String = "https://dev.by"

class AllNewsFragment : Fragment() {

    private val viewModel : AllNewsViewModel by inject()

    private val adapter = AllNewsAdapter()

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
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        viewModel.allNewsDataList.observe(viewLifecycleOwner, { quantityList ->
            val adapterList = mutableListOf<AllNewsItem>()
            adapterList.addAll(quantityList)
            adapter.insertData(adapterList)
        })

        return v
    }

    inner class AllNewsAdapter : RecyclerView.Adapter<AllNewsAdapter.AllNewsViewHolder>() {
        private var items: List<AllNewsItem> = listOf()

        fun insertData(adapterList: MutableList<AllNewsItem>) {
            items = adapterList
            notifyDataSetChanged()
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
}