package entertainment.veks.newsapp

import android.app.Application
import entertainment.veks.newsapp.data.NewsUseCase
import entertainment.veks.newsapp.data.NewsUseCaseImpl
import entertainment.veks.newsapp.data.Repository
import entertainment.veks.newsapp.data.RepositoryImpl
import entertainment.veks.newsapp.ui.AllNewsViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NewsApp : Application() {
    override fun onCreate() {
        startKoin {
            modules(appModule)
        }

        super.onCreate()
    }
}

val appModule = module {
    single { AllNewsViewModel(get()) }
    single { NewsUseCaseImpl(get()) as NewsUseCase }
    single { RepositoryImpl() as Repository }
}