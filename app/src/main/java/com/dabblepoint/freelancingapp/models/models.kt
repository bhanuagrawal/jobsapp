package com.dabblepoint.freelancingapp.models

import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.ArrayList


data class User( var name: String,
                 var mob: String,
                 var role: String,
                 var rating: Number = 0,
                 var akills: ArrayList<String> = arrayListOf())

data class Project(var id: String,
                   var title: String,
                   var akills: ArrayList<String>,
                   var date_created: Date = Date(),
                   var created_by: MutableLiveData<User> = MutableLiveData(),
                   var requests: MutableLiveData<ArrayList<WorkRequest>> = MutableLiveData())


data class WorkRequest(var request_id: String,
                       var project: Project,
                       var user: MutableLiveData<User> = MutableLiveData(),
                       var employee_rating: Number = 0,
                       var employer_rating: Number = 0,
                       var assigned: Boolean=false,
                       var completed:Boolean = false )