package com.jchavez.wheelycoolapp.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jchavez.wheelycoolapp.R
import com.jchavez.wheelycoolapp.adapters.WheelOptionRecyclerViewAdapter
import com.jchavez.wheelycoolapp.models.WheelOption
import kotlinx.android.synthetic.main.fragment_spin_wheel.view.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [WheelOptionFragment.OnListFragmentInteractionListener] interface.
 */
class WheelOptionFragment : Fragment() {
    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null
    private var items = arrayListOf<WheelOption>()
    private var recyclerAdapter: WheelOptionRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            items = it.getParcelableArrayList(ARG_ITEMS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_option_list, container, false)
        val recyclerView = view.findViewById<View>(R.id.list)
        if (recyclerView is RecyclerView) {
            recyclerAdapter = WheelOptionRecyclerViewAdapter(items, listener)
            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = recyclerAdapter
            }
        }

        view.actionButton.setOnClickListener{
            if (items.count() > 1) {
                listener?.onContinueFragmentInteraction(items)
            } else {
                Toast.makeText(activity, getString(R.string.please_add_items), Toast.LENGTH_SHORT).show()
            }
        }

        activity?.title = getString(R.string.setup)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun addWheelOption(wheelOption: WheelOption) {
        items.add(wheelOption)
        recyclerAdapter?.notifyItemInserted(items.count())
    }

    fun removeWheelOption(wheelOption: WheelOption) {
        val filteredItems = items.filter { it.name == wheelOption.name }
        items.removeAll(filteredItems)
        recyclerAdapter?.notifyDataSetChanged()
    }

    fun numberOfItems(): Int {
        return items.count()
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: WheelOption)
        fun onContinueFragmentInteraction(items: ArrayList<WheelOption>)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        const val ARG_ITEMS = "items"
        @JvmStatic
        fun newInstance(columnCount: Int, items: ArrayList<WheelOption>) =
                WheelOptionFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                        putParcelableArrayList(ARG_ITEMS, items)
                    }
                }
    }
}
