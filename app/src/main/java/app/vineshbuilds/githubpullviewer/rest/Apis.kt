package app.vineshbuilds.githubpullviewer.rest

import app.vineshbuilds.githubpullviewer.model.PR
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Apis {
    @GET("/repos/{repoPath}/pulls")
    fun getOpenPulls(
            @Path("repoPath", encoded = true) repoPath: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
            @Query("state") state: String = "open"
    ): Single<List<PR>>
}