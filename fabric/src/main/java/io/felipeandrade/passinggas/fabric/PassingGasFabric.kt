package io.felipeandrade.passinggas.fabric

import io.felipeandrade.passinggas.PassingGas
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Items

class PassingGasFabric : ModInitializer {
    override fun onInitialize() {
        PassingGas.init()
        PassingGas.LOG.info("PassingGas Fabric initialized")

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS)
            .register(ItemGroupEvents.ModifyEntries { entries: FabricItemGroupEntries ->
                entries.addAfter(
                    Items.BEETROOT_SEEDS,
                    PassingGas.CABBAGE_SEEDS.get()
                )
            })

//        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register { content ->
//            content.accept(PassingGas.FLATULENCE_POTION.get())
//            content.accept(PassingGas.LONG_FLATULENCE_POTION.get())
//        }

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
            .register(ItemGroupEvents.ModifyEntries { entries: FabricItemGroupEntries ->
                entries.addAfter(
                    Items.BEETROOT,
                    PassingGas.CABBAGE.get()
                )
                entries.addAfter(
                    Items.MILK_BUCKET,
                    PassingGas.MILK_BOTTLE.get()
                )
            })
    }
}
