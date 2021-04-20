package com.erdees.quizanga.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.R
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.models.Question
import com.erdees.quizanga.viewModels.GameQuestionFragmentViewModel
import kotlin.random.Random


class GameQuestionFragment : Fragment() {

    lateinit var application: QuizangaApplication
    private lateinit var answersLayout: LinearLayout
    private lateinit var question: Question
    lateinit var playerWithTurn: Player
    private lateinit var viewModel: GameQuestionFragmentViewModel
    private var ID_OF_BUTTON_WITH_CORRECT_ANSWER: Int = 0
    private var ID_OF_BUTTON_WITH_INCORRECT_ANSWER_1: Int = 0
    private var ID_OF_BUTTON_WITH_INCORRECT_ANSWER_2: Int = 0
    private var ID_OF_BUTTON_WITH_INCORRECT_ANSWER_3: Int = 0
    private val listOfIncorrectButtonIds = listOf(
        ID_OF_BUTTON_WITH_INCORRECT_ANSWER_1,
        ID_OF_BUTTON_WITH_INCORRECT_ANSWER_2,
        ID_OF_BUTTON_WITH_INCORRECT_ANSWER_3
    )
    private val alphabet = listOf("A", "B", "C", "D")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_question_fragment, container, false)
        val playerNameTextView = view.findViewById<TextView>(R.id.game_question_player_name_tv)
        val questionTV = view.findViewById<TextView>(R.id.game_question_question)
        val categoryTV = view.findViewById<TextView>(R.id.game_question_category)
        answersLayout = view.findViewById(R.id.game_question_answers_layout)
        ID_OF_BUTTON_WITH_CORRECT_ANSWER = requireActivity().resources.getString(R.string.ID_OF_CORRECT_ANSWER).toInt()
        ID_OF_BUTTON_WITH_INCORRECT_ANSWER_1 = requireActivity().resources.getString(R.string.ID_OF_INCORRECT_ANSWER1).toInt()
        ID_OF_BUTTON_WITH_INCORRECT_ANSWER_2 = requireActivity().resources.getString(R.string.ID_OF_INCORRECT_ANSWER2).toInt()
        ID_OF_BUTTON_WITH_INCORRECT_ANSWER_3 = requireActivity().resources.getString(R.string.ID_OF_INCORRECT_ANSWER3).toInt()
        viewModel = ViewModelProvider(this).get(GameQuestionFragmentViewModel::class.java)

        viewModel.getQuestion().observe(viewLifecycleOwner, { allQuestions ->
            if (appWillSoonRunOutOfQuestions(allQuestions.results.size)) viewModel.addMoreQuestions()
            val thisQuestion = allQuestions.results[application.sessionQuestionCounter]
            question = thisQuestion
            val decodedQuestion = Html.fromHtml(thisQuestion.question)
            val decodedCategory = Html.fromHtml(thisQuestion.category)
            questionTV.text = decodedQuestion.toString()
            categoryTV.text = decodedCategory.toString()

            if (thisQuestion.type == "boolean") setAnswersAsBoolean()
            else setAnswers()
            Log.i(TAG,thisQuestion.correct_answer)
        })

        playerWithTurn = application.game.players[application.game.currentTurnCounter]
        playerNameTextView.text = "Question for " + playerWithTurn.name

        return view
    }


    private fun List<Button>.blockAllButtons(){
        this.forEach{
            it.isClickable = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setAnswers() {
        val positionOfCorrectAnswer = randomizePositionOfCorrectAnswer()
        val buttonA = Button(requireContext())
        val buttonB = Button(requireContext())
        val buttonC = Button(requireContext())
        val buttonD = Button(requireContext())
        val allButtonList = listOf(buttonA, buttonB, buttonC, buttonD)
        val buttonWithCorrectAnswer = allButtonList[positionOfCorrectAnswer]
        buttonWithCorrectAnswer.text =
            alphabet[allButtonList.indexOf(allButtonList[positionOfCorrectAnswer])] + ". " + (Html.fromHtml(
                question.correct_answer
            ).toString())
        buttonWithCorrectAnswer.id = ID_OF_BUTTON_WITH_CORRECT_ANSWER
        val listOfIncorrectButtons = allButtonList - buttonWithCorrectAnswer
        for (i in listOfIncorrectButtons.indices) {
            listOfIncorrectButtons[i].text =
                alphabet[i] + ". " + Html.fromHtml(question.incorrect_answers[i])
            listOfIncorrectButtons[i].id = listOfIncorrectButtonIds[i]
        }
        allButtonList.forEach { answersLayout.addView(it) }

        allButtonList.forEach { button ->
            button.setOnClickListener {
                questionAnswered(button.id)
                allButtonList.blockAllButtons()
            }
        }
    }

    private fun answerIsCorrect(idOfChosenButton: Int): Boolean {
        return (idOfChosenButton == ID_OF_BUTTON_WITH_CORRECT_ANSWER)
    }

    private fun questionAnswered(idOfButton: Int) {
        val chosenButton = view?.findViewById<Button>(idOfButton)
        if (answerIsCorrect(idOfButton)) lightButtonAccordiglyToAnswer(
            chosenButton!!,
            R.color.green_500,
            true
        )
        else lightButtonAccordiglyToAnswer(chosenButton!!, R.color.red_500,false)
    }

    private fun lightButtonAccordiglyToAnswer(button: Button, colorId: Int, isAnswerCorrect: Boolean) {
        button.setBackgroundColor(resources.getColor(colorId))
        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = 3
        anim.setAnimationListener( object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                if(isAnswerCorrect)answeredCorrectly(playerWithTurn)
                else answeredWrongly(playerWithTurn)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        Log.i(TAG,button.id.toString() + button.text.toString())
        button.startAnimation(anim)
    }

    private fun setAnswersAsBoolean() {
        val buttonTrue = Button(requireContext())
        val buttonFalse = Button(requireContext())
        val thisButtons = listOf(buttonTrue,buttonFalse)
        buttonTrue.text = "True"
        buttonFalse.text = "False"
        thisButtons.forEach{ answersLayout.addView(it)}
        if (question.correct_answer == "True"){
            buttonTrue.id = ID_OF_BUTTON_WITH_CORRECT_ANSWER
            buttonFalse.id = ID_OF_BUTTON_WITH_INCORRECT_ANSWER_1
        }
        else{
            buttonFalse.id = ID_OF_BUTTON_WITH_CORRECT_ANSWER
            buttonTrue.id = ID_OF_BUTTON_WITH_INCORRECT_ANSWER_1
        }
        buttonTrue.setOnClickListener {
            questionAnswered(buttonTrue.id)
            thisButtons.blockAllButtons()
        }
        buttonFalse.setOnClickListener {
            questionAnswered(buttonFalse.id)
            thisButtons.blockAllButtons()
        }
    }

    private fun updateGameState() {
        val updatedGameState = GameState(
            application.game.gameId,
            application.game.numberOfTurnsLeft,
            application.game.difficultLevel,
            application.game.currentTurnCounter
        )
        viewModel.updateGameState(updatedGameState)
    }

    private fun answeredCorrectly(player: Player) {
        application.game.correctAnswer(player)
        application.incrementQuestionCounter()
        viewModel.updatePoints(player)
        updateGameState()
        Log.i(TAG, "Answered correctly!")
        Log.i(
            TAG,
            "turns left : ${application.game.numberOfTurnsLeft} current turn counter: ${application.game.currentTurnCounter}"
        )
    }

    private fun answeredWrongly(player: Player) {
        application.game.wrongAnswer(player)
        application.incrementQuestionCounter()
        viewModel.updatePoints(player)
        updateGameState()
        Log.i(TAG, "Answered wrongly!")
        Log.i(
            TAG,
            "turns left : ${application.game.numberOfTurnsLeft} current turn counter: ${application.game.currentTurnCounter}"
        )

    }




    private fun randomizePositionOfCorrectAnswer(): Int {
        return Random(System.currentTimeMillis()).nextInt(0, 4)
    }

    private fun appWillSoonRunOutOfQuestions(numberOfQuestions: Int): Boolean {
        return (numberOfQuestions <= application.sessionQuestionCounter + 2)
    }

    companion object {
        const val TAG = "GameQuestionFragment"
        fun newInstance(): GameQuestionFragment = GameQuestionFragment()
    }
}