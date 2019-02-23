package com.jchavez.wheelycoolapp.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jchavez.wheelycoolapp.R
import kotlinx.android.synthetic.main.fragment_input.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [InputFragment.OnFragmentDialogInteractionListener] interface
 * to handle interaction events.
 * Use the [InputFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class InputFragment : DialogFragment() {
    private var listener: OnFragmentDialogInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)
        view.actionButton.setOnClickListener {
            if (view.editText.text.toString().isNotBlank()) {
                listener?.onFragmentDialogInteraction(view.editText.text.toString())
                dismiss()
            }
        }

        view.setOnClickListener {
            dismiss()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentDialogInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentDialogInteractionListener {
        fun onFragmentDialogInteraction(itemName: String)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment InputFragment.
         */
        @JvmStatic
        fun newInstance() =
                InputFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
