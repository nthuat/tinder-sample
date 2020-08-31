package com.thuatnguyen.tindersample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thuatnguyen.tindersample.R
import com.thuatnguyen.tindersample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(binding.container.id, TinderFragment.newInstance())
            .commit()
        binding.btnFavoriteUsers.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, TinderFragment.newInstance(true))
                .commit()
        }

    }
}