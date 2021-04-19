package com.erdees.quizanga.fragments

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.R
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.models.Question
import com.erdees.quizanga.viewModels.GameQuestionFragmentViewModel
import kotlin.random.Random

class GameQuestionFragment:Fragment() {

    lateinit var application: QuizangaApplication
    lateinit var answersLayout : LinearLayout
    lateinit var question : Question
    lateinit var playerWithTurn : Player
    lateinit var viewModel : GameQuestionFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_question_fragment,container,false)
        val playerNameTextView = view.findViewById<TextView>(R.id.game_question_player_name_tv)
        val questionTV = view.findViewById<TextView>(R.id.game_question_question)
        val categoryTV = view.findViewById<TextView>(R.id.game_question_category)
        answersLayout = view.findViewById(R.id.game_question_answers_layout)
        viewModel = ViewModelProvider(this).get(GameQuestionFragmentViewModel::class.java)
        viewModel.getQuestion().observe(viewLifecycleOwner, { allQuestions ->
            if(appWillSoonRunOutOfQuestions(allQuestions.results.size)) viewModel.addMoreQuestions()
            val thisQuestion = allQuestions.results[application.sessionQuestionCounter]
            question = thisQuestion
            val decodedQuestion = Html.fromHtml(thisQuestion.question)
            val decodedCategory = Html.fromHtml(thisQuestion.category)
            questionTV.text = decodedQuestion.toString()
            categoryTV.text = decodedCategory.toString()


            if(thisQuestion.type == "boolean") setAnswersAsBoolean()
            else setAnswers()
        })


        playerWithTurn = application.game.players[application.game.currentTurnCounter]
        playerNameTextView.text = "Question for " +  playerWithTurn.name

        return view
    }

    private fun randomizePositionOfCorrectAnswer():Int{
       return Random(System.currentTimeMillis()).nextInt(0,4)
    }

    private fun setAnswers(){
        val positionOfCorrectAnswer = randomizePositionOfCorrectAnswer()
        val buttonA = Button(requireContext())
        val buttonB = Button(requireContext())
        val buttonC = Button(requireContext())
        val buttonD = Button(requireContext())
        val allButtonList = listOf(buttonA,buttonB,buttonC,buttonD)
        val alphabet = listOf("A","B","C","D")
        allButtonList[positionOfCorrectAnswer].text =alphabet[allButtonList.indexOf(allButtonList[positionOfCorrectAnswer])]+". " + (Html.fromHtml(question.correct_answer).toString())
        val listOfIncorrectButtons = allButtonList - allButtonList[positionOfCorrectAnswer]
        for(i in listOfIncorrectButtons.indices){
            listOfIncorrectButtons[i].text =alphabet[i]+". " + Html.fromHtml(question.incorrect_answers[i])
        }
        allButtonList.forEach { answersLayout.addView(it) }

        allButtonList.forEach{ button ->
            button.setOnClickListener {
                questionAnswered(button.text.toString())
            }
        }
    }
    private fun questionAnswered(answer : String){
    if(answer == question.correct_answer) answeredCorrectly(playerWithTurn)
    else answeredWrongly(playerWithTurn)
    }


    private fun setAnswersAsBoolean() {
        val buttonTrue = Button(requireContext())
        buttonTrue.text = "True"
        val buttonFalse = Button(requireContext())
        buttonFalse.text = "False"
        answersLayout.addView(buttonTrue)
        answersLayout.addView(buttonFalse)
        buttonTrue.setOnClickListener {
            if (question.correct_answer == "True") answeredCorrectly(playerWithTurn)
            else answeredWrongly(playerWithTurn)
        }
        buttonFalse.setOnClickListener {
            if (question.correct_answer == "False") answeredCorrectly(playerWithTurn)
            else answeredWrongly(playerWithTurn)
        }
    }

    private fun updateGameState(){
        val updatedGameState = GameState(
            application.game.gameId,
            application.game.numberOfTurnsLeft,
            application.game.difficultLevel,
            application.game.currentTurnCounter
        )
        viewModel.updateGameState(updatedGameState)
    }

    private fun answeredCorrectly(player: Player){
        application.game.correctAnswer(player)
        application.incrementQuestionCounter()
        viewModel.updatePoints(player)
        updateGameState()
        Log.i(TAG,"Answered correctly!")
        Log.i(TAG,"turns left : ${application.game.numberOfTurnsLeft} current turn counter: ${application.game.currentTurnCounter}")

    }

    private fun answeredWrongly(player: Player){
        application.game.wrongAnswer(player)
        application.incrementQuestionCounter()
        viewModel.updatePoints(player)
        updateGameState()
        Log.i(TAG,"Answered wrongly!")
        Log.i(TAG,"turns left : ${application.game.numberOfTurnsLeft} current turn counter: ${application.game.currentTurnCounter}")

    }

    private fun appWillSoonRunOutOfQuestions(numberOfQuestions : Int):Boolean{
        return (numberOfQuestions <= application.sessionQuestionCounter+2)
    }

    companion object {
        const val TAG = "GameQuestionFragment"
        fun newInstance() : GameQuestionFragment = GameQuestionFragment()
    }
}