package com.jchavez.wheelycoolapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.jchavez.wheelycoolapp.R
import com.jchavez.wheelycoolapp.models.WheelOption
import kotlinx.android.synthetic.main.fragment_spin_wheel.view.*

private const val ARG_ITEMS = "items"

/**
 * A simple [Fragment] subclass.
 * Use the [SpinWheelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpinWheelFragment : Fragment() {
    private var items = arrayListOf<WheelOption>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            items = it.getParcelableArrayList(ARG_ITEMS)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spin_wheel, container, false)
        view.actionButton.setOnClickListener {
            view.wheelView.rotateWheel()
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        activity?.title = "Spin"
        view.wheelView.items = items
        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.clear()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param items Array list of items.
         * @return A new instance of fragment SpinWheelFragment.
         */

        @JvmStatic
        fun newInstance(items: ArrayList<WheelOption>) =
                SpinWheelFragment().apply {
                    arguments = Bundle().apply {
                        putParcelableArrayList(ARG_ITEMS, items)
                    }
                }
    }
}
