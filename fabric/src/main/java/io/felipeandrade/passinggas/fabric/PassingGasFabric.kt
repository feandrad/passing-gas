package io.felipeandrade.passinggas.fabric

import io.felipeandrade.passinggas.PassingGas
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.world.item.CreativeModeTabs

class PassingGasFabric : ModInitializer {
    override fun onInitialize() {
        PassingGas.init()
        PassingGas.LOG.info("PassingGas Fabric initialized")

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register { content ->
            content.accept(PassingGas.CABBAGE_SEEDS.get())
        }

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register { content ->
            content.accept(PassingGas.CABBAGE.get())
            content.accept(PassingGas.MILK_BOTTLE.get())
        }
    }
}
