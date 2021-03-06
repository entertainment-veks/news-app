package entertainment.veks.newsapp

import android.app.Application
import androidx.room.Room
import entertainment.veks.newsapp.cache.NewsDB
import entertainment.veks.newsapp.cache.NewsDao
import entertainment.veks.newsapp.data.*
import entertainment.veks.newsapp.ui.AllNewsViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

lateinit var database : NewsDB

class NewsApp : Application() {
    override fun onCreate() {
        database = Room.databaseBuilder(
            applicationContext, NewsDB::class.java, "news_cache"
        ).build()

        startKoin {
            modules(appModule)
        }

        super.onCreate()
    }

    private val appModule = module {
        single { AllNewsViewModel(get(), get(), get()) }

        single { this@NewsApp as Application }

        single { OnlineGetDataUseCase(get(), get(), get()) }
        single { OfflineGetDataUseCase(get()) }

        single { ClearCacheRepository(get()) }
        single { AddCacheRepository(get()) }
        single { GetDataFromSiteRepository() }
        single { GetCacheRepository(get()) }

        single { database.newsDao as NewsDao }
    }
}