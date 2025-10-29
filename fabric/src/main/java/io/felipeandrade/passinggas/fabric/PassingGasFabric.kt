package io.felipeandrade.passinggas.fabric

import io.felipeandrade.passinggas.PassingGas
import net.fabricmc.api.ModInitializer

class PassingGasFabric : ModInitializer {
    override fun onInitialize() {
        PassingGas.LOG.info("PassingGas Fabric initialized")

//        Registry.register(Registries.ITEM, Identifier(MOD_ID, "splash_potion_cleansing"), SPLASH_POTION_OF_CLEANSING)
//        Registry.register(Registries.POTION, Identifier(MOD_ID, "flatulence"), FlatulencePotion.FLATULENCE_POTION)
//        Registry.register(
//            Registries.POTION,
//            Identifier(MOD_ID, "long_flatulence"),
//            FlatulencePotion.LONG_FLATULENCE_POTION
//        )
//        BrewingRecipeRegistryMixin.register(Potions.AWKWARD.value(), PassingGas.CABBAGE, FlatulencePotion.FLATULENCE_POTION)
//        BrewingRecipeRegistryMixin.register(
//            FlatulencePotion.FLATULENCE_POTION,
//            Items.REDSTONE,
//            FlatulencePotion.LONG_FLATULENCE_POTION
//        )

//        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(PassingGas.CABBAGE_SEEDS, 0.3f)
//        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(PassingGas.CABBAGE, 0.65f)
//
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register({ content ->
//            content.add(PassingGas.CABBAGE_SEEDS)
//        })
//
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register({ content ->
//            content.add(PassingGas.CABBAGE)
//            content.add(PassingGas.MILK_BOTTLE)
//            content.add(PassingGas.SPLASH_POTION_OF_CLEANSING)
//        })
    }
}
