package com.example.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

//Intentionally does not derive from BaseFragment
//Sole role of HomeFragment is ATM being a target destination
class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.homeText).text = arguments?.getString(TEXT_ARG)
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
