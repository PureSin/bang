package com.standoff.bang.bang;

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
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
import com.standoff.bang.bang.model.Action
import com.standoff.bang.bang.model.actionChosen
import com.standoff.bang.bang.model.roundResult
import com.standoff.bang.bang.model.startGame
import com.standoff.bang.bang.moves.GameAction


class GameActivity : Activity(){

    var fadeOut: Animation? = null

    val actionTextView: TextView? by bindView(R.id.action_text_view)
    val lives: TextView? by bindView(R.id.lives)
    val bullets: TextView? by bindView(R.id.bullets)
    val progressBar: ProgressBar? by bindView(R.id.timer)
    var action: GameAction? = null
    var timer: TimerWithProgressBar? = null

    var app:BangApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as BangApplication

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

    fun listenForResult() {
        app!!.network.getEvents().subscribe { event ->
            when(event) {
                is roundResult -> showResult(event)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        action = MovementTracker.processEvent(event!!)
        if (action != null) {
            actionTextView!!.visibility = View.VISIBLE
            actionTextView!!.text = "Action: " + action!!.name

            playMove()
        }
        return super.onTouchEvent(event)
    }

    fun startTimer() {
        timer!!.start()
    }


    fun playMove() {
        if (action != null) {
            val finalAction = if (action == GameAction.SHOOT) {
                Action.SHOOT
            } else if (action == GameAction.BLOCK){
                Action.DEFEND
            } else {
                Action.RELOAD
            }

            app!!.network.emit(actionChosen(app!!.game!!.player.name, finalAction))
        }
    }

    fun showResult(result: roundResult) {
        val outcome =
                if (result.oppAction == Action.SHOOT && result.yourAction != Action.DEFEND) {
            "You got shot."
        } else if (result.yourAction == Action.SHOOT && result.oppAction != Action.DEFEND) {
            "You shot your opponent."
        } else {
            "Everyone are alive :("
        }

        var  print = ""
        print += outcome + "\n"
        print += "Lives remaining: " + app!!.game!!.player.lives + "\n"
        print += "Bullets remaining: " + app!!.game!!.player.bullets + "\n"



        actionTextView!!.text = print
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
//            activity.playMove()
        }
    }

    fun start() {
        countdown.start()
    }
}