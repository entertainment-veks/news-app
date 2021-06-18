package entertainment.veks.newsapp.data

import entertainment.veks.newsapp.NEWS_URL
import entertainment.veks.newsapp.REFERRER
import entertainment.veks.newsapp.USER_AGENT
import entertainment.veks.newsapp.cache.NewsDao
import entertainment.veks.newsapp.cache.NewsItem
import org.jsoup.Jsoup

interface GetRepository {
    fun execute() : List<NewsItem>
}

interface SetRepository {
    fun execute(arg : List<NewsItem>)
}

class GetDataFromSiteRepository : GetRepository {
    override fun execute() : List<NewsItem> {
        val result = Jsoup
            .connect(NEWS_URL)
            .userAgent(USER_AGENT)
            .referrer(REFERRER)
            .get()

        val listItems = mutableListOf<NewsItem>()

        result.select("div.card_media").forEach {
            val currentItem = NewsItem()
            currentItem.iconUrl = it.select("img.card__img").attr("src")
            currentItem.url = it.select("div.card__body").select("a").attr("href")
            currentItem.title = it.select("div.card__body").select("a").text()

            listItems.add(currentItem)
        }

        return listItems
    }
}

class UpdateCacheRepository(private val newsDao: NewsDao) : SetRepository {
    override fun execute(arg : List<NewsItem>) {
        newsDao.clearAll()
        newsDao.insertAll(arg)
    }
}

class GetDataFromCacheRepository(private val newsDao: NewsDao) : GetRepository {
    override fun execute(): List<NewsItem> {
        return newsDao.getAll()
    }
}