package com.example.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home.databinding.FragmentHomeBinding

//Intentionally does not derive from BaseFragment
//Sole role of HomeFragment is ATM being a target destination
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return FragmentHomeBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.homeText?.text = arguments?.getString(TEXT_ARG)
    }

    companion object {
        private const val TEXT_ARG = "TEXT_ARG"

        fun bundle(text: String): Bundle {
            return Bundle().apply {
                putString(TEXT_ARG, text)
            }
        }
    }
}
