package com.standoff.bang.bang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import thirdparty.bindView

class StartGameActivity: Activity() {

    val startBtn:Button? by bindView(R.id.start_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.init_screen)

        startBtn!!.setOnClickListener { onClick() }
    }

    fun onClick() {
        val i = Intent(this, WaitForPlayersActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onBackPressed() {
        // do nothing
    }
}