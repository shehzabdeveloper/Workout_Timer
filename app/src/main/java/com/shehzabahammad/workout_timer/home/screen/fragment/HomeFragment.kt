package com.shehzabahammad.workout_timer.home.screen.fragment

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shehzabahammad.workout_timer.R
import com.shehzabahammad.workout_timer.home.model.RoutineSet
import com.shehzabahammad.workout_timer.util.Util.appendZero
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    enum class TimerState {
        START_STATE, PAUSE_STATE, STOP_STATE
    }

    private lateinit var timer: CountDownTimer

    private var timerLengthSeconds = 0L
    private var currentState = TimerState.STOP_STATE
    private var secondsRemaining = 0L
    private var flag = 0
    private var workoutRound = 0

    private val workout: ArrayList<RoutineSet> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvRound.text = getString(R.string.round, workoutRound)
        onTimerFinished()

        fabPlay.setOnClickListener {
            if (flag == 0) dummy()
            progressBar.visibility = View.VISIBLE

            currentState = TimerState.START_STATE
            startTimer(workout[0])
            updateButtons()
        }
        fabPause.setOnClickListener {
            flag = 1
            workoutRound--
            currentState = TimerState.PAUSE_STATE
            timer.cancel()
            updateButtons()
        }
        fabStop.setOnClickListener {
            flag = 0
            workoutRound = 0
            tvRound.text = getString(R.string.round, workoutRound)
            workout.clear()
            progressBar.visibility = View.GONE
            currentState = TimerState.STOP_STATE
            timer.cancel()
            onTimerFinished()
            updateButtons()
        }
    }

    private fun startTimer(routineSet: RoutineSet) {
        tvTitle.text = routineSet.workoutTitle
        if (routineSet.workoutTitle == "Rest") {
            progressBar.indeterminateDrawable.colorFilter = PorterDuffColorFilter(
                Color.RED,
                PorterDuff.Mode.SRC_ATOP
            )
        } else {
            workoutRound++
            tvRound.text = getString(R.string.round, workoutRound)
            progressBar.indeterminateDrawable.colorFilter = PorterDuffColorFilter(
                Color.GREEN,
                PorterDuff.Mode.SRC_ATOP
            )
        }

        timer = object : CountDownTimer(
            if (secondsRemaining * 1000 != 0L) secondsRemaining * 1000 else routineSet.timer * 1000,
            1000
        ) {
            override fun onFinish() {
                workout.remove(routineSet)
                if (workout.isNotEmpty()) {
                    startTimer(workout[0])
                } else {
                    onTimerFinished()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = (millisUntilFinished % 60000) / 1000
                updateCountdownUI(millisUntilFinished)
            }
        }.start()
    }

    private fun onTimerFinished() {
        currentState = TimerState.STOP_STATE
        tvTitle.text = getString(R.string.cool_down)
        workoutRound = 0
        tvRound.text = getString(R.string.round, workoutRound)
        progressBar.visibility = View.GONE
        //set the length of the timer to be the one set in SettingsActivity
        //if the length was changed when the timer was running
        setNewTimerLength()
//        progressBar.progress = 0
//        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI(0)
    }

    private fun setNewTimerLength() {
        val lengthInMinutes = 0
        timerLengthSeconds = (lengthInMinutes.toLong())
//        progressBar.max = timerLengthSeconds.toInt()
    }

    @SuppressLint("SetTextI18n")
    private fun updateCountdownUI(millisUntilFinished: Long) {
        val minutesRemaining = millisUntilFinished / 60000
        val secondsRemaining = (millisUntilFinished % 60000) / 1000
        val minutes = appendZero(minutesRemaining)
        val seconds = appendZero(secondsRemaining)
        val timerText = "${minutes}:${seconds}"
        tvCountDown.text = timerText
    }

    @SuppressLint("RestrictedApi")
    private fun updateButtons() {
        when (currentState) {
            TimerState.START_STATE -> {
                fabPlay.visibility = View.GONE
                fabPause.visibility = View.VISIBLE
                fabPlay.isClickable = false
                fabPause.isClickable = true
                fabStop.isClickable = true
            }
            TimerState.PAUSE_STATE -> {
                fabPlay.visibility = View.VISIBLE
                fabPause.visibility = View.GONE
                fabPlay.isClickable = true
                fabPause.isClickable = false
                fabStop.isClickable = true
            }
            TimerState.STOP_STATE -> {
                fabPlay.visibility = View.VISIBLE
                fabPause.visibility = View.GONE
                fabPlay.isClickable = true
                fabPause.isClickable = false
                fabStop.isClickable = false

            }
        }
    }

    private fun dummy() {
        workout.add(RoutineSet("Push Ups", 21))
        workout.add(RoutineSet("Rest", 11))
        workout.add(RoutineSet("Jump Jack", 21))
        workout.add(RoutineSet("Rest", 11))
    }
}