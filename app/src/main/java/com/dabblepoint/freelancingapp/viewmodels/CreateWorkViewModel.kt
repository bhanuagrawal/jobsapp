package com.dabblepoint.freelancingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.dabblepoint.freelancingapp.data.Repo
import com.dabblepoint.freelancingapp.models.RequestStatus
import com.dabblepoint.freelancingapp.models.User


class CreateWorkViewModel : ViewModel() {

    private val repo: Repo = Repo()

    val projectAddStatus: MutableLiveData<RequestStatus> = MutableLiveData()

    fun createProject(title: String, skills: ArrayList<String>, user: User?) {

        user?.let {
            repo.addProject(title, user.mob, skills, projectAddStatus)
        }

    }
    // TODO: Implement the ViewModel
}
