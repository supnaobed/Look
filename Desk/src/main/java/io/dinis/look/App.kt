package io.dinis.look

import io.dinis.look.core.Repo
import io.dinis.look.core.Storage
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.stage.Stage


/**
 * Created by Dinis Ishmukhametov on 21,May,2020
 * dinis.ish@gmail.com
 * Kazan, RUSSIA.
 */
class App: Application() {
    override fun start(primaryStage: Stage) {
        val borderPane = BorderPane()
        val label = Label("Loading..")
        borderPane.center = label
        borderPane.prefHeight = 200.0
        borderPane.prefWidth = 200.0
        val scene = Scene(borderPane)
        primaryStage.setScene(scene);
        primaryStage.show()
        Repo(simpleStorage).screenState {
            Platform.runLater { label.text = it.toString() }
        }
    }

    val simpleStorage = object : Storage {

        val storage = HashMap<String, ArrayList<String>>()

        override fun remove(key: String) {
            storage.remove(key)
        }

        override fun get(key: String): List<String> {
            var result = storage[key]
            return result ?: emptyList()
        }

        override fun add(key: String, value: String) {
            val arrayList = ArrayList(get(key))
            arrayList.add(value)
            storage[key] = arrayList
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}