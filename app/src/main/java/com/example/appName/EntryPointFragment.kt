package com.example.appName

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class EntryPointFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = null

    override fun onStart() {
        super.onStart()

        (requireActivity() as MainActivity)
            .navController
            .navigate(
                EntryPointFragmentDirections.actionEntryPointFragmentToUserNavGraph()
            )
    }
}
