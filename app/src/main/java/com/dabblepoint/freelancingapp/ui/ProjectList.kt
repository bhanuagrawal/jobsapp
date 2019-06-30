package com.dabblepoint.freelancingapp.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dabblepoint.freelancingapp.R
import com.dabblepoint.freelancingapp.models.Project
import com.dabblepoint.freelancingapp.ui.adapters.ItemAdapter
import com.dabblepoint.freelancingapp.ui.adapters.ProjectsAdapter
import com.dabblepoint.freelancingapp.viewmodels.MainViewModel
import com.dabblepoint.freelancingapp.viewmodels.ProjectListViewModel
import kotlinx.android.synthetic.main.works_fragment.*


class ProjectList : Fragment() {

    companion object {
        fun newInstance() = ProjectList()
    }

    private lateinit var adapter: ItemAdapter<Project>
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: ProjectListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ProjectsAdapter(R.layout.project, arrayListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.works_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        projects.layoutManager = LinearLayoutManager(context)
        projects.adapter = adapter


        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }


        viewModel = ViewModelProviders.of(this).get(ProjectListViewModel::class.java)
        mainViewModel.user.value?.let {

            viewModel.getProjectsCreatedBy(it).observe(this, Observer { projects ->

                projects?.let {
                    Log.d("projects", projects.toString())

                    adapter.onDataChange(projects)

                    projects.forEach { project ->

                        project.requests.observe(this, Observer { workRequests ->

                            Log.d("work requests", project.title + " " + workRequests.toString())

                        })
                    }
                }



            })



        }
    }



}
