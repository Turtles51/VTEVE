package com.eve.pricewatcher.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.eve.pricewatcher.R
import com.google.android.material.tabs.TabLayout

class AddEditItemActivity : AppCompatActivity() {

    private lateinit var etItemName: AutoCompleteTextView
    private lateinit var etItemId: EditText
    private lateinit var etThreshold: EditText
    private lateinit var spinnerInterval: Spinner
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var tabOrderType: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        etItemName = findViewById(R.id.etItemName)
        etItemId = findViewById(R.id.etItemId)
        etThreshold = findViewById(R.id.etThreshold)
        spinnerInterval = findViewById(R.id.spinnerInterval)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
        tabOrderType = findViewById(R.id.tabOrderType)

        // Настройка интервалов
        val intervals = arrayOf("5 мин", "10 мин", "15 мин", "30 мин", "1 час", "2 часа", "6 часов")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, intervals)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInterval.adapter = adapter

        // Вкладки для типа ордера
        tabOrderType.addTab(tabOrderType.newTab().setText("Ордера на продажу"))
        tabOrderType.addTab(tabOrderType.newTab().setText("Ордера на покупку"))

        btnSave.setOnClickListener {
            // TODO: Сохранить товар
            finish()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}
