package io.dinis.look

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import io.dinis.look.core.Repo
import io.dinis.look.core.ScreenError
import io.dinis.look.core.Storage
import io.dinis.look.core.createApplicationScreenMessage
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("look", Context.MODE_PRIVATE)
        val repo = Repo(storage = simpleStorage)
        val btn = findViewById<Button>(R.id.btn)
        btn.visibility = View.GONE
        repo.screenState(result = {
            runOnUiThread {
                findViewById<TextView>(R.id.text_view).text = it.toString()
                if (it is ScreenError) {
                    btn.visibility = View.VISIBLE
                } else {
                    btn.visibility = View.GONE
                }
            }
        })
        btn.setOnClickListener {
            repo.retry()
        }
    }

    lateinit var sharedPreferences: SharedPreferences

    val simpleStorage = object : Storage {

        val storage = HashMap<String, ArrayList<String>>()

        override fun remove(key: String) {
            storage.remove(key)
            sharedPreferences.edit().remove(key).apply()
        }

        override fun get(key: String): List<String> {
            var result = storage[key]
            if (result == null) {
                val set = sharedPreferences.getStringSet(key, emptySet())
                result = ArrayList(set)
                storage[key] = result
            }
            return result
        }

        override fun add(key: String, value: String) {
            val arrayList = ArrayList(get(key))
            arrayList.add(value)
            storage[key] = arrayList
            sharedPreferences.edit().putStringSet(key, arrayList.toSet()).apply()
        }
    }
}


