package com.coding.stackexchange.model

import com.google.gson.annotations.SerializedName

/**
 * Model to store the Question related information that is fetched using the
 * stackexchange API.
 */
data class Question(

    @SerializedName(TITLE) var title: String? = null,
    @SerializedName(ANSWER_COUNT) var answerCount: Int = 0,
    @SerializedName(IS_ANSWERED) var isAnswered: Boolean = false,
    @SerializedName(VIEW_COUNT) var viewCount: Int = 0,
    @SerializedName(SCORE) var score: Int = 0

) {
    companion object {
        const val TITLE = "title"
        const val ANSWER_COUNT = "answer_count"
        const val IS_ANSWERED = "is_answered"
        const val VIEW_COUNT = "view_count"
        const val SCORE = "score"

    }
}