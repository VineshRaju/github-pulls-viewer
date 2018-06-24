package app.vineshbuilds.githubpullviewer.presenter

interface PresenterStub {
    fun getNextPage()
    fun refresh(repo: String)
    fun onDestroy()
}