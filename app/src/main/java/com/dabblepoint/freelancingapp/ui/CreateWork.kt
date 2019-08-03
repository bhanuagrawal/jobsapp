package com.dabblepoint.freelancingapp.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.dabblepoint.freelancingapp.FragmentInteractionListner
import com.dabblepoint.freelancingapp.viewmodels.CreateWorkViewModel
import com.dabblepoint.freelancingapp.R
import com.dabblepoint.freelancingapp.models.RequestStatus
import com.dabblepoint.freelancingapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.create_work_fragment.*

class CreateWork : Fragment() {

    companion object {
        fun newInstance() = CreateWork()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: CreateWorkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_work_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateWorkViewModel::class.java)
        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        post.setOnClickListener {

            val skills = arrayListOf<String>()
            if(skill1.isChecked){
                skills.add(skill1.text.toString())
            }
            if(skill2.isChecked){
                skills.add(skill2.text.toString())
            }
            if(skill3.isChecked){
                skills.add(skill3.text.toString())
            }
            if(skill4.isChecked){
                skills.add(skill4.text.toString())
            }
            viewModel.createProject(title.text.toString(), skills, mainViewModel.user.value )
        }


        viewModel.projectAddStatus.observe(this, Observer {

            when(it.status){

                RequestStatus.Status.SUCCESS -> {

                    viewModel.projectAddStatus.postValue(RequestStatus(RequestStatus.Status.NOT_REQUESTED))

                    post.isClickable = true
                    Toast.makeText(context, "Project Added", Toast.LENGTH_LONG).show()
                    (activity as FragmentInteractionListner).navigateTo(R.id.works)
                }

                RequestStatus.Status.FAILURE -> {
                    viewModel.projectAddStatus.postValue(RequestStatus(RequestStatus.Status.NOT_REQUESTED))
                    post.isClickable = true
                    Toast.makeText(context, "Project not added", Toast.LENGTH_LONG).show()
                }

                RequestStatus.Status.REQUESTED -> post.isClickable = false
            }
        })
    }

}
