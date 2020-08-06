package com.shehzabahammad.workout_timer.home.screen.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shehzabahammad.workout_timer.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    enum class ButtonState {
        START_STATE, PAUSE_STATE, STOP_STATE
    }

    private lateinit var currentState: ButtonState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentState = ButtonState.START_STATE
        fabPlay.setOnClickListener {
            currentState = ButtonState.START_STATE
            updateButtons()
            Toast.makeText(context, "Clicked Play", Toast.LENGTH_SHORT).show()
        }
        fabPause.setOnClickListener {
            currentState = ButtonState.PAUSE_STATE
            updateButtons()
            Toast.makeText(context, "Clicked Pause", Toast.LENGTH_SHORT).show()
        }
        fabStop.setOnClickListener {
            currentState = ButtonState.STOP_STATE
            updateButtons()
            Toast.makeText(context, "Clicked Stop", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateButtons() {
        when (currentState) {
            ButtonState.START_STATE -> {
                fabPlay.isClickable = false
                fabPause.isClickable = true
                fabStop.isClickable = true
            }
            ButtonState.PAUSE_STATE -> {
                fabPlay.isClickable = true
                fabPause.isClickable = false
                fabStop.isClickable = true
            }
            ButtonState.STOP_STATE -> {
                fabPlay.isClickable = true
                fabPause.isClickable = false
                fabStop.isClickable = false

            }
        }
    }
}