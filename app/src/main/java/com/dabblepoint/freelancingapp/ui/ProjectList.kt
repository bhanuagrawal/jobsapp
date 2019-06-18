package com.dabblepoint.freelancingapp.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dabblepoint.freelancingapp.R
import com.dabblepoint.freelancingapp.viewmodels.MainViewModel
import com.dabblepoint.freelancingapp.viewmodels.ProjectListViewModel


class ProjectList : Fragment() {

    companion object {
        fun newInstance() = ProjectList()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: ProjectListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.works_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        viewModel = ViewModelProviders.of(this).get(ProjectListViewModel::class.java)
        mainViewModel.user.value?.let {

            viewModel.getProjectsCreatedBy(it).observe(this, Observer { projects ->

                Log.d("projects", projects.toString())

                projects?.forEach { project ->

                    project.requests.observe(this, Observer { workRequests ->

                        Log.d("work requests", project.title + " " + workRequests.toString())

                    })
                }

            })



        }
    }

}
