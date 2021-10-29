package br.com.raveline.testgroovy

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaylistsFeature {
    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
       // assertDisplayed("Hello World!")
    }

    @Test
    fun displayListOfPlaylists() {

        Thread.sleep(2000)
        assertRecyclerViewItemCount(R.id.recyclerViewFragmentPlaylist, 10)

        onView(
            allOf(
                withId(R.id.tvTitleItemAdapter),
                isDescendantOfA(nthChildOf(withId(R.id.recyclerViewFragmentPlaylist), 0))
            )
        ).check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tvCategoryItemAdapter),
                isDescendantOfA(nthChildOf(withId(R.id.recyclerViewFragmentPlaylist), 0))
            )
        ).check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.ivItemAdapter),
                isDescendantOfA(nthChildOf(withId(R.id.recyclerViewFragmentPlaylist), 0))
            )
        ).check(matches(withDrawable(R.drawable.playlist)))
            .check(matches(isDisplayed()))
    }

}


fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("position $childPosition of parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            if (view.parent !is ViewGroup) return false
            val parent = view.parent as ViewGroup

            return (parentMatcher.matches(parent)
                    && parent.childCount > childPosition
                    && parent.getChildAt(childPosition) == view)
        }
    }
}