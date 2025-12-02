package io.felipeandrade.passinggas

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import io.felipeandrade.passinggas.block.CabbageCropBlock
import io.felipeandrade.passinggas.effect.FlatulenceEffect
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.component.Consumable
import net.minecraft.world.item.component.Consumables
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.PushReaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object PassingGas {
    const val MOD_ID: String = "passinggas"
    const val MOD_NAME: String = "Passing Gas"
    val LOG: Logger = LoggerFactory.getLogger(MOD_NAME)

    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(MOD_ID, Registries.ITEM)
    val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(MOD_ID, Registries.BLOCK)
    val SOUND_EVENTS: DeferredRegister<SoundEvent> = DeferredRegister.create(MOD_ID, Registries.SOUND_EVENT)
    val MOB_EFFECTS: DeferredRegister<MobEffect> = DeferredRegister.create(MOD_ID, Registries.MOB_EFFECT)
    val POTIONS: DeferredRegister<Potion> = DeferredRegister.create(MOD_ID, Registries.POTION)

    fun init() {
        SOUND_EVENTS.register()
        MOB_EFFECTS.register()
        BLOCKS.register()
        ITEMS.register()
        POTIONS.register()
    }

    val MILK_CONSUMABLE: Consumable = Consumables.defaultDrink()
        .onConsume(ClearAllStatusEffectsConsumeEffect.INSTANCE)
        .build()

    val MILK_BOTTLE: RegistrySupplier<Item> = ITEMS.register("milk_bottle") {
        Item(Item.Properties().setId(resourceKey("milk_bottle", Registries.ITEM))
            .craftRemainder(Items.GLASS_BOTTLE)
            .component(DataComponents.CONSUMABLE, MILK_CONSUMABLE)
            .usingConvertsTo(Items.GLASS_BOTTLE)
            .stacksTo(16))
    }

    val FART_SOUND_EVENT: RegistrySupplier<SoundEvent> = SOUND_EVENTS.register("fart") {
        SoundEvent.createVariableRangeEvent(asResource("fart"))
    }

    val FLATULENCE_EFFECT: RegistrySupplier<MobEffect> = MOB_EFFECTS.register("flatulence") {
        FlatulenceEffect()
    }

    val CABBAGE_FOOD: FoodProperties = FoodProperties.Builder()
        .nutrition(3).saturationModifier(0.6f).build()

    val CABBAGE: RegistrySupplier<Item> = ITEMS.register("cabbage") {
        val consumable = Consumables.defaultFood()
            .onConsume(
                ApplyStatusEffectsConsumeEffect(
                    MobEffectInstance(Holder.direct(FLATULENCE_EFFECT.get()), 600, 0), 1.0F
                )
            )
            .build()
        Item(Item.Properties().setId(resourceKey("cabbage", Registries.ITEM))
            .food(CABBAGE_FOOD, consumable))
    }

    val CABBAGE_CROP: RegistrySupplier<Block> = BLOCKS.register("cabbage_crop_block") {
        CabbageCropBlock(BlockBehaviour.Properties.of()
            .setId(resourceKey("cabbage_crop_block", Registries.BLOCK))
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY))
    }

    val CABBAGE_SEEDS: RegistrySupplier<Item> = ITEMS.register("cabbage_seeds") {
        BlockItem(CABBAGE_CROP.get(), Item.Properties()
            .setId(resourceKey("cabbage_seeds", Registries.ITEM))
            .useItemDescriptionPrefix())
    }

    val FLATULENCE_POTION: RegistrySupplier<Potion> = POTIONS.register("flatulence_potion") {
        Potion("flatulence_potion", MobEffectInstance(Holder.direct(FLATULENCE_EFFECT.get()), 3600))
    }

    val LONG_FLATULENCE_POTION: RegistrySupplier<Potion> = POTIONS.register("long_flatulence_potion") {
        Potion("long_flatulence_potion", MobEffectInstance(Holder.direct(FLATULENCE_EFFECT.get()), 9600))
    }

    fun asResource(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)

    fun <T> resourceKey(path: String, key: ResourceKey<Registry<T>>) =
        ResourceKey.create(key, asResource(path))
}
