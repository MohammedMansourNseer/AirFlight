package dev.trip.airflight.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.trip.airflight.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}