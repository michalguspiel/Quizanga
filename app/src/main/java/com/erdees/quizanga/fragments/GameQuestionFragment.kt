package com.erdees.quizanga.fragments

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.media.MediaPlayer
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.R
import com.erdees.quizanga.Utils.addMargin
import com.erdees.quizanga.Utils.appWillSoonRunOutOfQuestions
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
    private lateinit var listOfIncorrectButtonIds: List<Int>
    private lateinit var alphabet: MutableList<String>
    private val red = 0xFFFF0000

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

        setButtonIdsAndIncorrectButtonIdList()
        alphabet = mutableListOf("A", "B", "C", "D")

        viewModel = ViewModelProvider(this).get(GameQuestionFragmentViewModel::class.java)
        viewModel.getQuestions().observe(viewLifecycleOwner, { allQuestions ->
            if (appWillSoonRunOutOfQuestions(
                    allQuestions.results.size,
                    application.sessionQuestionCounter
                )
            ) viewModel.addMoreQuestions(application.game.difficultLevel)
            val thisQuestion = allQuestions.results[application.sessionQuestionCounter]
            question = thisQuestion
            val decodedQuestion = Html.fromHtml(thisQuestion.question)
            val decodedCategory = Html.fromHtml(thisQuestion.category)
            questionTV.text = decodedQuestion.toString()
            categoryTV.text = decodedCategory.toString()

            if (thisQuestion.type == "boolean") setAnswersAsBoolean()
            else setAnswers()
            Log.i(TAG, thisQuestion.correct_answer)
        })

        viewModel.getPlayersForThisGame(application.game.gameId).observe(viewLifecycleOwner,
             {
                playerWithTurn = it[application.game.currentTurnCounter]
                playerNameTextView.text = "Question for " + playerWithTurn.name
            })

        return view
    }


    private fun setButtonIdsAndIncorrectButtonIdList() {
        ID_OF_BUTTON_WITH_CORRECT_ANSWER =
            requireActivity().resources.getString(R.string.ID_OF_CORRECT_ANSWER).toInt()
        ID_OF_BUTTON_WITH_INCORRECT_ANSWER_1 =
            requireActivity().resources.getString(R.string.ID_OF_INCORRECT_ANSWER1).toInt()
        ID_OF_BUTTON_WITH_INCORRECT_ANSWER_2 =
            requireActivity().resources.getString(R.string.ID_OF_INCORRECT_ANSWER2).toInt()
        ID_OF_BUTTON_WITH_INCORRECT_ANSWER_3 =
            requireActivity().resources.getString(R.string.ID_OF_INCORRECT_ANSWER3).toInt()
        listOfIncorrectButtonIds = listOf(
            ID_OF_BUTTON_WITH_INCORRECT_ANSWER_1,
            ID_OF_BUTTON_WITH_INCORRECT_ANSWER_2,
            ID_OF_BUTTON_WITH_INCORRECT_ANSWER_3
        )
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
        setCorrectButtonTextAndId(buttonWithCorrectAnswer, alphabet[allButtonList.indexOf(allButtonList[positionOfCorrectAnswer])])
        val listOfIncorrectButtons = allButtonList - buttonWithCorrectAnswer
        setWrongButtonsTextAndIds(listOfIncorrectButtons)
        allButtonList.forEach { button ->
            button.addMargin(5)
            button.setBackgroundResource(R.drawable.answer_button)
            answersLayout.addView(button)
            button.setOnClickListener {
                questionAnswered(button.id)
                allButtonList.blockAllButtons()
            }
        }
    }

    private fun setAnswersAsBoolean() {
        val buttonTrue = Button(requireContext())
        val buttonFalse = Button(requireContext())
        val thisButtons = listOf(buttonTrue, buttonFalse)
        buttonTrue.text = getString(R.string.True)
        buttonFalse.text = getString(R.string.False)
        thisButtons.forEach { button ->
            button.setBackgroundResource(R.drawable.answer_button)
            button.addMargin(5)
            answersLayout.addView(button) }
        if (question.correct_answer == "True") setIdOfBooleanAnswersButtons(buttonTrue, buttonFalse)
        else setIdOfBooleanAnswersButtons(buttonFalse, buttonTrue)
        thisButtons.forEach { button ->
            button.setOnClickListener {
                questionAnswered(button.id)
                thisButtons.blockAllButtons()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCorrectButtonTextAndId(buttonWithCorrectAnswer: Button, letter: String) {
        buttonWithCorrectAnswer.text = "$letter. " + (Html.fromHtml(
            question.correct_answer
        ).toString())
        buttonWithCorrectAnswer.id = ID_OF_BUTTON_WITH_CORRECT_ANSWER
        alphabet.minusAssign(letter)
    }

    private fun setWrongButtonsTextAndIds(wrongButtonList: List<Button>) {
        for (i in wrongButtonList.indices) {
            setEachWrongButtonTextAndId(wrongButtonList[i], i)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setEachWrongButtonTextAndId(buttonWithWrongAnswer: Button, i: Int) {
        val letter = alphabet[i]
        buttonWithWrongAnswer.text = "$letter. " + Html.fromHtml(question.incorrect_answers[i])
        buttonWithWrongAnswer.id = listOfIncorrectButtonIds[i]
    }


    private fun isAnswerCorrect(idOfChosenButton: Int): Boolean {
        return (idOfChosenButton == ID_OF_BUTTON_WITH_CORRECT_ANSWER)
    }

    private fun questionAnswered(idOfButton: Int) {
        val chosenButton = view?.findViewById<Button>(idOfButton)
        if (isAnswerCorrect(idOfButton)) lightCorrectAnswer(chosenButton!!)
        else lightWrongAnswerOnRedAndLightCorrectAnswerOnGreen(chosenButton!!)
    }



    private fun lightWrongAnswerOnRedAndLightCorrectAnswerOnGreen(button: Button) {
        playSound(false)
        val blinkingAnim: Animation = AlphaAnimation(1.0f, 0.2f)
        val colorAnimation: ObjectAnimator = ObjectAnimator.ofInt(
            button,
            "backgroundColor",
            R.color.button_color,
            red.toInt(),
        )
        setColorAnimation(colorAnimation)
        setAnimation(blinkingAnim)
        val correctButton = view?.findViewById<Button>(ID_OF_BUTTON_WITH_CORRECT_ANSWER)
        correctButton!!.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green_500))
        colorAnimation.start()
        correctButton.startAnimation(blinkingAnim)
    }


    private fun lightCorrectAnswer(button: Button) {
        playSound(true)
        button.setBackgroundColor(resources.getColor(R.color.green_500))
        val anim: Animation = AlphaAnimation(1.0f, 0.2f)
        setAnimation(anim)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                answeredCorrectly(playerWithTurn)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        button.startAnimation(anim)
    }



    private fun setIdOfBooleanAnswersButtons(correctButton: Button, wrongButton: Button) {
        correctButton.id = ID_OF_BUTTON_WITH_CORRECT_ANSWER
        wrongButton.id = ID_OF_BUTTON_WITH_INCORRECT_ANSWER_1
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
        viewModel.updatePoints(player)
        application.incrementQuestionCounter()
        updateGameState()
        Log.i(TAG, "Answered correctly!")
    }

    private fun answeredWrongly(player: Player) {
        application.game.wrongAnswer(player)
        viewModel.updatePoints(player)
        application.incrementQuestionCounter()
        updateGameState()
        Log.i(TAG, "Answered wrongly!")
    }

    private fun setColorAnimation(animation : ObjectAnimator){
        animation.duration = 600
        animation.setEvaluator(ArgbEvaluator())
    }

    private fun setAnimation(animation: Animation) {
        with(animation) {
            duration = 650
            startOffset = 20
            repeatMode = Animation.REVERSE
            repeatCount = 3
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }
                override fun onAnimationEnd(animation: Animation?) {
                    answeredWrongly(playerWithTurn)
                }
                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
        }
    }

    private fun playSound(isAnswerCorrect : Boolean){
        val correctSound = MediaPlayer.create(requireContext(),R.raw.correct_answer)
        val wrongSound = MediaPlayer.create(requireContext(),R.raw.wrong_answer)
        correctSound.setOnCompletionListener { it.release() }
        wrongSound.setOnCompletionListener { it.release() }
        if(isAnswerCorrect) correctSound.start() else wrongSound.start()
    }

    private fun List<Button>.blockAllButtons() {
        this.forEach {
            it.isClickable = false
        }
    }

    private fun randomizePositionOfCorrectAnswer(): Int {
        return Random(System.currentTimeMillis()).nextInt(0, 4)
    }

    companion object {
        const val TAG = "GameQuestionFragment"
        fun newInstance(): GameQuestionFragment = GameQuestionFragment()
    }
}