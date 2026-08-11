package com.eve.pricewatcher.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eve.pricewatcher.R

class HistoryActivity : AppCompatActivity() {

    private lateinit var rvHistory: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var btnClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        rvHistory = findViewById(R.id.rvHistory)
        tvEmpty = findViewById(R.id.tvEmpty)
        btnClear = findViewById(R.id.btnClear)

        rvHistory.layoutManager = LinearLayoutManager(this)

        // TODO: Загрузить историю из базы данных

        btnClear.setOnClickListener {
            // TODO: Очистить историю с подтверждением
        }
    }
}
