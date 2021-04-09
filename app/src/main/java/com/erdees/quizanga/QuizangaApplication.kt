package com.erdees.quizanga



class QuizangaApplication {

    private object NoActionScreenCallback : (Screen) -> Unit {
        override fun invoke(screen: Screen) {}
    }

    private var screenCallback : (Screen) -> Unit = NoActionScreenCallback

    lateinit var game :  Game
    lateinit var screen : Screen


    fun startGame(){
        screen = SetGameScreen()
        this.screenCallback(screen)
    }

    fun open() {
    game = Game()
    }

    fun setInitialScreen(){
        screen = if(!game.hasStarted) WelcomeScreen()
        else GameScreen()
    }


    fun withScreenCallback(callback: (Screen) -> Unit ){
        this.screenCallback = callback
        updateScreen(screen)
    }

    private fun updateScreen(screen: Screen) {
        this.screen = screen
        screenCallback(this.screen)

    }

}




