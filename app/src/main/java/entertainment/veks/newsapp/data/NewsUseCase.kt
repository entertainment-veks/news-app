package entertainment.veks.newsapp.data

import entertainment.veks.newsapp.item.AllNewsItem
import org.jsoup.nodes.Document

const val NEWS_URL = "https://dev.by/news/"

interface NewsUseCase {
    fun execute(callback: CallBack<List<AllNewsItem>>)
}

class NewsUseCaseImpl(private val repository: Repository) : NewsUseCase {
    override fun execute(callback: CallBack<List<AllNewsItem>>) {
        repository.getDataFromSite(NEWS_URL, object : CallBack<Document> {
            override fun onSuccess(result: Document) {
                val listItems = mutableListOf<AllNewsItem>()

                result.select("div.card_media").forEach {
                    val currentItem = AllNewsItem()
                    currentItem.iconUrl = it.select("img.card__img").attr("src")
                    currentItem.url = it.select("div.card__body").select("a").attr("href")
                    currentItem.title = it.select("div.card__body").select("a").text()

                    listItems.add(currentItem)
                }

                callback.onSuccess(listItems)
            }
        })
    }
}