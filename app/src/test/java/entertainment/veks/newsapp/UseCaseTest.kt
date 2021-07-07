package entertainment.veks.newsapp

import entertainment.veks.newsapp.cache.NewsDao
import entertainment.veks.newsapp.cache.NewsItem
import entertainment.veks.newsapp.data.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OfflineUseCaseTest {
    private val useCase = OfflineGetDataUseCase(FakeGetCacheRepo())

    @Test
    fun executeTest() {
        val expected = listOf(NewsItem("11", "12", "13"),
            NewsItem("21", "22", "23"),
            NewsItem("31", "32", "33"))
        val actual = useCase.execute(Unit)

        assertEquals(expected, actual)
    }
}

@RunWith(JUnit4::class)
class OnlineUseCaseTest {
    private val useCase = OnlineGetDataUseCase(FakeClearCacheRepo(), FakeAddCacheRepo(), FakeGetDataFromSiteRepo())

    @Test
    fun executeTest() {
        val expected = listOf(NewsItem("11", "12", "13"),
            NewsItem("21", "22", "23"),
            NewsItem("31", "32", "33"))

        val actual = useCase.execute(1)

        assertEquals(expected, actual)
    }
}

class FakeClearCacheRepo : ClearCacheRepository(FakeDao()) {
    override fun execute(input: Unit) {}
}

class FakeAddCacheRepo : AddCacheRepository(FakeDao()) {
    override fun execute(input: List<NewsItem>) {}
}

class FakeGetDataFromSiteRepo : GetDataFromSiteRepository() {
    override fun execute(page: Int): List<NewsItem> {
        return listOf(NewsItem("11", "12", "13"),
            NewsItem("21", "22", "23"),
            NewsItem("31", "32", "33"))
    }
}

class FakeGetCacheRepo : GetCacheRepository(FakeDao()) {
    override fun execute(input: Unit): List<NewsItem> {
        return listOf(NewsItem("11", "12", "13"),
            NewsItem("21", "22", "23"),
            NewsItem("31", "32", "33"))
    }
}

class FakeDao : NewsDao {
    override fun insertAll(news: List<NewsItem>) {}
    override fun getAll(): List<NewsItem> {return listOf()}
    override fun clearAll() {}
}