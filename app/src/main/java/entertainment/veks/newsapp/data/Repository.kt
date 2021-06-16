package entertainment.veks.newsapp.data

import android.os.AsyncTask
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36"
const val REFERRER = "http://www.google.com"

interface Repository {
    fun getDataFromSite(url: String) : Document
}

class RepositoryImpl : Repository {

    override fun getDataFromSite(url: String) : Document {
        return Jsoup
            .connect(url)
            .userAgent(USER_AGENT)
            .referrer(REFERRER)
            .get()
    }
}