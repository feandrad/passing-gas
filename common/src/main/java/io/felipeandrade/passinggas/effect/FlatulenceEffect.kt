package io.felipeandrade.passinggas.effect

import io.felipeandrade.passinggas.PassingGas.FART_SOUND_EVENT
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import java.util.*

class FlatulenceEffect : MobEffect(MobEffectCategory.NEUTRAL, 0x660066) {
    private val random = Random()

    override fun applyEffectTick(serverLevel: ServerLevel, livingEntity: LivingEntity, i: Int): Boolean {
        if (livingEntity is ServerPlayer && livingEntity.isCrouching) {
            livingEntity.makeSound(FART_SOUND_EVENT)
        } else {
            // 10% chance to call playFartSound(entity);
            if (random.nextDouble() <= 0.1) {
                livingEntity.makeSound(FART_SOUND_EVENT)
            }
        }

        return true
    }

    override fun shouldApplyEffectTickThisTick(duration: Int, amplifier: Int): Boolean {
        val i = random.nextInt(25, 31) shr amplifier
        if (i > 0) {
            return duration % i == 0
        } else {
            return true
        }
    }
}
