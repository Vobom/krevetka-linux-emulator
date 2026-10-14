package com.krevetka.linux

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    // This prototype stores state only while the app process is alive.
    private val demoInstalled = mutableSetOf<String>()
    private var activeDistro: Distro? = null

    private val background = Color.rgb(13, 16, 25)
    private val cardColor = Color.rgb(27, 33, 48)
    private val accent = Color.rgb(123, 164, 255)
    private val muted = Color.rgb(174, 184, 204)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHome()
    }

    private fun text(value: String, size: Float, color: Int = Color.WHITE, bold: Boolean = false) =
        TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(typeface, Typeface.BOLD)
            setPadding(0, 4, 0, 4)
        }

    private fun button(value: String, onClick: () -> Unit) =
        Button(this).apply {
            text = value
            isAllCaps = false
            setOnClickListener { onClick() }
        }

    private fun column(padding: Int = 22) = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(padding, padding, padding, padding)
        setBackgroundColor(background)
    }

    private fun showHome() {
        activeDistro = null
        val page = column()
        page.addView(text("krevetka", 32f, Color.WHITE, true))
        page.addView(text("LINUX EMULATOR", 13f, accent, true))
        page.addView(text("Твой Linux — в Android", 23f, Color.WHITE, true).apply {
            setPadding(0, 22, 0, 2)
        })
        page.addView(text(
            "Выбери дистрибутив. Это ранний прототип меню: установщик Linux пока не подключён.",
            14f, muted
        ).apply { setPadding(0, 0, 0, 18) })

        val cards = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        Distro.values().forEach { distro ->
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 14, 16, 10)
                setBackgroundColor(cardColor)
            }
            card.addView(text("${distro.icon}  ${distro.displayName}", 21f, Color.WHITE, true))
            card.addView(text(distro.description, 14f, muted))
            card.addView(text("Пакеты: ${distro.packageManager}  •  Рабочий стол: ${distro.desktop}", 12f, accent))
            if (distro.id in demoInstalled) {
                card.addView(button("Открыть экран дистрибутива") { showDistro(distro) })
                card.addView(button("Сбросить демо-состояние") {
                    demoInstalled.remove(distro.id)
                    showHome()
                })
            } else {
                card.addView(button("Подробнее") { showDistro(distro) })
                card.addView(button("Установить (демо)") {
                    demoInstalled.add(distro.id)
                    Toast.makeText(this, "Это только демо. Настоящая установка появится позже.", Toast.LENGTH_LONG).show()
                    showHome()
                })
            }
            val params = LinearLayout.LayoutParams(-1, -2)
            params.bottomMargin = 14
            cards.addView(card, params)
        }

        val scroll = ScrollView(this)
        scroll.addView(cards)
        page.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        page.addView(text("v0.1.0 • Интерфейсный прототип", 12f, muted))
        setContentView(page)
    }

    private fun showDistro(distro: Distro) {
        activeDistro = distro
        val page = column()
        page.addView(text("←  ${distro.displayName}", 28f, Color.WHITE, true))
        page.addView(text("Окружение Linux", 14f, accent, true))
        page.addView(text(
            "\\nПланируемая конфигурация\\n\\n" +
                "• Менеджер пакетов: ${distro.packageManager}\\n" +
                "• Рабочий стол: ${distro.desktop}\\n" +
                "• Изолированные файлы дистрибутива\\n" +
                "• Возврат в меню без удаления файлов\\n\\n" +
                "Статус: PRoot, rootfs и графический сервер ещё не подключены.",
            16f, muted
        ))
        page.addView(button("Запуск Linux (скоро)") {
            Toast.makeText(this, "Запуск станет доступен после интеграции PRoot.", Toast.LENGTH_LONG).show()
        })
        page.addView(button("← Главное меню") { showHome() })
        setContentView(page)
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (activeDistro != null) showHome() else super.onBackPressed()
    }
}
