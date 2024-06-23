package com.cremwholesale.holdem

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.cremwholesale.holdem.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private val cards = arrayOf(
//        R.drawable.ac, R.drawable.ad, R.drawable.ah, R.drawable.aces, R.drawable.c2, R.drawable.c3,
//        R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7, R.drawable.c8, R.drawable.c9,
//        R.drawable.card, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6,
//        R.drawable.d7, R.drawable.d8, R.drawable.d9, R.drawable.h2, R.drawable.h3, R.drawable.h4,
//        R.drawable.h5, R.drawable.h6, R.drawable.jc, R.drawable.jd, R.drawable.jh, R.drawable.js,
//        R.drawable.h7, R.drawable.h8, R.drawable.h9, R.drawable.ks, R.drawable.kd, R.drawable.kh,
//        R.drawable.ks, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5, R.drawable.s6,
//        R.drawable.s7, R.drawable.s8, R.drawable.s9, R.drawable.qc, R.drawable.qd, R.drawable.qh,
//        R.drawable.qs, R.drawable.ts, R.drawable.th, R.drawable.td, R.drawable.tc,
//    )
//
//
//    private fun startGame() {
//        // Перемешиваем колоду
//        cards.shuffle()
//
//        // Раздаем карты с задержкой
//        val handler = Handler(Looper.getMainLooper())
//        for (i in 0..1) {
//            handler.postDelayed({
//                playerCards[i].setImageResource(cards[i])
//                dealerCards[i].setImageResource(cards[i + 2])
//            }, (i * 1000).toLong())
//        }
//
//        handler.postDelayed({
//            Toast.makeText(requireContext(), "Карты розданы!", Toast.LENGTH_SHORT).show()
//        }, 3000)
//    }
}