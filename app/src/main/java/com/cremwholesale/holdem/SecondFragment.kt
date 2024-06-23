package com.cremwholesale.holdem

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cremwholesale.holdem.databinding.FragmentSecondBinding
import com.cremwholesale.holdem.model.Card
import kotlinx.coroutines.*

class SecondFragment : Fragment() {

    private val TAG = "TEST-->"

    private var scoreBetAll = 0
    private var scoreBetPlayer = 20
    private var scoreBetDealer = 20
    private var balance = 500

//    private var progression = 1

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private var gameStateCurrent = 3
    private lateinit var playerCards: Array<ImageView>
    private lateinit var dealerCards: Array<ImageView>
    private lateinit var tableCards: Array<ImageView>

    private var musicOn = true

    private val cards = mutableListOf(
        R.drawable.ac, R.drawable.ad, R.drawable.ah, R.drawable.aces,
        R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5,
        R.drawable.c6, R.drawable.c7, R.drawable.c8, R.drawable.c9,
        R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5,
        R.drawable.d6, R.drawable.d7, R.drawable.d8, R.drawable.d9,
        R.drawable.h2, R.drawable.h3, R.drawable.h4, R.drawable.h5,
        R.drawable.h6, R.drawable.h7, R.drawable.h8, R.drawable.h9,
        R.drawable.jc, R.drawable.jd, R.drawable.jh, R.drawable.js,
        R.drawable.ks, R.drawable.kd, R.drawable.kh, R.drawable.kc,
        R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5,
        R.drawable.s6, R.drawable.s7, R.drawable.s8, R.drawable.s9,
        R.drawable.qc, R.drawable.qd, R.drawable.qh, R.drawable.qs,
        R.drawable.ts, R.drawable.th, R.drawable.td, R.drawable.tc
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dealerCards = arrayOf(binding.compCard1, binding.compCard2)
        tableCards =
            arrayOf(binding.card1, binding.card2, binding.card3, binding.card4, binding.card5)
        playerCards = arrayOf(binding.myCard1, binding.myCard2)

        binding.betBank.text = "0"
        binding.betBalance.text = balance.toString()
        binding.dealerBet.text = "0"
        binding.playerBet.text = "0"



        startGame()

        binding.betButton.setOnClickListener {
            if (balance < 20){
                showToast("UPS Balance")
            }else{
                betScore(binding.playerBet, 1)
            }

        }

        binding.btnDeal.setOnClickListener {
//            determineWinner()
        }

        binding.checkButton.setOnClickListener {
            if (gameStateCurrent >= 6) {
                determineWinner()
            }else{
                binding.progressComp.visibility = View.VISIBLE
                binding.checkButton.isEnabled = false
                binding.betButton.isEnabled = false
                binding.foldButton.isEnabled = false
                binding.btnDeal.isEnabled = false

                GlobalScope.launch(Dispatchers.Main) {
                    delay(3000)
                    checkNext(1)
                    computer(binding.dealerBet)
                    binding.progressComp.visibility = View.INVISIBLE
                    binding.checkButton.isEnabled = true
                    binding.betButton.isEnabled = true
                    binding.foldButton.isEnabled = true
                    binding.btnDeal.isEnabled = true
                }
            }

        }


        binding.foldButton.setOnClickListener {
            resetGame(1)
        }

        binding.backStack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnMusic.setOnClickListener {
            musicOn = !musicOn
            updateMusicButton(binding.btnMusic)
        }
    }

    private fun computer(view: TextView) {
        val methods = listOf(
            { checkNext(0) },
            { resetGame(0) },
            { betScore(view, 0) }
        )

        val randomMethod = methods.random()
        randomMethod()
    }

    private fun computer2(view: TextView) {
        val methods = listOf(
            { resetGame(0) },
            { betScore(view, 0) }
        )

        val randomMethod = methods.random()
        randomMethod()
    }

