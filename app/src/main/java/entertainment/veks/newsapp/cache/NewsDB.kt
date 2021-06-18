package entertainment.veks.newsapp.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsItem::class], version = 1)
abstract class NewsDB : RoomDatabase() {
    abstract val newsDao: NewsDao
}