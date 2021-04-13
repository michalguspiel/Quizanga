package com.erdees.quizanga.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.R
import java.util.zip.Inflater

class SetGamePlayerListAdapter(val activity: Activity,
                                val list: List<Player>):ArrayAdapter<Player>
    (activity, R.layout.item_set_game_player_list) {

    override fun getCount(): Int {
        return list.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
       val inflater = activity.layoutInflater
        val rowView = inflater.inflate(R.layout.item_set_game_player_list,parent,false)

        val nameTextField = rowView.findViewById<EditText>(R.id.item_set_game_name_edittext)

        nameTextField.addTextChangedListener((object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        }))

        return rowView
    }




}