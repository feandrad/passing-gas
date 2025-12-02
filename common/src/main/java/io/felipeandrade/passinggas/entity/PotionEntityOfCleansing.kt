package io.felipeandrade.passinggas.entity

import io.felipeandrade.passinggas.PassingGas
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult

class PotionEntityOfCleansing : net.minecraft.world.entity.projectile.ThrowableItemProjectile {

    constructor(entityType: EntityType<out net.minecraft.world.entity.projectile.ThrowableItemProjectile>, level: Level) : super(entityType, level)

    constructor(level: Level, owner: LivingEntity) : super(PassingGas.POTION_ENTITY_OF_CLEANSING.get(), owner, level, ItemStack(PassingGas.SPLASH_POTION_OF_CLEANSING.get()))

    override fun getDefaultItem(): Item {
        return PassingGas.SPLASH_POTION_OF_CLEANSING.get()
    }

    override fun onHit(hitResult: HitResult) {
        if (hitResult.type == HitResult.Type.ENTITY) {
            val entity = (hitResult as EntityHitResult).entity
            if (entity is LivingEntity) {
                entity.removeAllEffects()
            }
        }

        if (!level().isClientSide) {
            clearEffectArea()
            this.discard()
        }
    }

    private fun clearEffectArea() {
        val box = this.boundingBox.inflate(4.0, 2.0, 4.0)
        val list = this.level().getEntitiesOfClass(LivingEntity::class.java, box) { true }

        for (livingEntity in list) {
            val d = this.distanceToSqr(livingEntity)
            if (d < 16.0) {
                livingEntity.removeAllEffects()
            }
        }
    }
}