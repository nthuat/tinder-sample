package com.thuatnguyen.tindersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.thuatnguyen.tindersample.R

private const val ARG_FAVORITE_USER_MODE = "favorite_user_mode"

class TinderFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private var isFavoriteUserMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isFavoriteUserMode = it.getBoolean(ARG_FAVORITE_USER_MODE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tinder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        registerObservers()
        viewModel.getNextUsers()
    }

    private fun registerObservers() {
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it[0].toString(), Toast.LENGTH_LONG).show()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(isFavoriteUserMode: Boolean = false) =
            TinderFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_FAVORITE_USER_MODE, isFavoriteUserMode)
                }
            }
    }
}