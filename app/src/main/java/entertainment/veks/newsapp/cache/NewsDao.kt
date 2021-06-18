package entertainment.veks.newsapp.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert
    fun insertAll(news : List<NewsItem>)

    @Query("SELECT * from newsitem")
    fun getAll(): List<NewsItem>

    @Query("DELETE from newsitem")
    fun clearAll()
}