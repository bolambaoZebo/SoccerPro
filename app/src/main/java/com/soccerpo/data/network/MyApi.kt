package com.soccerpo.data.network

import com.soccerpo.data.network.response.SoccerResponse
import com.soccerpo.data.network.response.VideoResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MyApi {

    @GET(".netlify/functions/api")
    suspend fun getSoccerList() : Response<SoccerResponse>

    @GET(".netlify/functions/api/video")
    suspend fun getVideoList(
//        @Query("page") pageIndex: Int,
//        @Query("limit") pageLimit: Int
    ) : Response<VideoResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return  Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://sleepy-turing-6de1dd.netlify.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}