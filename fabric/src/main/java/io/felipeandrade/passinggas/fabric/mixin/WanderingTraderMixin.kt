package io.felipeandrade.passinggas.fabric.mixin

import io.felipeandrade.passinggas.PassingGas.CABBAGE_SEEDS
import io.felipeandrade.passinggas.trade.SeedTradeLogic
import net.minecraft.world.entity.npc.WanderingTrader
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.trading.ItemCost
import net.minecraft.world.item.trading.MerchantOffer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import kotlin.random.Random

@Mixin(WanderingTrader::class)
class WanderingTraderMixin {
    
    @Inject(method = ["updateTrades"], at = [At("TAIL")])
    private fun addCustomTrades(callbackInfo: CallbackInfo) {
        val trader = this as WanderingTrader
        val offers = trader.offers
        val random = Random(trader.random.nextLong())
        
        if (random.nextDouble() < 0.2)
            offers.add(
                MerchantOffer(
                    ItemCost(Items.EMERALD, random.nextInt(1, 3)),
                    ItemStack(CABBAGE_SEEDS, random.nextInt(2, 7)),
                    12, 1, 0.05f
                )
            )
        
        val sellSeed = SeedTradeLogic.pickSeeds(Random(trader.random.nextLong()))
        sellSeed.forEach { seed ->
            offers.add(
                MerchantOffer(
                    ItemCost(seed, random.nextInt(16, 65)),  
                    ItemStack(Items.EMERALD, random.nextInt(1, 3)),  
                    8, 1, 0.05f
                )
            )
        }
    }
}