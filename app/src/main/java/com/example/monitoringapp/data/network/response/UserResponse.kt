package com.example.monitoringapp.data.network.response

import com.example.monitoringapp.data.model.User
import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    var success: Boolean?,
    var clearRes: ObjectResponse<User>?
)


