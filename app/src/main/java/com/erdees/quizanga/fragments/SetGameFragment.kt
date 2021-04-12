package com.erdees.quizanga.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.QuizangaApplication
import com.erdees.quizanga.R
import com.erdees.quizanga.adapters.SetGamePlayerListAdapter
import com.erdees.quizanga.viewModels.SetGameFragmentViewModel

class SetGameFragment : Fragment() {

    lateinit var application: QuizangaApplication
    lateinit var playersCountTextView: TextView
    lateinit var roundCountTextView: TextView
    private lateinit var playersDialog: AlertDialog
    private lateinit var roundsDialog: AlertDialog
    lateinit var turnsNumberPicker: NumberPicker
    lateinit var playersNumberPicker: NumberPicker
    lateinit var listView : ListView

    lateinit var viewModel : SetGameFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.set_game_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SetGameFragmentViewModel::class.java)



        /**Binders*/
        playersCountTextView = view.findViewById(R.id.view_set_game_player_count)
        roundCountTextView = view.findViewById(R.id.view_set_game_number_of_turns_tv)
        listView = view.findViewById(R.id.view_set_game_player_list)
        /**Buttons*/
        playersCountTextView.setOnClickListener {
            pickAmountOfPlayers()
        }
        roundCountTextView.setOnClickListener {
            pickNumberOfTurns()
        }
        setUpBasicSettings()
        val listAdapter = SetGamePlayerListAdapter(requireContext(), listOf())

        /**Listening to LiveData*/
        viewModel.getAmountOfGameTurns().observe(viewLifecycleOwner,  { amount ->
            application.game.setAmountOfGameTurns(amount)
            roundCountTextView.text = application.game.numberOfTurns.toString()
        })
        viewModel.getAmountOfPlayers().observe(viewLifecycleOwner,{amount ->
            application.game.setAmountOfPlayers(amount)
            playersCountTextView.text = application.game.playersAmount.toString()
        })


        return view
    }

    private fun pickAmountOfPlayers() {
        playersNumberPicker = NumberPicker(requireContext())
        with(playersNumberPicker) {
            minValue = 2
            maxValue = 12
        }
        playersNumberPicker.id = resources.getString(R.string.PLAYER_NUMBER_PICKER_ID).toInt()
        playersDialog = AlertDialog.Builder(requireContext())
                .setTitle("Pick number of players")
                .setView(playersNumberPicker)
                .setNeutralButton("Back", null)
                .setPositiveButton("Submit", null)
                .show()
        val submitButton = playersDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        submitButton.id = resources.getString(R.string.PLAYER_DIALOG_BUTTON_ID).toInt()
        submitButton.setOnClickListener {
            setAmountOfPlayers(playersNumberPicker.value)
            playersDialog.dismiss()
        }
    }

    private fun setAmountOfPlayers(number: Int) {
        viewModel.setAmountOfPlayers(number)
    }

    private fun pickNumberOfTurns() {
        turnsNumberPicker = NumberPicker(requireContext())
        with(turnsNumberPicker) {
            minValue = 3
            maxValue = 20
        }
        turnsNumberPicker.id = resources.getString(R.string.TURN_NUMBER_PICKER_ID).toInt()
        roundsDialog = AlertDialog.Builder(requireContext())
                .setTitle("Pick number of turns")
                .setView(turnsNumberPicker)
                .setNeutralButton("Back", null)
                .setPositiveButton("Submit", null)
                .show()
        val submitButton = roundsDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        submitButton.id = resources.getString(R.string.TURN_DIALOG_BUTTON_ID).toInt()
        submitButton.setOnClickListener {
            setNumberOfTurns(turnsNumberPicker.value)
        roundsDialog.dismiss()
        }
    }

    private fun setNumberOfTurns(number: Int) {
        viewModel.setAmountOfGameTurns(number)
    }

    private fun setUpBasicSettings(){
        viewModel.setAmountOfPlayers(playersCountTextView.text.toString().toInt())
       // application.game.playersAmount = playersCountTextView.text.toString().toInt()
    }

    companion object {
        const val TAG = "SetGameFragment"
        fun newInstance(): SetGameFragment = SetGameFragment()
    }
}