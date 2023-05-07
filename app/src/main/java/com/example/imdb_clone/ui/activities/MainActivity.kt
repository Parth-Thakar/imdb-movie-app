package com.example.imdb_clone.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.imdb_clone.MovApplication
import com.example.imdb_clone.R
import com.example.imdb_clone.databinding.ActivityMainBinding
import com.example.imdb_clone.utils.NetworkUtils
import com.example.imdb_clone.viewmodel.MainViewModel
import com.example.imdb_clone.viewmodel.MainViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setting up the navigation hosted fragment in the fragment space created in layout file
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentSpace)
        val navController = navHostFragment!!.findNavController()
        val popupmenu = PopupMenu(this, null)
        popupmenu.inflate(R.menu.bottom_nav_menu)
        binding.bottomBar.setupWithNavController(popupmenu.menu, navController)

        // Field Dependency Injection for every inject annotation
        (application as MovApplication).applicationComponent.inject(this)

        //creating the instance of the mainViewModel to test the livedata using observables.
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)


        if (!NetworkUtils.isInternetAvailable(this)) {
            // Create the object of AlertDialog Builder class
            val builder = AlertDialog.Builder(this)
            // Set the message show for the Alert time
            builder.setMessage("Please Turn on your Internet")
            // Set Alert Title
            builder.setTitle("Your Internet is OFF !")
            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false)
            // Create the Alert dialog
            val alertDialog = builder.create()
            // Show the Alert Dialog box
            alertDialog.show()
        }


    }
}