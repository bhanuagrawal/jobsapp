package com.dabblepoint.freelancingapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class UserDetailViewModel : ViewModel() {

    private val repo: Repo = Repo()

    val userAddStatus: MutableLiveData<RequestStatus> = MutableLiveData()

    fun createUser(name: String, mob: String, role: String){
        repo.createUser(name, mob, role, userAddStatus)
    }
}
