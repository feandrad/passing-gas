package io.felipeandrade.passinggas

import io.felipeandrade.passinggas.block.CabbageCropBlock
import io.felipeandrade.passinggas.effect.FlatulenceEffect
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.Registry.register
import net.minecraft.core.Registry.registerForHolder
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries.*
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.resources.ResourceLocation.withDefaultNamespace
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvent.createVariableRangeEvent
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.Items.registerItem
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
import java.util.function.Function

object PassingGas {
    const val MOD_ID: String = "passinggas"
    const val MOD_NAME: String = "Passing Gas"
    val LOG: Logger = LoggerFactory.getLogger(MOD_NAME)

    val MILK_CONSUMABLE: Consumable = Consumables.defaultDrink()
        .onConsume(ClearAllStatusEffectsConsumeEffect.INSTANCE)
        .build()

    val MILK_BOTTLE: Item = registerItem(
        "milk_bottle",
        Item.Properties().craftRemainder(Items.GLASS_BOTTLE)
            .component(DataComponents.CONSUMABLE, MILK_CONSUMABLE)
            .usingConvertsTo(Items.GLASS_BOTTLE)
            .stacksTo(16)
    )

    val FART_SOUND_EVENT: SoundEvent = run {
        val resourceLocation = asResource("event.fart")
        register(
            SOUND_EVENT,
            resourceLocation,
            createVariableRangeEvent(resourceLocation)
        )
    }

    val FLATULENCE_EFFECT: Holder<MobEffect> = registerForHolder(
        MOB_EFFECT,
        withDefaultNamespace("flatulence_effect"),
        FlatulenceEffect().withSoundOnAdded(FART_SOUND_EVENT)
    )

    val CABBAGE_CONSUMABLE: Consumable = Consumables.defaultFood()
        .onConsume(
            ApplyStatusEffectsConsumeEffect(
                MobEffectInstance(FLATULENCE_EFFECT, 600, 0), 1.0F
            )
        )
        .build()

    val CABBAGE_FOOD: FoodProperties = FoodProperties.Builder()
        .nutrition(3).saturationModifier(0.6f).build()

    val CABBAGE: Item = registerItem(
        "cabbage",
        Item.Properties().food(CABBAGE_FOOD, CABBAGE_CONSUMABLE)
    )

    val CABBAGE_CROP: Block = registerBlock(
        "cabbage_crop_block",
        { properties: BlockBehaviour.Properties -> CabbageCropBlock(properties) },
        BlockBehaviour.Properties.of()
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY)
    )

    val CABBAGE_SEEDS: Item = registerItem("cabbage_seeds") { properties: Item.Properties ->
        BlockItem(CABBAGE_CROP, properties.useItemDescriptionPrefix())
    }

//    val SPLASH_POTION_OF_CLEANSING: Item = SplashPotionOfCleansing(Settings().maxCount(16))

    val FLATULENCE_POTION: Potion =
        registerPotion("flatulence_potion", MobEffectInstance(FLATULENCE_EFFECT, 3600))
    val LONG_FLATULENCE_POTION: Potion =
        registerPotion("long_flatulence_potion", MobEffectInstance(FLATULENCE_EFFECT, 9600))

    private fun registerPotion(string: String, mobEffectInstance: MobEffectInstance) =
        register(
            POTION,
            resourceKey(string, Registries.POTION),
            Potion(string, mobEffectInstance)
        )


    private fun registerBlock(
        string: String,
        function: Function<BlockBehaviour.Properties, Block>,
        properties: BlockBehaviour.Properties
    ): Block {
        val resourceKey = resourceKey(string, Registries.BLOCK)
        val block = function.apply(properties.setId(resourceKey))
        return register(BLOCK, resourceKey, block)
    }

    private fun registerItemBlock(
        path: String,
        block: Block,
        properties: Item.Properties = Item.Properties()
    ): BlockItem {
        val key = resourceKey(path, Registries.ITEM)
        val props = properties.useBlockDescriptionPrefix().setId(key)
        return register(ITEM, key, BlockItem(block, props))
    }

    private fun registerItem(
        path: String,
        properties: Item.Properties = Item.Properties(),
        factory: (Item.Properties) -> Item = { prop -> Item(prop) },
    ): Item {
        val key = resourceKey(path, Registries.ITEM)
        return registerItem(key, factory, properties)
    }

    fun asResource(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)

    fun <T> resourceKey(path: String, key: ResourceKey<Registry<T>>) =
        ResourceKey.create(key, asResource(path))
}
