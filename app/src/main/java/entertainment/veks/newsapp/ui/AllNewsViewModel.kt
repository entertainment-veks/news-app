package entertainment.veks.newsapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.lifecycle.*
import entertainment.veks.newsapp.cache.NewsItem
import entertainment.veks.newsapp.data.OfflineGetDataUseCase
import entertainment.veks.newsapp.data.OnlineGetDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllNewsViewModel(
    private val onlineUseCase: OnlineGetDataUseCase,
    private val offlineUseCase: OfflineGetDataUseCase,
    private val application: Application
) : ViewModel() {

    private val _allNewsDataList = MutableLiveData<List<NewsItem>>()
    val allNewsDataList: LiveData<List<NewsItem>> = _allNewsDataList

    var currentPage = 0

    init { downloadMore() }

    fun downloadMore() {
        currentPage++
        loadData(currentPage)
    }

    private fun loadData(page : Int) {
        if (isOnline(application)) {
            viewModelScope.launch(Dispatchers.IO) {
                val result : MutableList<NewsItem> = _allNewsDataList.value?.toMutableList() ?: mutableListOf()
                result.addAll(onlineUseCase.execute(page))
                _allNewsDataList.postValue(result)
            }
        } else {
            Toast.makeText(application, "Cannot connect to the internet", Toast.LENGTH_LONG).show()
            viewModelScope.launch(Dispatchers.IO) {
                _allNewsDataList.postValue(offlineUseCase.execute(Unit))
            }
        }
    }

    private fun isOnline(application: Application) : Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}