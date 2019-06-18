package com.dabblepoint.freelancingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.dabblepoint.freelancingapp.models.User

class MainViewModel : ViewModel() {

    val user: MutableLiveData<User> = MutableLiveData()
    // TODO: Implement the ViewModel
}
