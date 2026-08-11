package com.eve.pricewatcher

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var rvItems: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var btnHistory: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItems = findViewById(R.id.rvItems)
        tabLayout = findViewById(R.id.tabLayout)
        fabAdd = findViewById(R.id.fabAdd)
        btnHistory = findViewById(R.id.btnHistory)

        rvItems.layoutManager = LinearLayoutManager(this)

        // Добавляем вкладки
        tabLayout.addTab(tabLayout.newTab().setText("Ордера на продажу"))
        tabLayout.addTab(tabLayout.newTab().setText("Ордера на покупку"))

        // TODO: Здесь будет логика загрузки списка товаров

        fabAdd.setOnClickListener {
            // TODO: Открыть экран добавления товара
        }

        btnHistory.setOnClickListener {
            // TODO: Открыть экран истории
        }
    }
}
