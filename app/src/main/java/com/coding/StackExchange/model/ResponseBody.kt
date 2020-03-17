package com.coding.stackexchange.model

import com.google.gson.annotations.SerializedName

/**
 * Model to hold the response information provided by the stackexchange API.
 */
data class ResponseBody(
    @SerializedName(QUOTA_MAX) var quotaMax: Int? = 0,
    @SerializedName(QUOTA_REMAINING) var quotaRemaining: Int? = 0,
    @SerializedName(HAS_MORE) var hasMore: Boolean? = false,
    @SerializedName(ITEMS) var questions: ArrayList<Question>? = null
) {
    companion object {
        const val QUOTA_MAX = "quota_max"
        const val HAS_MORE = "has_more"
        const val ITEMS = "items"
        const val QUOTA_REMAINING = "quota_remaining"
    }
}