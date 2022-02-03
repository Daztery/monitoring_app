package com.example.monitoringapp.data.network.response

data class CollectionResponse<T>(
    var success: Boolean = false,
    var data: List<T> = emptyList(),
    //var params: Params? = Params(),
)

data class ObjectResponse<T>(
    var success: Boolean = false,
    var data: T? = null,
    //var params: Params? = Params(),
)