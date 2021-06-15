package entertainment.veks.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import entertainment.veks.newsapp.data.CallBack
import entertainment.veks.newsapp.data.NewsUseCase
import entertainment.veks.newsapp.item.AllNewsItem

class AllNewsViewModel(getNewsUseCase: NewsUseCase) : ViewModel() {
    private val _allNewsDataList = MutableLiveData<List<AllNewsItem>>()
    val allNewsDataList : LiveData<List<AllNewsItem>> = _allNewsDataList

    init {
        getNewsUseCase.execute(object : CallBack<List<AllNewsItem>> {
            override fun onSuccess(result: List<AllNewsItem>) {
                _allNewsDataList.value = result
            }
        })
    }
}