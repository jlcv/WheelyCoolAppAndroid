package com.jchavez.wheelycoolapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.jchavez.wheelycoolapp.R
import com.jchavez.wheelycoolapp.fragments.WheelOptionFragment.OnListFragmentInteractionListener
import com.jchavez.wheelycoolapp.models.WheelOption
import kotlinx.android.synthetic.main.fragment_option.view.*

class WheelOptionRecyclerViewAdapter(
        private val mValues: List<WheelOption>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<WheelOptionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_option, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name
        holder.deleteButton.setOnClickListener {
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content
        val deleteButton: ImageButton = mView.deleteButton

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
