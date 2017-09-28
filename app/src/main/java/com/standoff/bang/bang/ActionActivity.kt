package com.standoff.bang.bang

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.support.v4.view.MotionEventCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

import com.standoff.bang.bang.moves.MovementTracker

import java.util.ArrayList

import timber.log.Timber
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import com.standoff.bang.bang.moves.GameAction
import thirdparty.bindView

class ActionActivity : Activity() {

    val actionView: TextView by bindView(R.id.action)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.action_activity)
        MovementTracker.init()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = MovementTracker.processEvent(event)
        if (action != null) {
            Timber.d("Selected action: " + action.name)
            updateAction(action)
        }
        return super.onTouchEvent(event)
    }

    fun updateAction(action: GameAction) {
        actionView!!.setText(action.name)
    }
}