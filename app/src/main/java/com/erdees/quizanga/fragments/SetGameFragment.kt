package com.erdees.quizanga.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.QuizangaApplication
import com.erdees.quizanga.R
import com.erdees.quizanga.adapters.SetGamePlayerListAdapter
import com.erdees.quizanga.levelOfDifficult.Easy
import com.erdees.quizanga.levelOfDifficult.Hard
import com.erdees.quizanga.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.viewModels.SetGameFragmentViewModel

class SetGameFragment : Fragment(), AdapterView.OnItemClickListener {

    lateinit var application: QuizangaApplication
    lateinit var playersCountTextView: TextView
    lateinit var roundCountTextView: TextView
    private lateinit var playersDialog: AlertDialog
    private lateinit var roundsDialog: AlertDialog
    lateinit var turnsNumberPicker: NumberPicker
    lateinit var playersNumberPicker: NumberPicker
    lateinit var listView : ListView
    lateinit var playerListLayout : LinearLayout
    lateinit var viewModel : SetGameFragmentViewModel
    lateinit var startGameButton: Button
    private val playerList = mutableListOf<Player>()

    private lateinit var levelsList : List<LevelOfDifficult>
    private lateinit var levelsSpinner : AutoCompleteTextView
    override fun onResume() {
        levelsList = listOf(Easy(), Hard())
        val levelsAdapter = ArrayAdapter(requireActivity(), R.layout.support_simple_spinner_dropdown_item, levelsList.map{it.name})
        with(levelsSpinner) {
            setSelection(0)
            setAdapter(levelsAdapter)
            onItemClickListener = this@SetGameFragment
            gravity = Gravity.CENTER
            id = 0
        }
        super.onResume()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.set_game_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SetGameFragmentViewModel::class.java)


        /**Binders*/
        playersCountTextView = view.findViewById(R.id.view_set_game_player_count)
        roundCountTextView = view.findViewById(R.id.view_set_game_number_of_turns_tv)
        playerListLayout = view.findViewById(R.id.view_set_game_player_list_layout)
        startGameButton = view.findViewById(R.id.view_set_game_start_button)
        levelsSpinner = view.findViewById(R.id.view_set_game_difficult_level)
        /**Buttons*/
        playersCountTextView.setOnClickListener {
            pickAmountOfPlayers()
        }
        roundCountTextView.setOnClickListener {
            pickNumberOfTurns()
        }
        startGameButton.setOnClickListener {
            saveListOfPlayersFromLayoutToList()
        }

        setUpBasicSettings()

        /**Listening to LiveData*/
        viewModel.getAmountOfGameTurns().observe(viewLifecycleOwner,  { amount ->
            application.game.setAmountOfGameTurns(amount)
            roundCountTextView.text = application.game.numberOfTurns.toString()
        })
        viewModel.getAmountOfPlayers().observe(viewLifecycleOwner,{amount ->
            application.game.setAmountOfPlayers(amount)
            playersCountTextView.text = application.game.playersAmount.toString()
            saveListOfPlayersFromLayoutToList()
            setListOfPlayersAccordingly(amount)

        })


        return view
    }

    private fun prePopulateListWithPreviousNames(list: List<Player>,index: Int){
        val thisRow = playerListLayout.getChildAt(index)
        val thisEditText = thisRow.findViewById<EditText>(R.id.item_set_game_name_edittext)
        thisEditText.setText(list[index].name)
    }

    private fun saveListOfPlayersFromLayoutToList(){
    val count = playerListLayout.childCount
        for(i in 0 until(count)){
            val rowView = playerListLayout.getChildAt(i)
            val editText = rowView.findViewById<EditText>(R.id.item_set_game_name_edittext)
            if(!editText.text.isNullOrBlank()) {
                val newPlayer = Player(editText.text.toString(),0)
                playerList.add(newPlayer)
            }
            else playerList.add(Player("",0))
        }
    }

    private fun setListOfPlayersAccordingly(number: Int){
        playerListLayout.removeAllViews()
        for(i in 0 until number) {
            val inflater = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_set_game_player_list, null)
            playerListLayout.addView(inflater)
          if(playerList.size > i)  prePopulateListWithPreviousNames(playerList,i)
        }
        playerList.clear()
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
    }

    companion object {
        const val TAG = "SetGameFragment"
        fun newInstance(): SetGameFragment = SetGameFragment()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    Log.i("TEST","Clicked somewhere")
    }
}