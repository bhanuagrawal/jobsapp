package com.dabblepoint.freelancingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.dabblepoint.freelancingapp.data.Repo
import com.dabblepoint.freelancingapp.models.RequestStatus

class UserDetailViewModel : ViewModel() {

    private val repo: Repo = Repo()

    val userAddStatus: MutableLiveData<RequestStatus> = MutableLiveData()

    fun createUser(name: String, mob: String, role: String){
        repo.createUser(name, mob, role, userAddStatus)
    }
}
