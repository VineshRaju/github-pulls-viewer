package app.vineshbuilds.githubpullviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import app.vineshbuilds.githubpullviewer.model.PR
import app.vineshbuilds.githubpullviewer.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RestClient.apis.getOpenPulls("square/okhttp")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PR>>() {
                    override fun onSuccess(pullRequests: List<PR>) {
                        Log.e("RES", pullRequests.toString())
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
    }
}
