package com.erdees.quizanga.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdees.quizanga.R
import com.erdees.quizanga.adapters.GamesHistoryRecyclerAdapter
import com.erdees.quizanga.viewModels.GamesHistoryViewModel

class GamesHistory: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.games_history_fragment,container,false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.games_history_rv)

        val viewModel = ViewModelProvider(this).get(GamesHistoryViewModel::class.java)

        viewModel.getGamesHistory().observe(viewLifecycleOwner,{ games ->
            val adapter = GamesHistoryRecyclerAdapter(games,requireContext())
            recyclerView.adapter = adapter
            recyclerView.layoutManager =  LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        })

        return view
    }

    companion object {
        const val TAG = "GamesHistory"
        fun newInstance() : GamesHistory = GamesHistory()
    }
}