package com.example.imdb_clone.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdb_clone.R
import com.example.imdb_clone.databinding.MovieListItemBinding
import com.example.imdb_clone.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Adapter class for the recyclerview used in the application.
class MovListAdapters(val context: Context, var list: List<Movie>, private val listner: Share) :
    RecyclerView.Adapter<MovListAdapters.MovListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovListViewHolder {
        return MovListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)
        )
    }

    //setting up the each item's according to the list in the individual item's field
    override fun onBindViewHolder(holder: MovListViewHolder, position: Int) {
        var item = list[position]

        holder.binding.movieTitleTV.text = "Title: " + item.Title
        holder.binding.castTextView.text = "Cast: " + item.Cast
        holder.binding.runtimeTextView.text = "Runtime: " + item.Runtime
        holder.binding.yearLaunchTextView.text = "Year: " + item.Year

        Glide.with(context)
            .load(item.Movie_Poster)
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.movieImageView)

        // reading out the data to check the the current movie item is already been added in the favlist or not
        readData()
        // if yes setting up the fav button to red
        isCheck = if (watchList!!.contains(item.Title)) {
            holder.binding.favButton.setColorFilter(ContextCompat.getColor(context, R.color.red))
            true
        } else {
            holder.binding.favButton.setColorFilter(ContextCompat.getColor(context, R.color.white))
            false
        }
        // if fav button clicked adding the title of the movie in the sharedprefrence
        holder.binding.favButton.setOnClickListener {
            isCheck = if (!isCheck) {
                if (!watchList!!.contains(item.Title)) {
                    watchList!!.add(item.Title)
                }
                //storing the data
                storeData()
                holder.binding.favButton.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.red
                    )
                )
                true
            } else {
                holder.binding.favButton.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                // if clicked again on the same favbutton deleting title stored in the shared prefrences
                watchList!!.remove(item.Title)
                storeData()
                false
            }
        }
        // listner logic to share the movie item using intent
        holder.binding.shareButton.setOnClickListener {
            listner.shareMov(item)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    // on adding the new list concatenating the new passed list in the existing list in the adapter
    fun updateDataList(dataItem: List<Movie>) {
        list = list + dataItem
    }

    inner class MovListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = MovieListItemBinding.bind(view)
    }


    var watchList: ArrayList<String>? = null
    var isCheck = false

    // simple logic to store the title of the selected movie in the shared prefrence
    private fun storeData() {
        val sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.prefs_key), Context.MODE_PRIVATE)
        val gson = Gson()
        val editor = sharedPreferences.edit()
        val json = gson.toJson(watchList)
        editor.putString(context.getString(R.string.prefs_key), json)
        editor.apply()
    }

    // simple logic to read the data from the shared prefs and store in the watchlist which is favlist.
    private fun readData() {
        val sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.prefs_key), Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(context.getString(R.string.prefs_key), ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        watchList = gson.fromJson(json, type)
    }
}

// listner interface for the share button
interface Share {
    fun shareMov(item: Movie)
}
