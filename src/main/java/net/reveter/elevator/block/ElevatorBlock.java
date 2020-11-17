package net.reveter.elevator.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElevatorBlock extends Block {
    public ElevatorBlock(Settings settings) {
        super(settings);
    }


    public static boolean isSafe(World world, BlockPos pos){
        return world.getBlockState(pos).getCollisionShape(world, pos.up(2)).isEmpty();
    }
}
