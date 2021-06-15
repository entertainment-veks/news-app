package entertainment.veks.newsapp.data

interface CallBack<T> {
    fun onSuccess(result: T)
}