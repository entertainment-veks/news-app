package entertainment.veks.newsapp.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsItem(
    @PrimaryKey
    var url: String = "https://dev.by/news/",
    var iconUrl: String = "https://dev.by/assets/logo-c39214c7aad5915941bcf4ccda40ac3641f2851d5ec7e897270da373ed9701ad.svg",
    var title: String = "New"
)