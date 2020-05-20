package io.dinis.look.core


/**
 * Created by Dinis Ishmukhametov on 20,May,2020
 * dinis.ish@gmail.com
 * Kazan, RUSSIA.
 */
sealed class ScreenState

 class ScreenLoading: ScreenState()

data class ScreenError(val message: String): ScreenState()

data class ScreenShowTitle(val title: String): ScreenState()

data class ScreenShowCode(val code: String): ScreenState()
