package io.felipeandrade.passinggas.fabric

import io.felipeandrade.passinggas.PassingGas
import io.felipeandrade.passinggas.PassingGas.FLATULENCE_POTION
import io.felipeandrade.passinggas.PassingGas.LONG_FLATULENCE_POTION
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Holder
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionContents

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


                val potionStack = ItemStack(Items.POTION)
                potionStack.set(DataComponents.POTION_CONTENTS, PotionContents(Holder.direct(FLATULENCE_POTION.get())))
                val splashPotionStack = ItemStack(Items.SPLASH_POTION)
                potionStack.set(DataComponents.POTION_CONTENTS, PotionContents(Holder.direct(LONG_FLATULENCE_POTION.get())))
                val lingeringPotionStack = ItemStack(Items.LINGERING_POTION)
                potionStack.set(DataComponents.POTION_CONTENTS, PotionContents(Holder.direct(LONG_FLATULENCE_POTION.get())))

                entries.addAfter(
                    Items.POTION,
                    potionStack, splashPotionStack, lingeringPotionStack
                )
            })
    }
}
