package com.dabblepoint.freelancingapp.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dabblepoint.freelancingapp.FragmentInteractionListner
import com.dabblepoint.freelancingapp.R
import com.dabblepoint.freelancingapp.viewmodels.UserDetailViewModel
import com.dabblepoint.freelancingapp.models.RequestStatus
import com.dabblepoint.freelancingapp.models.User
import com.dabblepoint.freelancingapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.user_detail_fragment.*

class UserDetailFragment : Fragment() {

    companion object {
        fun newInstance() = UserDetailFragment()
    }

    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var viewModel: UserDetailViewModel
    private lateinit var mainViewModel: MainViewModel
    private var listener: FragmentInteractionListner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayOf(getString(R.string.employee), getString(
            R.string.employer
        )))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_detail_fragment, container, false)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInteractionListner) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement FragmentInteractionListner")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserDetailViewModel::class.java)
        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        viewModel.userAddStatus.observe(this, Observer {

            when(it.status){

                RequestStatus.Status.SUCCESS -> {

                    viewModel.userAddStatus.postValue(RequestStatus(RequestStatus.Status.NOT_REQUESTED))
                    submit.isClickable = true
                    Toast.makeText(context, "User Added", Toast.LENGTH_LONG).show()

                    it.data?.let {user ->
                        if(user is User){
                            mainViewModel.user.postValue(user)
                            when(user.role){
                                getString(R.string.employee) -> listener?.navigateTo(R.id.works)
                                getString(R.string.employer) -> listener?.navigateTo(R.id.works)
                            }
                        }

                    }
                }

                RequestStatus.Status.FAILURE -> {
                    viewModel.userAddStatus.postValue(RequestStatus(RequestStatus.Status.NOT_REQUESTED))
                    submit.isClickable = true
                    Toast.makeText(context, "User not added", Toast.LENGTH_LONG).show()
                }

                RequestStatus.Status.REQUESTED -> submit.isClickable = false
            }
        })

        role.adapter = arrayAdapter
        submit.setOnClickListener{
            viewModel.createUser(name.text.toString(),
                mob.text.toString(),
                role.selectedItem.toString())
        }
        // TODO: Use the ViewModel

    }

}

private fun <T> MutableLiveData<T>.observe(userDetailFragment: UserDetailFragment, function: () -> Unit) {

}
