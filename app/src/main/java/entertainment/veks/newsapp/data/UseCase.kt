package entertainment.veks.newsapp.data

import entertainment.veks.newsapp.cache.NewsItem
import kotlinx.coroutines.internal.synchronized

interface GetDataUseCase<T> {
    fun execute(page : T) : List<NewsItem>
}

class OnlineGetDataUseCase(
    private val clearCacheRepository: ClearCacheRepository,
    private val addCacheRepository: AddCacheRepository,
    private val getDataFromSiteRepository: GetDataFromSiteRepository
) : GetDataUseCase<Int> {
    override fun execute(page : Int): List<NewsItem> {
        if (page <= 1) {
            clearCacheRepository.execute(Unit)
        }

        val dataFromCurrentPage = getDataFromSiteRepository.execute(page)
        addCacheRepository.execute(dataFromCurrentPage)
        return dataFromCurrentPage
    }
}

class OfflineGetDataUseCase(
    private val getCacheRepository: GetCacheRepository
) : GetDataUseCase<Unit> {
    override fun execute(page : Unit): List<NewsItem> = getCacheRepository.execute(Unit)
}