package com.example.standfor.network

import com.example.standfor.model.LFList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPoint {

    @GET("dictionary.py")
    fun getAcronyms(@Query("sf") sf: String): Observable<List<LFList>>
}