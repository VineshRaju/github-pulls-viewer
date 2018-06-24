package app.vineshbuilds.githubpullviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import app.vineshbuilds.githubpullviewer.adapter.PRListAdapter
import app.vineshbuilds.githubpullviewer.model.PR
import app.vineshbuilds.githubpullviewer.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter: PRListAdapter by lazy {
        PRListAdapter(prs)
    }
    private val prs = mutableListOf<PR>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        rvPRs.adapter = adapter
        rvPRs.layoutManager = layoutManager

        bSearch.setOnClickListener {
            RestClient.apis.getOpenPulls("square/okhttp")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<PR>>() {
                        override fun onSuccess(pullRequests: List<PR>) {
                            prs.clear()
                            prs.addAll(pullRequests)
                            adapter.notifyDataSetChanged()
                        }

                        override fun onError(e: Throwable) {
                            prs.clear()
                            adapter.notifyDataSetChanged()
                            e.printStackTrace()
                        }

                    })
        }
    }
}
