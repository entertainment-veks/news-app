package entertainment.veks.newsapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import entertainment.veks.newsapp.ui.AllNewsFragment
import entertainment.veks.newsapp.ui.SingleActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailOpeningTest  {

    @Test
    fun test() {
        ActivityScenario.launch(SingleActivity::class.java)

        Thread.sleep(2000) //for network request

        onView(withId(R.id.fan_recycler)).perform(RecyclerViewActions
            .actionOnItemAtPosition<AllNewsFragment.AllNewsAdapter.AllNewsViewHolder>(1, click()))

        onView(withId(R.id.fvd_viewpager)).check(matches(isDisplayed()))
    }
}