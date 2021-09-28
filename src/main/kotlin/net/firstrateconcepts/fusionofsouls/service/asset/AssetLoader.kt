package net.firstrateconcepts.fusionofsouls.service.asset

import com.badlogic.gdx.utils.Disposable
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.async.KtxAsync
import net.firstrateconcepts.fusionofsouls.config.AssetConfig
import net.firstrateconcepts.fusionofsouls.event.EventBus
import net.firstrateconcepts.fusionofsouls.model.event.AssetsLoadedEvent
import net.firstrateconcepts.fusionofsouls.util.ext.fosLogger

class AssetLoader(private val unitAssets: UnitAssets, private val eventBus: EventBus, private val assetConfig: AssetConfig) : Disposable {
    private val logger = fosLogger()

    fun load() = KtxAsync.launch(assetConfig.asyncContext) {
        logger.info { "Loading assets" }
        unitAssets.loadAll().joinAll()
        logger.info { "Asset loading complete" }
        eventBus.postEvent(AssetsLoadedEvent())
    }

    override fun dispose() {
        logger.info { "Disposing" }
        assetConfig.dispose()
    }
}
