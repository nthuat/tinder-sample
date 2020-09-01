package com.thuatnguyen.tindersample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thuatnguyen.tindersample.R
import com.thuatnguyen.tindersample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var lastPosition = HOME_POSITION
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        addHomeFragment()
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(TinderFragment.newInstance(), HOME_FRAGMENT, HOME_POSITION)
                    true
                }
                R.id.navigation_favorite -> {
                    replaceFragment(
                        TinderFragment.newInstance(true),
                        FAVORITE_FRAGMENT, FAVORITE_POSITION
                    )
                    true
                }
                else -> false
            }
        }
    }

    private fun addHomeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, TinderFragment.newInstance(), HOME_FRAGMENT)
            .commit()
    }

    private fun replaceFragment(newFragment: Fragment, tag: String, newPosition: Int) {
        val (enter, exit) = when {
            newPosition < lastPosition -> android.R.anim.slide_in_left to android.R.anim.slide_out_right
            newPosition > lastPosition -> R.anim.slide_in_right to R.anim.slide_out_left
            else -> return
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(enter, exit)
            .replace(R.id.container, newFragment, tag)
            .commit()
        lastPosition = newPosition
    }

    companion object {
        private const val HOME_FRAGMENT = "HOME_FRAGMENT"
        private const val FAVORITE_FRAGMENT: String = "FAVORITE_FRAGMENT"
        private const val HOME_POSITION = 1
        private const val FAVORITE_POSITION = 2
    }
}