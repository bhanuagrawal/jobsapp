package com.dabblepoint.freelancingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.dabblepoint.freelancingapp.data.Repo
import com.dabblepoint.freelancingapp.models.Project
import com.dabblepoint.freelancingapp.models.RequestStatus
import com.dabblepoint.freelancingapp.models.User

class ProjectListViewModel : ViewModel() {

    init {
        print("hello")
    }

    private val repo: Repo = Repo()

    private val projectsFetchStatus: MutableLiveData<RequestStatus>
        get() = MutableLiveData()
    private val projects: MutableLiveData<ArrayList<Project>> = MutableLiveData()

    fun getProjectsCreatedBy(user: User): MutableLiveData<ArrayList<Project>>{
        if(projects.value == null){
            projects.value = arrayListOf()
            repo.getProjectsCreatedBy(user, projects, projectsFetchStatus)
        }
        return projects
    }
    // TODO: Implement the ViewModel
}
