package com.example.testappkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.support.v4.os.HandlerCompat.postDelayed
import android.content.DialogInterface
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.os.Build




class MainActivity : AppCompatActivity() {

    internal lateinit var tap_me_button: Button
    internal lateinit var your_score: TextView
    internal lateinit var time_left: TextView
    internal var score = 0
    internal var gameStarted = false
    internal lateinit var count_down_timer: CountDownTimer
    internal val initial_cout_down: Long = 6000
    internal val count_down_interval: Long = 1000
    internal val TAG = MainActivity::class.java.simpleName

    companion object {
        private val score_key = "score_key"
        private val time_left_key = "time_left_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate called. Score is: $score")

        tap_me_button = findViewById<Button>(R.id.tap_me_button)
        your_score = findViewById<TextView>(R.id.your_score)
        time_left = findViewById<TextView>(R.id.time_left)
        reset_game()
        //your_score.text = getString(R.string.your_score,score.toString())

        tap_me_button.setOnClickListener { View ->

            incrimentScore()

        }
    }

    private fun reset_game() {
        score = 0
        your_score.text = getString(R.string.your_score, score.toString())
        val initial_time_left = initial_cout_down / 1000
        time_left.text = getString(R.string.time_left, initial_time_left.toString())

        count_down_timer = object : CountDownTimer(initial_cout_down, count_down_interval) {
            override fun onTick(millisUntilFinished: Long) {
                //timeLeftOnTimer
                var timeLeft = millisUntilFinished / 1000
                time_left.text = getString(R.string.time_left, timeLeft.toString())

            }

            override fun onFinish() {
                endGame()


            }
        }

        gameStarted = false

    }

    private fun startGame() {
        count_down_timer.start()


        gameStarted = true
    }

    private fun endGame() {

        //Toast.makeText(this,"Your Score Is: " +score.toString(),Toast.LENGTH_LONG).show()
        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
        } else {
            builder = AlertDialog.Builder(this)
        }
        builder.setTitle("Delete entry")
            .setMessage("Are you sure you want to delete this entry?")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                //tap_me_button.setOnClickListener(View.OnClickListener {
                    tap_me_button.setEnabled(false)
                    tap_me_button.postDelayed(Runnable {
                        tap_me_button.setEnabled(true)
                        Log.d(TAG, "resend1")
                    }, 3000)
                //})
                var mToast: Toast? = null
                val timer = object : CountDownTimer(3000, 1000) {

                    override fun onTick(millisUntilFinished: Long) {
                        if (mToast != null) mToast!!.cancel()
                        mToast = Toast.makeText(
                            applicationContext,
                            "Game Will Start In : " + millisUntilFinished / 1000,
                            Toast.LENGTH_SHORT
                        )
                        mToast!!.show()
                    }

                    override fun onFinish() {

                    }
                }.start()
            }
            .setNegativeButton(android.R.string.no) { dialog, which ->
                // do nothing
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

        reset_game()



    }

    private fun incrimentScore() {
        if (!gameStarted) {
            startGame()
        }
        score = score + 1
        var newScore = getString(R.string.your_score, score.toString())
        your_score.text = newScore
    }

}
//chethana
