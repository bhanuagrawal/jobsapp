package com.dabblepoint.freelancingapp

import androidx.navigation.NavDirections

interface FragmentInteractionListner{
    fun navigateTo(directions: NavDirections)
    fun navigateTo(resID: Int)
}