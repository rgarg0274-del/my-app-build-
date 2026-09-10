package com.tickclock.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var circleButton: android.view.View

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            // Whether granted or not, we still start the service.
            // Without the permission the foreground notification just won't be shown,
            // but ticking will still continue.
            startTicking()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        circleButton = findViewById(R.id.circleButton)
        circleButton.setOnClickListener {
            if (TickService.isRunning) {
                stopTicking()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(
                        this, Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    startTicking()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Reflect the real current state in case the service was already
        // running (or was stopped) while this Activity was not on screen.
        updateButtonAppearance()
    }

    private fun startTicking() {
        val intent = Intent(this, TickService::class.java)
        ContextCompat.startForegroundService(this, intent)
        updateButtonAppearance()
    }

    private fun stopTicking() {
        val intent = Intent(this, TickService::class.java)
        stopService(intent)
        updateButtonAppearance()
    }

    private fun updateButtonAppearance() {
        circleButton.setBackgroundResource(
            if (TickService.isRunning) R.drawable.circle_grey else R.drawable.circle_white
        )
    }
}
