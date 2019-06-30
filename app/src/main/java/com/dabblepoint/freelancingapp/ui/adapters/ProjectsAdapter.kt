package com.dabblepoint.freelancingapp.ui.adapters

import com.dabblepoint.freelancingapp.models.Project

class ProjectsAdapter<T>(private val layoutId: Int, data: ArrayList<T>): ItemAdapter<T>(data) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }

}