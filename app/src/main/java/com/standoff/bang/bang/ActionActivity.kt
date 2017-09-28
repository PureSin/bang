package com.standoff.bang.bang

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import com.standoff.bang.bang.moves.GameAction
import com.standoff.bang.bang.moves.MovementTracker
import thirdparty.bindView
import timber.log.Timber

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