package com.example.task5.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task5.R
import com.example.task5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CatListFragment.create())
                .commitNow()
        }
    }
}
