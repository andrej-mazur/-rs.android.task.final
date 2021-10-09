package com.example.task5.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.task5.R

class MainActivity : AppCompatActivity() {

    private var _navController: NavController? = null

    private val navController get() = requireNotNull(_navController)

    private var _appBarConfiguration: AppBarConfiguration? = null

    private val appBarConfiguration get() = requireNotNull(_appBarConfiguration)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initNavigation() {
        _navController = (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment).navController
        _appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
