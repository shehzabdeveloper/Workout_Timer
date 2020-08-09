package com.shehzabahammad.workout_timer.home.screen.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shehzabahammad.workout_timer.R
import com.shehzabahammad.workout_timer.home.model.WorkoutSet
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

    private val workout: ArrayList<WorkoutSet> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onTimerFinished()

        fabPlay.setOnClickListener {
            if (flag == 0) dummy()
            progressBar.visibility=View.VISIBLE
            progressBar.isIndeterminate = true
            progressBar.indeterminateDrawable.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.MULTIPLY)
            currentState = TimerState.START_STATE
            tvTitle.text = workout[0].workoutTitle
            startTimer(workout[0])
            updateButtons()
        }
        fabPause.setOnClickListener {
            flag = 1
            currentState = TimerState.PAUSE_STATE
            timer.cancel()
            updateButtons()
        }
        fabStop.setOnClickListener {
            flag = 0
            workout.clear()
            progressBar.visibility=View.GONE
            currentState = TimerState.STOP_STATE
            timer.cancel()
            onTimerFinished()
            updateButtons()
        }
    }

    private fun startTimer(wrkSet: WorkoutSet) {
        timer = object : CountDownTimer(
            if (secondsRemaining * 1000 != 0L) secondsRemaining * 1000 else wrkSet.timer * 1000,
            1000
        ) {
            override fun onFinish() {
                workout.remove(wrkSet)
                if (workout.isNotEmpty()) {
//                    progressBar.progressTintList = ColorStateList.valueOf(Color.YELLOW)
                    progressBar.setBackgroundColor(Color.YELLOW)
                    tvTitle.text = workout[0].workoutTitle
                    startTimer(workout[0])
                } else {
                    onTimerFinished()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
//                secondsRemaining = millisUntilFinished / 1000
                secondsRemaining = (millisUntilFinished % 60000) / 1000
                updateCountdownUI(millisUntilFinished)
            }
        }.start()
    }

    private fun onTimerFinished() {
        currentState = TimerState.STOP_STATE
        tvTitle.text = getString(R.string.cool_down)
        progressBar.visibility=View.GONE
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

    private fun updateButtons() {
        when (currentState) {
            TimerState.START_STATE -> {
                fabPlay.isClickable = false
                fabPause.isClickable = true
                fabStop.isClickable = true
            }
            TimerState.PAUSE_STATE -> {
                fabPlay.isClickable = true
                fabPause.isClickable = false
                fabStop.isClickable = true
            }
            TimerState.STOP_STATE -> {
                fabPlay.isClickable = true
                fabPause.isClickable = false
                fabStop.isClickable = false

            }
        }
    }

    private fun appendZero(time: Long): String {
        val timeString = time.toString()
        return if (time < 10) "0$timeString" else timeString
    }
    private fun dummy() {
        workout.add(WorkoutSet("Pushup", 20))
        workout.add(WorkoutSet("Rest", 10))
        workout.add(WorkoutSet("Jump Jack", 20))
        workout.add(WorkoutSet("Rest", 10))
    }
}