    private fun betScore(view: TextView, int: Int) {
        Log.d(TAG, "betScore: $gameStateCurrent")
        if (gameStateCurrent >= 6) {
            determineWinner()
        }else{
            if (int != 0){
                if (view == binding.dealerBet) {
                    scoreBetDealer += 20
                    binding.dealerBet.text = scoreBetDealer.toString()
                    showToast("BET")
                } else {
                    scoreBetPlayer += 20
                    binding.playerBet.text = scoreBetPlayer.toString()

                }

                balance -= 20
                binding.betBalance.text = balance.toString()

                scoreBetAll = binding.dealerBet.text.toString().toInt() + binding.playerBet.text.toString().toInt()
                binding.betBank.text = scoreBetAll.toString()

                binding.progressComp.visibility = View.VISIBLE
                binding.checkButton.isEnabled = false
                binding.betButton.isEnabled = false
                binding.foldButton.isEnabled = false
                binding.btnDeal.isEnabled = false

                GlobalScope.launch(Dispatchers.Main) {
                    delay(3000)
                    checkNext(1)
                    computer2(binding.dealerBet)
                    binding.progressComp.visibility = View.INVISIBLE
                    binding.checkButton.isEnabled = true
                    binding.betButton.isEnabled = true
                    binding.foldButton.isEnabled = true
                    binding.btnDeal.isEnabled = true
                }
            }else{
                binding.dealerBet.text = binding.playerBet.text.toString()
                scoreBetAll = binding.dealerBet.text.toString().toInt() + binding.playerBet.text.toString().toInt()
                binding.betBank.text = scoreBetAll.toString()
            }


        }




    }


    private fun updateMusicButton(imageView: ImageView) {
        if (musicOn) imageView.setImageResource(R.drawable.ic_music) else imageView.setImageResource(
            R.drawable.ic_music_off
        )
    }

    private fun determineWinner() {
        val playerHand = evaluateHand(playerCards.map { cardFromImageView(it) } + tableCards.map {
            cardFromImageView(it)
        })
        val dealerHand = evaluateHand(dealerCards.map { cardFromImageView(it) } + tableCards.map {
            cardFromImageView(it)
        })

        val result = compareHands(playerHand, dealerHand)

        Log.d(TAG, "determineWinner: $result")
        when (result) {
            2 -> showToast("Игрок вы")
            1 -> showToast("Игрок выиграл!")
            -1 -> showToast("Дилер выиграл!")
            0 -> showToast("Ничья!")
            -2 -> showToast("Дилер выи")
        }
    }

    private fun evaluateHand(cards: List<Card>): HandEvaluationResult {
        val pokerHand = PokerHand.fromList(cards)

        return when {
            pokerHand.isStraightFlush -> HandEvaluationResult.STRAIGHT_FLUSH
            pokerHand.isFourOfAKind -> HandEvaluationResult.FOUR_OF_A_KIND
            pokerHand.isFullHouse -> HandEvaluationResult.FULL_HOUSE
            pokerHand.isFlush -> HandEvaluationResult.FLUSH
            pokerHand.isStraight -> HandEvaluationResult.STRAIGHT
            pokerHand.isThreeOfAKind -> HandEvaluationResult.THREE_OF_A_KIND
            pokerHand.isTwoPair -> HandEvaluationResult.TWO_PAIR
            pokerHand.isPair -> HandEvaluationResult.ONE_PAIR
            else -> HandEvaluationResult.HIGH_CARD
        }
    }

