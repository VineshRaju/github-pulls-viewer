package app.vineshbuilds.githubpullviewer

import app.vineshbuilds.githubpullviewer.presenter.PresenterImpl
import org.junit.Test

class AppTests {
    @Test
    fun inputVerificationTest() {
        val goodInputs = listOf(
                "flutter/flutter",
                "square/okhttp",
                "ReactiveX/rxjava",
                "VineshRaju/github-pull-viewer",
                "Someone_great/A_great_project"
        )
        goodInputs.forEach { assert(PresenterImpl.verify(it)) }

        val badInputs = listOf(
                "/",
                "",
                null,
                "Vinesh Raju/github-pull-viewer",
                "Someone_great/A_great project"
        )
        badInputs.forEach {
            //println("testing $it")
            assert(!PresenterImpl.verify(it))
        }
    }
}
