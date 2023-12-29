package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //    private val profileList = ArrayList<Profile>()
    private val profileList = arrayListOf<Profile>(
        Profile("Sihyun", "010-1111-1111"),
        Profile("Sihyun", "010-2222-1111"),
        Profile("Sihyun", "010-3333-1111"),
        Profile("Sihyun", "010-4444-1111")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.listProfile.adapter = ProfileListAdapter(profileList)
        binding.listProfile.layoutManager = LinearLayoutManager(requireActivity())
        binding.listProfile.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listProfile.adapter = ProfileListAdapter(profileList)
        binding.listProfile.layoutManager = LinearLayoutManager(requireActivity())
        binding.listProfile.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}