    private fun compareHands(
        playerHand: HandEvaluationResult,
        dealerHand: HandEvaluationResult
    ): Int {
        return playerHand.compareTo(dealerHand)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun cardFromImageView(imageView: ImageView): Card {
        val cardDrawable = imageView.drawable
            ?: throw IllegalArgumentException("ImageView does not have a drawable")

        val cardWeight = getCardWeightFromResource(cardDrawable)
        val cardSuit = getCardSuitFromResource(cardDrawable)

        return Card(cardWeight, cardSuit)
    }

    private fun getCardWeightFromResource(cardDrawable: Drawable): Weight {
        val context = requireContext()
        return when {
            cardDrawable.isEqualTo(context, R.drawable.ac) -> Weight.ACE
            cardDrawable.isEqualTo(context, R.drawable.ad) -> Weight.ACE
            cardDrawable.isEqualTo(context, R.drawable.ah) -> Weight.ACE
            cardDrawable.isEqualTo(context, R.drawable.aces) -> Weight.ACE
            cardDrawable.isEqualTo(context, R.drawable.c2) -> Weight.TWO
            cardDrawable.isEqualTo(context, R.drawable.c3) -> Weight.THREE
            cardDrawable.isEqualTo(context, R.drawable.c4) -> Weight.FOUR
            cardDrawable.isEqualTo(context, R.drawable.c5) -> Weight.FIVE
            cardDrawable.isEqualTo(context, R.drawable.c6) -> Weight.SIX
            cardDrawable.isEqualTo(context, R.drawable.c7) -> Weight.SEVEN
            cardDrawable.isEqualTo(context, R.drawable.c8) -> Weight.EIGHT
            cardDrawable.isEqualTo(context, R.drawable.c9) -> Weight.NINE
            cardDrawable.isEqualTo(context, R.drawable.d2) -> Weight.TWO
            cardDrawable.isEqualTo(context, R.drawable.d3) -> Weight.THREE
            cardDrawable.isEqualTo(context, R.drawable.d4) -> Weight.FOUR
            cardDrawable.isEqualTo(context, R.drawable.d5) -> Weight.FIVE
            cardDrawable.isEqualTo(context, R.drawable.d6) -> Weight.SIX
            cardDrawable.isEqualTo(context, R.drawable.d7) -> Weight.SEVEN
            cardDrawable.isEqualTo(context, R.drawable.d8) -> Weight.EIGHT
            cardDrawable.isEqualTo(context, R.drawable.d9) -> Weight.NINE
            cardDrawable.isEqualTo(context, R.drawable.h2) -> Weight.TWO
            cardDrawable.isEqualTo(context, R.drawable.h3) -> Weight.THREE
            cardDrawable.isEqualTo(context, R.drawable.h4) -> Weight.FOUR
            cardDrawable.isEqualTo(context, R.drawable.h5) -> Weight.FIVE
            cardDrawable.isEqualTo(context, R.drawable.h6) -> Weight.SIX
            cardDrawable.isEqualTo(context, R.drawable.h7) -> Weight.SEVEN
            cardDrawable.isEqualTo(context, R.drawable.h8) -> Weight.EIGHT
            cardDrawable.isEqualTo(context, R.drawable.h9) -> Weight.NINE

            cardDrawable.isEqualTo(context, R.drawable.s2) -> Weight.TWO
            cardDrawable.isEqualTo(context, R.drawable.s3) -> Weight.THREE
            cardDrawable.isEqualTo(context, R.drawable.s4) -> Weight.FOUR
            cardDrawable.isEqualTo(context, R.drawable.s5) -> Weight.FIVE
            cardDrawable.isEqualTo(context, R.drawable.s6) -> Weight.SIX
            cardDrawable.isEqualTo(context, R.drawable.s7) -> Weight.SEVEN
            cardDrawable.isEqualTo(context, R.drawable.s8) -> Weight.EIGHT
            cardDrawable.isEqualTo(context, R.drawable.s9) -> Weight.NINE

            cardDrawable.isEqualTo(context, R.drawable.jc) -> Weight.JACK
            cardDrawable.isEqualTo(context, R.drawable.jd) -> Weight.JACK
            cardDrawable.isEqualTo(context, R.drawable.jh) -> Weight.JACK
            cardDrawable.isEqualTo(context, R.drawable.js) -> Weight.JACK
            cardDrawable.isEqualTo(context, R.drawable.ks) -> Weight.KING
            cardDrawable.isEqualTo(context, R.drawable.kc) -> Weight.KING
            cardDrawable.isEqualTo(context, R.drawable.kd) -> Weight.KING
            cardDrawable.isEqualTo(context, R.drawable.kh) -> Weight.KING
            cardDrawable.isEqualTo(context, R.drawable.qc) -> Weight.QUEEN
            cardDrawable.isEqualTo(context, R.drawable.qd) -> Weight.QUEEN
            cardDrawable.isEqualTo(context, R.drawable.qh) -> Weight.QUEEN
            cardDrawable.isEqualTo(context, R.drawable.qs) -> Weight.QUEEN
            cardDrawable.isEqualTo(context, R.drawable.tc) -> Weight.TEN
            cardDrawable.isEqualTo(context, R.drawable.ts) -> Weight.TEN
            cardDrawable.isEqualTo(context, R.drawable.th) -> Weight.TEN
            cardDrawable.isEqualTo(context, R.drawable.td) -> Weight.TEN
            else -> {
                // Log the unknown drawable resource ID
                Log.e(TAG, "Unknown card drawable: $cardDrawable")
                throw IllegalArgumentException("Unknown card drawable")
            }
        }
    }

    private fun getCardSuitFromResource(cardDrawable: Drawable): Suit {
        val context = requireContext()
        return when {
            cardDrawable.isEqualTo(context, R.drawable.ac) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.ad) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.ah) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.aces) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.c2) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.c3) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.c4) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.c5) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.c6) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.c7) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.c8) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.c9) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.d2) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.d3) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.d4) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.d5) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.d6) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.d7) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.d8) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.d9) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.h2) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.h3) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.h4) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.h5) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.h6) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.h7) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.h8) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.h9) -> Suit.HEARTS

            cardDrawable.isEqualTo(context, R.drawable.s2) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.s3) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.s4) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.s5) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.s6) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.s7) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.s8) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.s9) -> Suit.SPADES

            cardDrawable.isEqualTo(context, R.drawable.jc) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.jd) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.jh) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.js) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.ks) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.kc) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.kd) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.kh) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.qc) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.qd) -> Suit.DIAMONDS
            cardDrawable.isEqualTo(context, R.drawable.qh) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.qs) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.tc) -> Suit.CLUBS
            cardDrawable.isEqualTo(context, R.drawable.ts) -> Suit.SPADES
            cardDrawable.isEqualTo(context, R.drawable.th) -> Suit.HEARTS
            cardDrawable.isEqualTo(context, R.drawable.td) -> Suit.DIAMONDS
            else -> {
                // Log the unknown drawable resource ID
                Log.e(TAG, "Unknown card drawable: $cardDrawable")
                throw IllegalArgumentException("Unknown card drawable")
            }
        }
    }

    private fun Drawable.isEqualTo(context: Context, resId: Int): Boolean {
        val resDrawable = ContextCompat.getDrawable(context, resId) ?: return false
        if (this is BitmapDrawable && resDrawable is BitmapDrawable) {
            val bitmap1 = this.bitmap
            val bitmap2 = resDrawable.bitmap
            return bitmap1.sameAs(bitmap2)
        }
        return false
    }


    private fun checkNext(int: Int) {
        if (int != 0){
            when (gameStateCurrent) {
                4 -> dealCardTo(tableCards[3])
                5 -> dealCardTo(tableCards[4])
            }
            gameStateCurrent++
        }else{
            showToast("CHECK")
        }
        scoreBetAll = binding.dealerBet.text.toString().toInt() + binding.playerBet.text.toString().toInt()
        binding.betBank.text = scoreBetAll.toString()
    }

    private fun distribution() {
        dealCardTo(dealerCards[0])
        dealCardTo(dealerCards[1])
        dealCardTo(playerCards[0])
        dealCardTo(playerCards[1])
        dealTableCards()

    }

    private fun dealCardTo(imageView: ImageView) {
        val cardResource = cards.random()
        cards.remove(cardResource)
        imageView.setImageResource(cardResource)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun dealTableCards() {
        GlobalScope.launch {
            for (i in 0..2) {
                delay(500)
                withContext(Dispatchers.Main) {
                    dealCardTo(tableCards[i])
                }
            }
            withContext(Dispatchers.Main) {
                binding.checkButton.isEnabled = true
                binding.betButton.isEnabled = true
                binding.foldButton.isEnabled = true
                binding.btnDeal.isEnabled = true

            }
        }
    }


    private fun startGame() {
        binding.checkButton.isEnabled = false
        binding.betButton.isEnabled = false
        binding.foldButton.isEnabled = false
        binding.btnDeal.isEnabled = false
        cards.shuffle()
        gameStateCurrent = 4
        clearAllCards()
        distribution()
        balance -= 20
        binding.betBalance.text = balance.toString()
        binding.dealerBet.text = scoreBetDealer.toString()
        binding.playerBet.text = scoreBetPlayer.toString()

    }

    private fun clearAllCards() {
        dealerCards.forEach { it.setImageResource(R.drawable.card) }
        playerCards.forEach { it.setImageResource(R.drawable.card) }
        tableCards.forEach { it.setImageResource(R.drawable.card) }
    }

    private fun resetGame(int: Int) {
        if (int == 0){
            balance += scoreBetAll
        }
        // Reset game state variables
        gameStateCurrent = 4

        // Clear cards from ImageViews
        for (card in playerCards) {
            card.setImageResource(R.drawable.card)
        }
        for (card in dealerCards) {
            card.setImageResource(R.drawable.card)
        }
        for (card in tableCards) {
            card.setImageResource(R.drawable.card)
        }
        showToast("FOLD")

        binding.betBank.text = "0"
        binding.dealerBet.text = "0"
        binding.playerBet.text = "0"
        gameStateCurrent = 3
        scoreBetPlayer = 20
        scoreBetDealer = 20
        scoreBetAll = 0

        startGame()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
