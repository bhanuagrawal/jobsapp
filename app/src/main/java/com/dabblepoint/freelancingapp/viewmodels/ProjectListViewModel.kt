package com.dabblepoint.freelancingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.dabblepoint.freelancingapp.data.Repo
import com.dabblepoint.freelancingapp.models.Project
import com.dabblepoint.freelancingapp.models.RequestStatus
import com.dabblepoint.freelancingapp.models.User

class ProjectListViewModel : ViewModel() {

    private val repo: Repo = Repo()

    val projectsFetchStatus: MutableLiveData<RequestStatus> = MutableLiveData()

    fun getProjectsCreatedBy(user: User): MutableLiveData<ArrayList<Project>>{
        return repo.getProjectsCreatedBy(user, projectsFetchStatus)
    }
    // TODO: Implement the ViewModel
}
