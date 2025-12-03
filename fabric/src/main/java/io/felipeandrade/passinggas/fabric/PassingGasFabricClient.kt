package io.felipeandrade.passinggas.fabric

import dev.architectury.registry.client.level.entity.EntityRendererRegistry
import dev.architectury.registry.client.rendering.RenderTypeRegistry
import io.felipeandrade.passinggas.PassingGas
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.renderer.chunk.ChunkSectionLayer
import net.minecraft.client.renderer.entity.ThrownItemRenderer

class PassingGasFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, PassingGas.CABBAGE_CROP.get())
        EntityRendererRegistry.register(PassingGas.POTION_ENTITY_OF_CLEANSING, ::ThrownItemRenderer)
    }
}
