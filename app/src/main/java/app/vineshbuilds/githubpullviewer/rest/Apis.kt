package app.vineshbuilds.githubpullviewer.rest

import app.vineshbuilds.githubpullviewer.model.PR
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Apis {
    @GET("/repos/repoPath/pulls")
    fun getOpenPulls(
            @Path("repoPath") repoPath: String
    ): Single<List<PR>>
}