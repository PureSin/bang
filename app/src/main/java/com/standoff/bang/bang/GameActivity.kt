package com.standoff.bang.bang;

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import com.standoff.bang.bang.moves.MovementTracker
import thirdparty.bindView
import android.os.CountDownTimer
import com.standoff.bang.bang.moves.GameAction


class GameActivity : Activity(){

    var fadeOut: Animation? = null

    val actionTextView: TextView? by bindView(R.id.action_text_view)
    val progressBar: ProgressBar? by bindView(R.id.timer)
    var action: GameAction? = null
    var timer: TimerWithProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.submit_action)
        fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        fadeOut!!.duration = 1500
        fadeOut!!.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                actionTextView!!.visibility = View.INVISIBLE
                startTimer()
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        MovementTracker.init()
        actionTextView!!.startAnimation(fadeOut)

        timer = TimerWithProgressBar(progressBar!!, this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = MovementTracker.processEvent(event!!)
        if (action != null) {
            actionTextView!!.visibility = View.VISIBLE
            actionTextView!!.text = "Action: " + action.name
        }
        return super.onTouchEvent(event)
    }

    fun startTimer() {
        timer!!.start()
    }

    fun resetTimer() {
        timer!!.reset()
    }

    fun playMove() {
        // send the action to server
    }
}

class TimerWithProgressBar(val bar: ProgressBar, val activity: GameActivity) {
    var i = 0
    val countdown = object : CountDownTimer(5000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            i++
            bar.progress = i * 100 / (5000 / 1000)
        }

        override fun onFinish() {
            i++
            bar.progress = 100
            activity.playMove()
        }
    }

    fun start() {
        countdown.start()
    }

    fun reset() {
        countdown.cancel()
    }
}