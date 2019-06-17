package com.dabblepoint.freelancingapp

import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.ArrayList

data class User( var name: String,
                 var mob: String,
                 var role: String,
                 var rating: String = "0",
                 var akills: ArrayList<String> = arrayListOf())

data class Work(var work_id: String,
                var title: String,
                var akills: ArrayList<String>,
                var date_created: Date = Date(),
                var created_by: MutableLiveData<User> = MutableLiveData(),
                var requests: ArrayList<User> = arrayListOf()
)


data class WorkRequest(var request_id: String)