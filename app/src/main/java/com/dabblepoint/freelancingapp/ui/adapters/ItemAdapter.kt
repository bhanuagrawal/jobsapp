package com.dabblepoint.freelancingapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dabblepoint.freelancingapp.BR


abstract class ItemAdapter<T>(val data: ArrayList<T>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ViewDataBinding = DataBindingUtil.inflate(
            layoutInflater, viewType, parent, false
        )
        return ItemViewHolder<T>(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder<T>).bind(data[position])
    }


    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    abstract fun getLayoutIdForPosition(position: Int): Int


    fun onDataChange(newData: ArrayList<T>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }


    inner class ItemViewHolder<T>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: T){

            binding.setVariable(BR.data, data)
            binding.executePendingBindings()
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }
}