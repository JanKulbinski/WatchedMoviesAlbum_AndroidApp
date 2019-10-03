package com.example.watchedonnetflix.ImdbAPI

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbAPI {
@GET("/")
        fun search(@Query("t") t : String, @Query("apikey") apikey : String, @Query("plot") plot : String) : Call<OMDbDTO>

}