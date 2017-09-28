package com.standoff.bang.bang;

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.standoff.bang.bang.moves.MovementTracker
import thirdparty.bindView

/**
 * Created by gavra on 9/28/17.
 */

class GameActivity : Activity(){

    var fadeOut: Animation? = null

    val actionTextView: TextView? by bindView(R.id.action_text_view)

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
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        MovementTracker.init()
        actionTextView!!.startAnimation(fadeOut)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = MovementTracker.processEvent(event!!)
        if (action != null) {
            actionTextView!!.visibility = View.VISIBLE
            actionTextView!!.text = "Action: " + action.name
        }
        return super.onTouchEvent(event)
    }

    fun startCountdown() {

    }

    fun endCountdown() {

    }
}
