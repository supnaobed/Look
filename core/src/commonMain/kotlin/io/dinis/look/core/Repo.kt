package io.dinis.look.core


import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random


val json = defaultSerializer()

/**
 * Created by Dinis Ishmukhametov on 19,May,2020
 * dinis.ish@gmail.com
 * Kazan, RUSSIA.
 */

class Repo(storage: Storage) {

    var api: Api = Api(client(storage))

    @ExperimentalCoroutinesApi
    private val retryChannel = BroadcastChannel<Unit>(10)

    fun screenState(result: (ScreenState) -> Unit){
        GlobalScope.launch {
            observeState().collect {
                result(it)
            }
        }
    }

    public fun retry() {
        GlobalScope.launch {
            retryChannel.send(Unit)
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun observeState() : Flow<ScreenState> = flow {
        val device = flow<ScreenState> {
            ping()
            emit(ScreenShowTitle(getProfile()!!))
        }.catch {
            val code = generateCode()
            emit(ScreenLoading())
            emit(ScreenShowCode(code))
            emit(newDevice(code))
        }
        emitAll(device)
        emitAll(retryChannel.asFlow().flatMapLatest { flow { emitAll(device) }})
    }

    private suspend fun newDevice(code: String) : ScreenState {
        try {
            sendCode(code)
            getProfile()?.let {
                return ScreenShowTitle(it)
            }
            return ScreenError("Some error")
        }catch (e: Exception){
            return ScreenError(e.message ?: "Some error")
        }
    }

    private suspend fun getProfile() : String? {
        val result = api.profile()
        return result.result?.title
    }

    private fun generateCode(): String {
        return Random.nextInt(99999, 999999).toString()
    }

    private suspend fun sendCode(code: String) {
        while(true) {
            val result = api.sendCode(code)
            val success = result.status.value == 200
            if (!success) {
                delay(1000 * 10)
            }else{
                return
            }
        }
    }

    private suspend fun ping() : String {
        return api.ping()
    }

}



