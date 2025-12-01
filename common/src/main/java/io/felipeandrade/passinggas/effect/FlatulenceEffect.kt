package io.felipeandrade.passinggas.effect

import io.felipeandrade.passinggas.PassingGas.FART_SOUND_EVENT
import io.felipeandrade.passinggas.PassingGas.LOG
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import java.util.*

class FlatulenceEffect : MobEffect(MobEffectCategory.NEUTRAL, 0x32CD32) { // Changed color to Foul green
    private val random = Random()

    override fun applyEffectTick(serverLevel: ServerLevel, livingEntity: LivingEntity, i: Int): Boolean {
        if (livingEntity is ServerPlayer && livingEntity.isCrouching) {
            livingEntity.playSound(FART_SOUND_EVENT.get(), 1.0f, 1.0f)
            LOG.info("Living entity ${livingEntity.name} farted because it was crouching")
        } else {
            // 10% chance to play Fart Sound
            if (random.nextDouble() <= 0.1) {
                livingEntity.playSound(FART_SOUND_EVENT.get(), 1.0f, 1.0f)
                LOG.info("Living entity ${livingEntity.name} farted because of chance")
            }
        }

        return true
    }

    override fun shouldApplyEffectTickThisTick(duration: Int, amplifier: Int): Boolean {
        val i = random.nextInt(25, 31) shr amplifier
        return if (i > 0) {
            duration % i == 0
        } else true
    }
}
