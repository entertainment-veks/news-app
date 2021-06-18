package entertainment.veks.newsapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import entertainment.veks.newsapp.cache.NewsItem
import entertainment.veks.newsapp.data.OfflineUseCase
import entertainment.veks.newsapp.data.OnlineUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllNewsViewModel(
    onlineUseCase: OnlineUseCase,
    offlineUseCase: OfflineUseCase,
    application: Application
) : ViewModel() {
    private val _allNewsDataList = MutableLiveData<List<NewsItem>>()
    val allNewsDataList: LiveData<List<NewsItem>> = _allNewsDataList

    init {
        if (isOnline(application)) {
            viewModelScope.launch(Dispatchers.IO) {
                _allNewsDataList.postValue(onlineUseCase.execute())
            }
        } else {
            Toast.makeText(application, "Cannot connect to the internet", Toast.LENGTH_LONG).show()
            viewModelScope.launch(Dispatchers.IO) { //there multithreading is not required
                _allNewsDataList.postValue(offlineUseCase.execute())
            }
        }
    }

    private fun isOnline(application: Application) : Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}