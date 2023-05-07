package com.example.imdb_clone.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdb_clone.R
import com.example.imdb_clone.adapters.MovListAdapters
import com.example.imdb_clone.adapters.Share
import com.example.imdb_clone.databinding.FragmentFavouriteListBinding
import com.example.imdb_clone.models.Movie
import com.example.imdb_clone.models.Movies
import com.example.imdb_clone.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavouriteListFragment : Fragment(), Share {

    private lateinit var binding: FragmentFavouriteListBinding
    private lateinit var watchList: ArrayList<String>
    private lateinit var watchlistItem: ArrayList<Movie>
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteListBinding.inflate(layoutInflater)

        // intializing the arraylist to filter out the title stored in the shared prefs
        watchlistItem = ArrayList()
        watchList = ArrayList()

        //read the data from the shared prefs
        readData()

        // getting the list from the ViewModel
        val databaseList = mainViewModel.movLiveDataDB.value
        val remoteList = mainViewModel.movLiveDataRE.value

        // searching title match from the database list
        for (watchData in watchList) {
            for (item in databaseList!!.Movie_List) {
                if (watchData == item.Title) {
                    watchlistItem.add(item)
                }
            }
        }
        // searching the title match from the remote api list
        for (watchData in watchList) {
            for (item in remoteList!!.Movie_List) {
                if (watchData == item.Title) {
                    watchlistItem.add(item)
                }
            }
        }

        // filtering out the duplicates data from the watchlist to load
        val watchListToLoad = watchlistItem.distinct()

        // setting up the recyclerview
        binding.favProductListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favProductListRecyclerView.adapter =
            MovListAdapters(requireContext(), watchListToLoad, this)

        return binding.root
    }

    // simple logic to read the data from the shared prefs
    private fun readData() {
        val sharedPreferences =
            requireContext().getSharedPreferences(getString(R.string.prefs_key), Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(getString(R.string.prefs_key), ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        watchList = gson.fromJson(json, type)
    }

    // share movie through intent implementation of interface created in the adapter class
    override fun shareMov(item: Movie) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Hey Check this Movie Named : ${item.Title} Starring : ${item.Cast} and here is the trailer ${item.YouTube_Trailer}"
        )
        intent.type = "text/plain"
        startActivity(intent)
    }

}