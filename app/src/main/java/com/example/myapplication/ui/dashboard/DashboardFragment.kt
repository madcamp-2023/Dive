package com.example.myapplication.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var userList: ArrayList<Profile>
    private lateinit var searchBar: SearchView
    private lateinit var addBtn: Button
    private val binding get() = _binding!!
    private val startForResultAdd =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Intent의 데이터를 가져옵니다.
                val data: Intent? = result.data
                // 여기서 데이터를 처리합니다.
                (binding.listProfile.adapter as ProfileListAdapter).updateList(handleResultAdd(data))
            }
        }

    private val startForResultEdit =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Intent의 데이터를 가져옵니다.
                val data: Intent? = result.data
                // 여기서 데이터를 처리합니다.
                if (!::userList.isInitialized) {
                    // Initialize the userList property
                    userList = (ViewModelProvider(this).get(DashboardViewModel::class.java)).userList
                }
                if (data?.getBooleanExtra("del", false) == false) {
                    userList[data?.getIntExtra("idx", -1)!!] = Profile(
                        data?.getStringExtra("photo"),
                        data?.getStringExtra("name"),
                        data?.getStringExtra("phone"),
                    )
                } else {
                    data?.getIntExtra("idx", -1)?.let { userList.removeAt(it) }
                }
                (binding.listProfile.adapter as ProfileListAdapter).updateList(userList)
            }
        }

    fun handleResultAdd(data: Intent?): ArrayList<Profile> {
        // 결과 데이터를 사용하는 코드
        if (!::userList.isInitialized) {
            // Initialize the userList property
            userList = (ViewModelProvider(this).get(DashboardViewModel::class.java)).userList
        }
        userList.add(
            Profile(
                data?.getStringExtra("photo"),
                data?.getStringExtra("name"),
                data?.getStringExtra("phone")
            )
        )
        return userList
    }

    /*
    fun handleResultEdit(data: Intent?): ArrayList<Profile> {
        // 결과 데이터를 사용하는 코드
        if (!::userList.isInitialized) {
            // Initialize the userList property
            userList = (ViewModelProvider(this).get(DashboardViewModel::class.java)).userList
        }
        userList[data?.getIntExtra("idx", -1)!!] = Profile(
            data?.getStringExtra("photo"),
            data?.getStringExtra("name"),
            data?.getStringExtra("phone"),
        )
        return userList
    }
     */

    private fun setupRecyclerView(userList: ArrayList<Profile>) {
        val profileListAdapter = ProfileListAdapter(userList, requireContext(), startForResultEdit)
        binding.listProfile.adapter = profileListAdapter
        binding.listProfile.layoutManager = LinearLayoutManager(requireActivity())
        binding.listProfile.setHasFixedSize(true)
        setupSearchBar(profileListAdapter)
    }

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
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        setupRecyclerView(dashboardViewModel.userList)
        setupAddBtn()
        binding.root.post {
            val navBarHeight =
                requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).height
            binding.listProfile.setPadding(0, 0, 0, navBarHeight)
        }
        return binding.root
    }


/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        setupRecyclerView(dashboardViewModel.userList)
        setupAddBtn()
    }

 */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}