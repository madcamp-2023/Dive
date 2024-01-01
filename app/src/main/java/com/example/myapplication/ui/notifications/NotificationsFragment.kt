package com.example.myapplication.ui.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentNotificationsBinding
import com.squareup.picasso.Picasso

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = BlogPagerAdapter(notificationsViewModel.BlogList)
//
//        val techItemBackground: ImageView = binding.techItemBackground
//        notificationsViewModel.currentIndex.observe(viewLifecycleOwner) { i ->
//            Picasso.get().load(notificationsViewModel.BlogList[i].url).fit().centerCrop()
//                .into(techItemBackground)
//        }
//
//        val techItemImage: ImageView = binding.techItemImage
//        notificationsViewModel.currentIndex.observe(viewLifecycleOwner) { i ->
//            Picasso.get().load(notificationsViewModel.BlogList[i].url).fit().centerInside()
//                .into(techItemImage)
//        }
//
//        val titleTextView: TextView = binding.titleText
//        val contentTextView: TextView = binding.contentText
//        notificationsViewModel.currentIndex.observe(viewLifecycleOwner) { i ->
//            // 제목에 대한 글자 수 제한
//            val title = notificationsViewModel.BlogList[i].title
//
//            // 내용에 대한 글자 수 제한
//            val content = if (notificationsViewModel.BlogList[i].content.length > 40) {
//                notificationsViewModel.BlogList[i].content.substring(0, 40) + "..."
//            } else {
//                notificationsViewModel.BlogList[i].content
//            }
//            titleTextView.text = title
//            contentTextView.text = content
//        }
//
//        binding.button1.setOnClickListener {
//            notificationsViewModel.moveToPrevious()
//        }
//
//        binding.button2.setOnClickListener {
//            notificationsViewModel.moveToNext()
//        }
//
//        binding.diveButton.setOnClickListener {
//            notificationsViewModel.getCurrentPageUrl()?.let {
//                url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                startActivity(intent)
//            }
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}