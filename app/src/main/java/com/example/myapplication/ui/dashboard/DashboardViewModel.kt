package com.example.myapplication.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.myapplication.R

class DashboardViewModel : ViewModel() {

    var profileList = arrayListOf<Profile>(
        Profile(R.drawable.d, "Sihyun", "010-1111-1111"),
        Profile(R.drawable.d, "Sihyun", "010-2222-1111"),
        Profile(R.drawable.d, "Sihyun", "010-3333-1111"),
        Profile(null, "Sihyun", "010-4444-1111"),
        Profile(R.drawable.d, "Sihyun", "010-1111-1111"),
        Profile(R.drawable.d, "Sihyun", "010-2222-1111"),
        Profile(R.drawable.d, "Sihyun", "010-3333-1111"),
        Profile(null, "Sihyun", "010-4444-1111"),
        Profile(R.drawable.d, "Sihyun", "010-1111-1111"),
        Profile(R.drawable.d, "Sihyun", "010-2222-1111"),
        Profile(R.drawable.d, "Sihyun", "010-3333-1111"),
        Profile(null, "Sihyun", "010-4444-1111")
    )
}