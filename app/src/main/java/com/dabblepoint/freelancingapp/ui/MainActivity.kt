package com.dabblepoint.freelancingapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.dabblepoint.freelancingapp.FragmentInteractionListner
import com.dabblepoint.freelancingapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentInteractionListner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun navigateTo(direction: NavDirections) {
        nav_host_fragment.findNavController().navigate(direction)
    }

    override fun navigateTo(resID: Int) {
        nav_host_fragment.findNavController().navigate(resID)
    }

}
