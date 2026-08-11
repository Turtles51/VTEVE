package com.eve.pricewatcher.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eve.pricewatcher.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var tvCacheSize: TextView
    private lateinit var btnClearCache: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tvCacheSize = findViewById(R.id.tvCacheSize)
        btnClearCache = findViewById(R.id.btnClearCache)
        btnBack = findViewById(R.id.btnBack)

        // TODO: Показать размер кэша

        btnClearCache.setOnClickListener {
            // TODO: Очистить кэш с подтверждением
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
