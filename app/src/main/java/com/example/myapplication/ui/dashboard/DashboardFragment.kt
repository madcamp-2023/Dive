package com.example.myapplication.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

class DashboardFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    fun readJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun parseJson(jsonString: String): List<Profile>? {
        return try {
            val gson = Gson()
            val profileListType = object : TypeToken<List<Profile>>() {}.type
            gson.fromJson(jsonString, profileListType)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val jsonString = readJsonFromAssets(requireContext(), "data.json")
        if (jsonString != null) {
            val userList = parseJson(jsonString)
            if (userList != null) {
                val profileListAdapter = ProfileListAdapter(userList, requireContext())
                binding.listProfile.adapter = profileListAdapter
                binding.listProfile.layoutManager = LinearLayoutManager(requireActivity())
                binding.listProfile.setHasFixedSize(true)


                profileListAdapter.setOnItemClickListener(object : ProfileListAdapter.OnItemClickListener {
                    override fun onItemClick(v: View, data: Profile, pos: Int) {
                        Log.i(data.name, "onClick!!")
                    }
                })
            } else {
                // TODO: handle parsing error
            }
        } else {
            // TODO: handle file reading error
        }

//        profileListAdapter.setOnItemClickListener(object : ProfileListAdapter.OnItemClickListener {
//            override fun onItemClick(v: View, data: Profile, pos: Int) {
//                Log.i(data.name, "onClick!!")
//            }
//        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.profile -> {
                Toast.makeText(requireContext(), "Button Clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}