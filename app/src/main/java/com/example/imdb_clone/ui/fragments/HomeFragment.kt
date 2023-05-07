package com.example.imdb_clone.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdb_clone.adapters.MovListAdapters
import com.example.imdb_clone.adapters.Share
import com.example.imdb_clone.databinding.FragmentHomeBinding
import com.example.imdb_clone.models.Movie

import com.example.imdb_clone.viewmodel.MainViewModel


class HomeFragment : Fragment(), Share {
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapters: MovListAdapters
    private var isLoading = false
    private var isRE = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // setting up the recyclerview
        binding.movListRecyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        binding.movListRecyclerView.layoutManager = layoutManager


        // observing the data fetched from the database and setting up to the recyclerview at first
        mainViewModel.movLiveDataDB.observe(viewLifecycleOwner) {
            isLoading = true
            adapters = MovListAdapters(requireContext(), it.Movie_List,this)
            binding.movListRecyclerView.adapter = adapters
            binding.spinKitViewHome.visibility = GONE
            isLoading = false
        }


        // adding the onscroll listner to check the last visited item to fetched new list from the remote api
        binding.movListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val totalItem = adapters.itemCount
                val firstItemCount = layoutManager.findFirstCompletelyVisibleItemPosition()

                // if remote call is false means no remote call has been done so calling and loading the remote call
                if (isRE == false && isLoading == false) {
                    if (visibleItemCount + firstItemCount >= totalItem) {
                        isLoading = true

                        var list: List<Movie>
                        list = listOf()
                        mainViewModel.movLiveDataRE.observe(viewLifecycleOwner)
                        {
                            list = it.Movie_List.toList()
                        }
                        binding.spinKitViewHome.visibility = VISIBLE
                        // delaying for the 0.5 sec to give the observe call to get executed completely because it is running async.
                        Handler().postDelayed({
                            // adding the list by passing in the update list function for the adapter and notifying the data set change
                            adapters.updateDataList(list)
                            adapters.notifyDataSetChanged()
                            binding.spinKitViewHome.visibility = GONE
                        }, 500)

                        isRE = true
                        isLoading = false
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        return binding.root
    }


    override fun onResume() {
        isRE = false
        super.onResume()
    }

    // share movie through intent implementation of interface created in the adapter class
    override fun shareMov(item: Movie) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check this Movie Named : ${item.Title} Starring : ${item.Cast} and here is the trailer ${item.YouTube_Trailer}")
        intent.type = "text/plain"
        startActivity(intent)
    }


}