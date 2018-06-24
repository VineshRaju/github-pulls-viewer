package app.vineshbuilds.githubpullviewer.view

interface ViewStub {
    fun isLoading(state: Boolean)
    fun onNextPageFetched(from: Int, count: Int)
    fun onRefresh()
    fun setError(message: String?)
}