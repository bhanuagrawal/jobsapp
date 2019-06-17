package com.dabblepoint.freelancingapp


data class RequestStatus ( var status: Status, var message:String = "" ){
    enum class Status{
        REQUESTED, SUCCESS, FAILURE
    }
}