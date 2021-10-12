package net.firstrateconcepts.fusionofsouls.service.duringRun

import com.badlogic.gdx.utils.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ktx.async.KtxAsync
import ktx.async.newSingleThreadAsyncContext
import net.firstrateconcepts.fusionofsouls.config.inject
import net.firstrateconcepts.fusionofsouls.util.framework.event.EventBus

@Suppress("LeakingThis")
abstract class RunService : Disposable {
    private val serviceContext = newSingleThreadAsyncContext("Service-Thread")
    abstract val eventBus: EventBus

    init {
        inject<RunServiceRegistry>().register(this)
    }

    fun start() {
        eventBus.registerHandlers(this)
        startInternal()
    }

    fun stop() {
        eventBus.unregisterHandlers(this)
        stopInternal()
    }

    fun runOnServiceThread(block: suspend CoroutineScope.() -> Unit) = KtxAsync.launch(serviceContext, block = block)

    protected open fun startInternal() = Unit
    protected open fun stopInternal() = Unit

    open fun frame(delta: Float) = Unit
    override fun dispose() = Unit
}