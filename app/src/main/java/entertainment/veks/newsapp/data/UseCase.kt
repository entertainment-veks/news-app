package entertainment.veks.newsapp.data

import entertainment.veks.newsapp.cache.NewsItem

interface UseCase {
    fun execute() : List<NewsItem>
}

class OnlineUseCase(
    private val updateCacheRepository: UpdateCacheRepository,
    private val getDataFromSiteRepository: GetDataFromSiteRepository
) : UseCase {
    override fun execute(): List<NewsItem> {
        val result = getDataFromSiteRepository.execute()
        updateCacheRepository.execute(result)
        return result
    }
}

class OfflineUseCase(
    private val getDataFromCacheRepository: GetDataFromCacheRepository
) : UseCase {
    override fun execute(): List<NewsItem> {
        return getDataFromCacheRepository.execute()
    }
}