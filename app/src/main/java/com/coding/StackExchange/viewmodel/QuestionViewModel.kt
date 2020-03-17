package com.coding.stackexchange.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coding.stackexchange.model.Question
import com.coding.stackexchange.model.ResponseBody
import com.coding.stackexchange.remote.QuestionInformationService
import com.coding.stackexchange.remote.ServiceProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionViewModel : ViewModel() {

    // Retrofit service to fetch for the questions list.
    private val service = ServiceProvider.setup.create(QuestionInformationService::class.java)
    val questionsListObservable = MutableLiveData<List<Question>>()
    val onFailureObservable = MutableLiveData<String>()
    var page = 0
    private var hasMore : Boolean? = null

    /**
     * Function to fetch for the latest Questions data from the internet using the API.
     */
    fun getQuestionList(){

        if(hasMore!= null && !hasMore!!){
            onFailureObservable.value = RESPONSE_FAILURE_MESSAGE
            return
        }
        page++
        // Service call to fetch for the remote questions data.
        service.fetchQuestionList(page).enqueue(object : Callback<ResponseBody>{

            // Handles the response containing the questions data once the
            // result is available.
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful)
                    questionsListObservable.value = response.body()?.questions
                else
                    onFailureObservable.value = response.message()
            }

            // Handles any exceptions that had occurred during the service call.
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onFailureObservable.value = t.localizedMessage
            }

        })
    }

    companion object {
        const val RESPONSE_FAILURE_MESSAGE = "API Reached the limit for getting Question data"
    }
}

