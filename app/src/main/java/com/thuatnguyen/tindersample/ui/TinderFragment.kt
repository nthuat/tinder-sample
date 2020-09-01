package com.thuatnguyen.tindersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.thuatnguyen.tindersample.R
import com.thuatnguyen.tindersample.databinding.FragmentTinderBinding
import com.thuatnguyen.tindersample.model.Result
import com.thuatnguyen.tindersample.ui.adapter.UserCardAdapter
import com.thuatnguyen.tindersample.util.ReloadCallback
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TinderFragment : Fragment(), CardStackListener, ReloadCallback {
    private lateinit var userCardAdapter: UserCardAdapter
    private lateinit var manager: CardStackLayoutManager
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentTinderBinding
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
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_tinder,
            container,
            false
        )
        initUI()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.result = viewModel.userLiveData
        binding.lifecycleOwner = this
        binding.callback = this
        registerObservers()
        viewModel.getUsers(isFavoriteUserMode)
    }

    private fun initUI() {
        manager = CardStackLayoutManager(activity, this).apply {
            setStackFrom(StackFrom.None)
            setVisibleCount(3)
            setTranslationInterval(8.0f)
            setScaleInterval(0.95f)
            setSwipeThreshold(0.3f)
            setMaxDegree(20.0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(true)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }
        userCardAdapter = UserCardAdapter()
        binding.cardStackView.apply {
            layoutManager = manager
            adapter = userCardAdapter
            itemAnimator.apply {
                if (this is DefaultItemAnimator) {
                    supportsChangeAnimations = false
                }
            }
        }
    }

    override fun reload() {
        viewModel.getUsers(isFavoriteUserMode)
    }

    private fun registerObservers() {
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            if (it is Result.Success) {
                userCardAdapter.addUserList(it.data)
            }
        })
    }

    companion object {
        private const val ARG_FAVORITE_USER_MODE = "favorite_user_mode"

        @JvmStatic
        fun newInstance(isFavoriteUserMode: Boolean = false) =
            TinderFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_FAVORITE_USER_MODE, isFavoriteUserMode)
                }
            }
    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        if (!isFavoriteUserMode && direction == Direction.Right) {
            viewModel.saveFavoriteUser(manager.topPosition - 1)
            Toast.makeText(activity, "Saved as favorite user", Toast.LENGTH_LONG).show()
        }
        if (manager.topPosition == userCardAdapter.itemCount) {
            if (!isFavoriteUserMode) {
                viewModel.getNextUsers()
            } else {
                viewModel.resetUserData()
            }
        }
    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardRewound() {

    }
}