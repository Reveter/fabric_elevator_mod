package net.reveter.elevator.mixin;

import net.reveter.elevator.block.ElevatorBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.reveter.elevator.ElevatorMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.reveter.elevator.ElevatorMod.TELEPORT_EVENT;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract BlockPos getBlockPos();
    @Shadow public World world;

    @Shadow public abstract void teleport(double destX, double destY, double destZ);

    @Shadow private Vec3d pos;



    @Inject(at = @At("HEAD"), method = "setSneaking", cancellable = true)
    private void setSneaking(boolean sneaking, CallbackInfo info){
        if(sneaking){
            BlockPos pos = getBlockPos();
            Block block = world.getBlockState(pos.down()).getBlock();

            if (block instanceof ElevatorBlock){
                pos = pos.down();
                for(int i = 0; i <= ElevatorMod.CONFIG.elevatorDistance; i++){
                    if(pos.getY() <= 0){
                        break;
                    }else if ((world.getBlockState(pos.down()).getBlock().equals(block)) && ElevatorBlock.isSafe(world, pos)) {
                        world.playSound(null, pos, TELEPORT_EVENT, SoundCategory.BLOCKS, 0.2F, 1.0F);
                        teleport(this.pos.x, pos.down().getY() + 1, this.pos.z);
                        break;
                    } else {
                        pos = pos.down();
                    }
                }
            }
        }
    }
}
