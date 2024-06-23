package com.cremwholesale.holdem.tips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cremwholesale.holdem.R
import com.cremwholesale.holdem.databinding.FragmentFirstBinding
import com.cremwholesale.holdem.databinding.FragmentOneBinding
import com.cremwholesale.holdem.databinding.FragmentTiipsOneBinding

class TipsOneFragment : Fragment() {
    private var _binding: FragmentTiipsOneBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTiipsOneBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextMain2.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsTwoFragment)
        }

        binding.nextMain3.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsThreeFragment)
        }
        binding.nextMain4.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsFourFragment)
        }
        binding.nextMain5.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsFiveFragment)
        }
        binding.nextMain6.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsSixFragment)
        }
        binding.nextMain7.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsSevenFragment)
        }
        binding.nextMain8.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsEightFragment)
        }
        binding.nextMain9.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsNineFragment)
        }
        binding.nextMain10.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsTenFragment)
        }
        binding.nextMain11.setOnClickListener {
            findNavController().navigate(R.id.action_tipsOneFragment_to_tipsElevenFragment)
        }

        binding.backStack.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}