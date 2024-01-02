package com.example.myapplication.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var searchBar: SearchView
    private lateinit var addBtn: Button
    private val binding get() = _binding!!


    private fun setupSearchBar(adapter: ProfileListAdapter) {
        searchBar = binding.searchBar
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return true
            }
        })
    }

    private fun setupAddBtn() {
        addBtn = binding.btnAdd
        addBtn.setOnClickListener {
            val intent = Intent(activity, ProfileAddActivity::class.java)

            startForResultAdd.launch(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
         dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        // setup Recycler View
        val profileListAdapter = ProfileListAdapter(ArrayList(emptyList<Profile>()), requireContext(), startForResultDetail)
        binding.listProfile.adapter = profileListAdapter
        binding.listProfile.layoutManager = LinearLayoutManager(requireActivity())
        binding.listProfile.setHasFixedSize(true)

        setupSearchBar(profileListAdapter)
        setupAddBtn()

        // Observe the LiveData and update the adapter when data changes
        dashboardViewModel.profileList.observe(viewLifecycleOwner, Observer {
            profileListAdapter.updateList(it)
        })

        // Fetch initial data
        dashboardViewModel.fetchProfileList()

        // setPadding on Navigation Bar position
        binding.root.post {
            val navBarHeight =
                requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).height
            binding.listProfile.setPadding(0, 0, 0, navBarHeight)
        }

        return binding.root
    }

    private val startForResultAdd =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                handleResultAdd(data)
            }
        }

    private val startForResultDetail =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data?.getBooleanExtra("del", false) == false) {
                    data?.getIntExtra("idx", -1)?.let {
                        dashboardViewModel.updateProfileByIndex(
                            it, Profile(
                                data?.getStringExtra("photo"),
                                data?.getStringExtra("name"),
                                data?.getStringExtra("phone"),
                            ))
                    }
                } else {
                    data?.getIntExtra("idx", -1)?.let { dashboardViewModel.deleteProfileByIndex(it) }
                }
            }
        }

    fun handleResultAdd(data: Intent?) {
        dashboardViewModel.addProfile(
            Profile(
                data?.getStringExtra("photo"),
                data?.getStringExtra("name"),
                data?.getStringExtra("phone")
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}