package com.example.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.home.databinding.FragmentHomeBinding

// Intentionally does not derive from BaseFragment
// Sole role of HomeFragment is ATM being a target destination
class HomeFragment : Fragment() {

    private val args by navArgs<HomeFragmentArgs>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeText.text = args.helloText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
