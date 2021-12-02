package com.example.watchlist2.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.watchlist2.R
import com.example.watchlist2.databinding.ActivityMainBinding
import com.example.watchlist2.util.PreferencesWrapper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _navController: NavController? = null

    private val navController get() = requireNotNull(_navController)

    private var _appBarConfiguration: AppBarConfiguration? = null

    private val appBarConfiguration get() = requireNotNull(_appBarConfiguration)

    private lateinit var binding: ActivityMainBinding

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface SharedPreferencesWrapperFactory {
        fun getSharedPreferencesWrapper(): PreferencesWrapper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initTheme()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        initNavigation()
    }

    private fun initTheme() {
        val sharedPreferencesWrapperFactory = EntryPointAccessors.fromApplication(this, SharedPreferencesWrapperFactory::class.java)
        val sharedPreferencesWrapper = sharedPreferencesWrapperFactory.getSharedPreferencesWrapper()
        sharedPreferencesWrapper.getPrefDarkMode().let {
            when (it) {
                "ON" -> setTheme(R.style.AppThemeDark)
                else -> setTheme(R.style.AppTheme)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController(R.id.nav_host).navigate(R.id.action_to_PreferencesFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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