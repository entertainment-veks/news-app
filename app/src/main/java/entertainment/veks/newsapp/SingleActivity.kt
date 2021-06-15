package entertainment.veks.newsapp

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import entertainment.veks.newsapp.item.AllNewsItem
import entertainment.veks.newsapp.ui.AllNewsFragment
import entertainment.veks.newsapp.ui.DetailVPFragment

class SingleActivity : AppCompatActivity(), FragmentManagerActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        supportFragmentManager.beginTransaction()
            .replace(R.id.as_container, AllNewsFragment.newInstance()).commit()
    }

    override fun getAppBar(): ActionBar {
        return supportActionBar!!
    }

    override fun showNewDetailFragment(allItems : List<AllNewsItem>, position : Int) {
        supportFragmentManager.beginTransaction()
            .addToBackStack("main_stack")
            .replace(R.id.as_container, DetailVPFragment.newInstance(allItems, position)).commit()
    }
}

interface FragmentManagerActivity {
    fun getAppBar(): ActionBar
    fun showNewDetailFragment(allItems : List<AllNewsItem>, position : Int)
}