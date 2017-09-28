package com.standoff.bang.bang

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bang.*

/**
 * Created by kelvinhanma on 9/28/17.
 */
class BangActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bang)
        val network = (application as BangApplication).network
        network.getEvents().subscribe({ event ->
            println(event)
        })
        joinButton.setOnClickListener {
            network.connect()
            Toast.makeText(this, "Joining game", Toast.LENGTH_LONG).show()
        }
    }
}
