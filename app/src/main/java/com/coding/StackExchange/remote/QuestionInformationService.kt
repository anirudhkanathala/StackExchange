package com.coding.stackexchange.remote

import com.coding.stackexchange.PAGE
import com.coding.stackexchange.model.Question
import com.coding.stackexchange.model.ResponseBody
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Contains remote service calls related to the question list.
 */
interface QuestionInformationService {

    /**
     * Service call to fetch the remote questions data.
     */
    @GET("search/advanced?order=desc&sort=activity&accepted=True&answers=1&pagesize=100&site=stackoverflow")
    fun fetchQuestionList(@Query(PAGE) page: Int): Call<ResponseBody>

}
