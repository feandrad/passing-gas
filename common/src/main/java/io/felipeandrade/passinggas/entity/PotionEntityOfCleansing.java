package io.felipeandrade.passinggas.entity;


//
//import java.util.List;
//
//public class PotionEntityOfCleansing extends PotionEntity {
//
//    public PotionEntityOfCleansing(World world, LivingEntity owner) {
//        super(world, owner);
//    }
//
//    protected void onCollision(HitResult hitResult) {
//        super.onCollision(hitResult);
//
//        if (hitResult.getType() == HitResult.Type.ENTITY) {
//            Entity entity = ((EntityHitResult) hitResult).getEntity();
//
//            if (entity.isLiving()) {
//                ((LivingEntity) entity).clearStatusEffects();
//            }
//        }
//
//        clearEffectArea();
//    }
//
//    private void clearEffectArea() {
//        Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
//        List<LivingEntity> list = this.getWorld().getEntitiesByClass(
//                LivingEntity.class,
//                box,
//                (entity) -> true
//        );
//
//        for (LivingEntity livingEntity : list) {
//            double d = this.squaredDistanceTo(livingEntity);
//            if (d < 16.0) {
//                livingEntity.clearStatusEffects();
//            }
//        }
//    }
//
//}
