package com.mst

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object MoreSwordType : ModInitializer {
    private val logger = LoggerFactory.getLogger("more-sword-type")
    const val MOD_ID = "more-sword-type"

    override fun onInitialize() {
        ModItems.initialize()
        logger.info("More Sword Type initialized!")
    }
}
