package io.felipeandrade.passinggas.item

import io.felipeandrade.passinggas.entity.PotionEntityOfCleansing
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level

class SplashPotionOfCleansing(properties: Properties) : Item(properties) {

    override fun use(level: Level, user: Player, hand: InteractionHand): InteractionResult {
        val itemStack = user.getItemInHand(hand)
        if (!level.isClientSide) {
            val potionEntity = PotionEntityOfCleansing(level, user)
            potionEntity.item = itemStack
            potionEntity.shootFromRotation(user, user.xRot, user.yRot, -20.0F, 0.5F, 1.0F)
            level.addFreshEntity(potionEntity)
        }

        user.awardStat(Stats.ITEM_USED.get(this))
        if (!user.abilities.instabuild) {
            itemStack.shrink(1)
        }

        return InteractionResult.SUCCESS
    }
}
