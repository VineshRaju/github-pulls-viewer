package app.vineshbuilds.githubpullviewer.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import app.vineshbuilds.githubpullviewer.R
import app.vineshbuilds.githubpullviewer.adapter.PRListAdapter
import app.vineshbuilds.githubpullviewer.presenter.PresenterImpl
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ViewStub {
    private val adapter: PRListAdapter by lazy {
        PRListAdapter(presenter.prs)
    }
    private val presenter = PresenterImpl(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        rvPRs.adapter = adapter
        rvPRs.layoutManager = layoutManager

        bSearch.setOnClickListener {
            presenter.refresh(etRepoPath.text.toString())
        }

        rvPRs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = recyclerView.layoutManager!!.childCount
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (presenter.hasMore && pbProgress.visibility == View.GONE) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - PresenterImpl.PULLS_PER_PAGE
                            && firstVisibleItemPosition >= 0) {
                        presenter.getNextPage()
                    }
                }
            }
        })
    }

    override fun setError(message: String?) {
        etRepoPath.error = message
    }

    override fun onNextPageFetched(from: Int, count: Int) {
        adapter.notifyItemRangeInserted(from, count)
    }


    override fun onRefresh() {
        adapter.notifyDataSetChanged()
    }

    override fun isLoading(state: Boolean) {
        pbProgress.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
