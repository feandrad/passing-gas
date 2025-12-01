package io.felipeandrade.passinggas.fabric

import dev.architectury.registry.client.rendering.RenderTypeRegistry
import io.felipeandrade.passinggas.PassingGas
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.renderer.chunk.ChunkSectionLayer

class PassingGasFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        RenderTypeRegistry.register(ChunkSectionLayer.CUTOUT, PassingGas.CABBAGE_CROP.get())
    }
}
