package com.example.imdb_clone.ui.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imdb_clone.R
import com.example.imdb_clone.databinding.FragmentUserProfileBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var watchList: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        // reading the data from the shared prefrences
        readData()


        binding.favListTV.text = watchList.size.toString()

        // sharing the Profile Image drawable aka photo using the implicit intent
        binding.shareProfileButton.setOnClickListener {
            val drawable: BitmapDrawable = binding.userProfileImage.drawable as BitmapDrawable
            val bitmap: Bitmap = drawable.bitmap

            val bitmapPath: String = MediaStore.Images.Media.insertImage(
                activity?.applicationContext?.contentResolver, bitmap, getString(R.string.drawable_image_key), null
            )

            val uri: Uri = Uri.parse(bitmapPath)

            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("image/png")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "User name : ${binding.userNameTV.text} \n  User Email : ${binding.emailTV.text}"
            )
            startActivity(Intent.createChooser(intent, getString(R.string.share_text_add)))
        }


        return binding.root
    }

    // simple logic to read the data from the shared preffs
    private fun readData() {
        val sharedPreferences =
            requireContext().getSharedPreferences(getString(R.string.prefs_key), Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(getString(R.string.prefs_key), ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        watchList = gson.fromJson(json, type)
    }

}