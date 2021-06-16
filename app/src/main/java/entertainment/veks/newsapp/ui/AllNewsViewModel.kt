package entertainment.veks.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import entertainment.veks.newsapp.data.NewsUseCase
import entertainment.veks.newsapp.item.AllNewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllNewsViewModel(getNewsUseCase: NewsUseCase) : ViewModel() {
    private val _allNewsDataList = MutableLiveData<List<AllNewsItem>>()
    val allNewsDataList : LiveData<List<AllNewsItem>> = _allNewsDataList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _allNewsDataList.postValue(getNewsUseCase.execute())
        }
    }
}