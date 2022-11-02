package com.example.androidlab_22_23.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidlab_22_23.R
import com.example.androidlab_22_23.databinding.FragmentServicesBinding

class ServicesFragment : Fragment(R.layout.fragment_services) {

    private var binding: FragmentServicesBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentServicesBinding.bind(view)

        binding?.run {
            btnProfile.setOnClickListener {
                findNavController().navigate(R.id.action_services_fragment_to_profile_fragment)
            }
            btnFriends.setOnClickListener {
                findNavController().navigate(R.id.action_services_fragment_to_friends_fragment)
            }
            btnMusic.setOnClickListener {
                findNavController().navigate(R.id.action_services_fragment_to_music_fragment)
            }
            btnShop.setOnClickListener {
                findNavController().navigate(R.id.action_services_fragment_to_shop_fragment)
            }
        }
    }
}