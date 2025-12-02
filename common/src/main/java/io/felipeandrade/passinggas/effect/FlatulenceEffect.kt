package io.felipeandrade.passinggas.effect

import io.felipeandrade.passinggas.PassingGas.FART_SOUND_EVENT
import io.felipeandrade.passinggas.PassingGas.LOG
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import kotlin.random.Random

class FlatulenceEffect : MobEffect(MobEffectCategory.NEUTRAL, 0x32CD32) {

    private val rng = Random.Default

    override fun applyEffectTick(serverLevel: ServerLevel, livingEntity: LivingEntity, i: Int): Boolean {
        // Verify we're on server side (should always be true since we have ServerLevel parameter)
        if (livingEntity.level().isClientSide) return true

        if (livingEntity.isCrouching) {
            livingEntity.playSoundSelfIncluded(FART_SOUND_EVENT.get())
            LOG.info("Living entity ${livingEntity.name} farted because it was crouching")
        } else {
            // 10% chance to play Fart Sound
            if (rng.nextDouble() <= 0.1) {
                livingEntity.playSoundSelfIncluded(FART_SOUND_EVENT.get())
                LOG.info("Living entity ${livingEntity.name} farted because of chance")
            }
        }

        return true
    }

    override fun shouldApplyEffectTickThisTick(duration: Int, amplifier: Int): Boolean {
        val i = rng.nextInt(25, 31) shr amplifier
        return if (i > 0) {
            duration % i == 0
        } else true
    }
}

private fun LivingEntity.playSoundSelfIncluded(soundEvent: SoundEvent, volume: Float = 1.0F, pitch: Float = 1.0f) {
//    if (this is ServerPlayer) {
        this.level().playSound(
            null,
            this.blockPosition(),
            soundEvent,
            this.soundSource,
            volume,
            pitch
        )
//    } else this.playSound(FART_SOUND_EVENT.get(), volume, pitch)
}
