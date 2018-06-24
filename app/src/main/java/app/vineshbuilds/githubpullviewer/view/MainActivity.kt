package app.vineshbuilds.githubpullviewer.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import app.vineshbuilds.githubpullviewer.R
import app.vineshbuilds.githubpullviewer.adapter.PRListAdapter
import app.vineshbuilds.githubpullviewer.model.PR
import app.vineshbuilds.githubpullviewer.presenter.PresenterImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainViewStub {

    private val prs = mutableListOf<PR>()
    private val presenter = PresenterImpl(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        rvPRs.adapter = adapter
        rvPRs.layoutManager = layoutManager

        bSearch.setOnClickListener {
            presenter.getPulls(etRepoPath.text.toString())
        }
    }

    override fun showError(message: String) {
        etRepoPath.error = message
    }

    override fun hideError() {
        etRepoPath.error = null
    }

    override fun showLoading() {
        pbProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbProgress.visibility = View.GONE
    }

    override fun onResult(pullRequests: List<PR>) {
        prs.clear()//removing old results
        prs.addAll(pullRequests)//adding the new results
        adapter.notifyDataSetChanged()
    }

    private val adapter: PRListAdapter by lazy {
        PRListAdapter(prs)
    }
}
