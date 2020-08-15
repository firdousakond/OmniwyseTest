package com.omniwyse.firdous.service

import com.omniwyse.firdous.model.UserModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("users")
    fun getUsers(@Query("since") since: Int, @Query("perpage") perPage: Int) : Single<List<UserModel>>
}