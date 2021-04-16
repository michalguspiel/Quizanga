package com.erdees.quizanga.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.erdees.quizanga.dao.GameStateDao
import com.erdees.quizanga.dao.PlayerDao
import com.erdees.quizanga.dao.StateAndPlayersDao
import com.erdees.quizanga.database.converter.Converter
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player

@Database(entities = [
 GameState::class,
 Player::class,
],version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppRoomDatabase: RoomDatabase() {


 abstract fun gameStateDao() : GameStateDao
 abstract fun playerDao() : PlayerDao
 abstract fun stateAndPlayersDao() : StateAndPlayersDao

 companion object{
  @Volatile
  private var INSTANCE: AppRoomDatabase? = null
  fun getDatabase(context: Context): AppRoomDatabase{
   val tempInstance = INSTANCE
   if(tempInstance != null){
    return tempInstance
   }
   synchronized(this){
    val instance = Room.databaseBuilder(
     context.applicationContext,
     AppRoomDatabase::class.java,
     "app_room_database"
    ).build()
    INSTANCE = instance
    return instance
   }
  }
 }

}
