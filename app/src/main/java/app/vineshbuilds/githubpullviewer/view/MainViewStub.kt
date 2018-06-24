package app.vineshbuilds.githubpullviewer.view

import app.vineshbuilds.githubpullviewer.model.PR

interface MainViewStub {
    fun showLoading()
    fun hideLoading()
    fun onResult(pullRequests: List<PR>)
    fun showError(message: String)
    fun hideError()
}