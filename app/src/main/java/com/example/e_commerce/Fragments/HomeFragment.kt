package com.example.e_commerce.Fragments

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentManager
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.Adapters.CategoriesAdapter
import com.example.e_commerce.R
import com.example.e_commerce.Retrofit.ApiService
import com.example.e_commerce.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myChildFragmentManager: FragmentManager

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val switchTheme: Switch = view.findViewById(R.id.switchTheme)
        recyclerView = view.findViewById(R.id.category_rv)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        myChildFragmentManager = requireActivity().supportFragmentManager // Update this line

//        CategoryCall()

        // Set initial state based on current mode
        switchTheme.isChecked = isNightModeEnabled()

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Enable dark mode
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Enable light mode
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CategoryCall()
    }

    private fun CategoryCall() {
        val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)
        val call = apiService.getCategories()

        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val categories = response.body()
                    if (categories != null) {
                        val adapter = CategoriesAdapter(categories, myChildFragmentManager)
                        recyclerView.adapter = adapter
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
            }
        })
    }

    private fun setNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate() // Recreate the activity to apply the new theme
    }

    private fun isNightModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

}

