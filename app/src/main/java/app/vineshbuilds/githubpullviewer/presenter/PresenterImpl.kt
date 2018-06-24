package app.vineshbuilds.githubpullviewer.presenter

import app.vineshbuilds.githubpullviewer.model.PR
import app.vineshbuilds.githubpullviewer.rest.RestClient
import app.vineshbuilds.githubpullviewer.view.ViewStub
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PresenterImpl(val view: ViewStub) : PresenterStub {

    var hasMore = true
    val prs = mutableListOf<PR>()
    private var currentPage = 1
    private var currentRepo = ""
    private val disposable = CompositeDisposable()

    companion object {
        /**
         *Verifies if the given path is in the valid format
         * @return true if valid path else false
         */
        private val INPUT_PATTERN = Regex("[^\\s]+/[^\\s]+")

        const val PULLS_PER_PAGE = 10
        fun verify(path: String?) = INPUT_PATTERN.matches(path ?: "")
        //private fun verify(path: String?) = path?.isNotBlank() ?: false
    }

    //new search
    override fun refresh(repo: String) {
        if (currentRepo != repo) {
            prs.clear()//removing old results
            currentRepo = repo
            currentPage = 1
            hasMore = true
            if (verify(repo)) {
                view.onRefresh()
                getNextPage()
            } else {
                view.setError("Incorrect Format")
            }
        }
    }


    /**
     * grabs the next N PRs of the currentRepo from github
     */
    override fun getNextPage() {
        //when there is no more PRs
        if (!hasMore) return

        //hide error
        view.setError(null)
        view.isLoading(true)
        disposable.add(
                RestClient.apis.getOpenPulls(repoPath = currentRepo, page = currentPage, perPage = PULLS_PER_PAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<PR>>() {
                            override fun onSuccess(pullRequests: List<PR>) {
                                view.isLoading(false)
                                if (pullRequests.isNotEmpty()) {
                                    prs.addAll(pullRequests)//adding the new results
                                    view.onNextPageFetched((currentPage - 1) * PULLS_PER_PAGE, pullRequests.size)
                                    currentPage++//incrementing to next page
                                } else {
                                    //when the first page is empty
                                    if (currentPage == 1)
                                        view.setError("Empty PRs!")
                                }
                                //if its last page
                                if (pullRequests.size < PULLS_PER_PAGE)
                                    hasMore = false
                            }

                            override fun onError(e: Throwable) {
                                view.isLoading(false)
                                view.setError(e.message ?: "Something went wrong :(")
                            }
                        })
        )
    }

    override fun onDestroy() {
        disposable.dispose()
    }
}