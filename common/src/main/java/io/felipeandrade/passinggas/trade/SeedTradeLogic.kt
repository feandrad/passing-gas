package io.felipeandrade.passinggas.trade

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import kotlin.random.Random

object SeedTradeLogic {
    private val SEEDS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "seeds"))
    
    fun pickSeeds(random: Random): List<Item> {
        val seeds = mutableListOf<Item>()
        
        // Collect all items from c:seeds tag
        val registry = net.minecraft.core.registries.BuiltInRegistries.ITEM
        registry.getTagOrEmpty(SEEDS_TAG).forEach { holder ->
            seeds.add(holder.value())
        }
        
        if (seeds.isEmpty()) return emptyList()
        
        // Shuffle for randomness
        seeds.shuffle(java.util.Random(random.nextLong()))
        
        // Determine count: guaranteed 1, then probabilistic extras
        var count = 1 // minimum guaranteed
        if (random.nextFloat() < 0.60f) count++
        if (random.nextFloat() < 0.35f) count++
        if (random.nextFloat() < 0.15f) count++
        
        // Cap at 4 and available seeds
        count = minOf(count, 4, seeds.size)
        
        return seeds.take(count)
    }
}