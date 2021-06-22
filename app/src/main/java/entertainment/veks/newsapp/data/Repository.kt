package entertainment.veks.newsapp.data

import entertainment.veks.newsapp.NEWS_URL
import entertainment.veks.newsapp.REFERRER
import entertainment.veks.newsapp.USER_AGENT
import entertainment.veks.newsapp.cache.NewsDao
import entertainment.veks.newsapp.cache.NewsItem
import org.jsoup.Jsoup

interface Repository<I, O> {
    fun execute(input : I) : O
}

class GetDataFromSiteRepository : Repository<Int, List<NewsItem>> {
    override fun execute(page : Int) : List<NewsItem> {
        var url = NEWS_URL

        if (page > 1) { url = "$url?page=$page" }

        val result = Jsoup
            .connect(url)
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

class AddCacheRepository(private val newsDao: NewsDao) : Repository<List<NewsItem>, Unit> {
    override fun execute(input : List<NewsItem>) {
        newsDao.insertAll(input)
    }
}

class GetCacheRepository(private val newsDao: NewsDao) : Repository<Unit, List<NewsItem>> {
    override fun execute(input: Unit): List<NewsItem> {
        return newsDao.getAll()
    }
}

class ClearCacheRepository(private val newsDao: NewsDao) : Repository<Unit, Unit> {
    override fun execute(input: Unit) {
        newsDao.clearAll()
    }
}