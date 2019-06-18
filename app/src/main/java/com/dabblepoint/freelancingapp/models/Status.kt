package com.dabblepoint.freelancingapp.models


data class RequestStatus (var status: Status, var data: Any? = null, var message:String = "" ){
    enum class Status{
        NOT_REQUESTED, REQUESTED, SUCCESS, FAILURE
    }
}