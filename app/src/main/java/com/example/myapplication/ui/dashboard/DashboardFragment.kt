package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // dummy data
    private val profileList = arrayListOf<Profile>(
        Profile(R.drawable.a, "Sihyun", "010-1111-1111"),
        Profile(R.drawable.b, "Sihyun", "010-2222-1111"),
        Profile(0, "Sihyun", "010-3333-1111"),
        Profile(0, "Sihyun", "010-4444-1111")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // inflate the layout
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            val name = bundle.getString("name")
            val phone = bundle.getString("phone")
            val img = bundle.getString("image")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profileListAdapter = ProfileListAdapter(profileList, requireContext())
        binding.listProfile.adapter = profileListAdapter
        binding.listProfile.layoutManager = LinearLayoutManager(requireActivity())
        binding.listProfile.setHasFixedSize(true)

        profileListAdapter.setOnItemClickListener(object : ProfileListAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: Profile, pos: Int) {
                Log.i(data.pf_name, "onClick!!")
            }
        })
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