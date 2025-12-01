package io.felipeandrade.passinggas.block

import com.mojang.serialization.MapCodec
import io.felipeandrade.passinggas.PassingGas.CABBAGE_SEEDS
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class CabbageCropBlock(properties: Properties) : CropBlock(properties) {
    override fun codec(): MapCodec<CabbageCropBlock> = CODEC

    override fun getBaseSeedId(): ItemLike = CABBAGE_SEEDS.get()

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape = SHAPES[this.getAge(blockState)]

    companion object {
        val CODEC: MapCodec<CabbageCropBlock> =
            simpleCodec<CabbageCropBlock> { properties: Properties -> CabbageCropBlock(properties) }
        private val SHAPES: Array<VoxelShape> =
            boxes(7) { i: Int -> column(16.0, 0.0, (2 + i).toDouble()) }
    }
}
