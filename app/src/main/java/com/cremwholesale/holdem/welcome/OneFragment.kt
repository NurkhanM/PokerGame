package com.cremwholesale.holdem.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cremwholesale.holdem.R
import com.cremwholesale.holdem.databinding.FragmentFirstBinding
import com.cremwholesale.holdem.databinding.FragmentOneBinding

class OneFragment : Fragment() {
    private var _binding: FragmentOneBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextMain.setOnClickListener {
            findNavController().navigate(R.id.action_oneFragment_to_twoFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}