package net.reveter.elevator.mixin;

import net.reveter.elevator.ElevatorMod;
import net.reveter.elevator.block.ElevatorBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.reveter.elevator.ElevatorMod.TELEPORT_EVENT;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	@Inject(at = @At("HEAD"), method = "jump", cancellable = true)
	private void init(CallbackInfo info) {
		BlockPos pos = this.getBlockPos();
		Block block = this.world.getBlockState(pos.down()).getBlock();

		if (block instanceof ElevatorBlock) {
			for(int i = 0; i <= ElevatorMod.CONFIG.elevatorDistance - 1; i++){
				if(pos.getY() >= this.world.getHeight()){
					break;
				}else if((world.getBlockState(pos.up()).getBlock().equals(block)) && ElevatorBlock.isSafe(world, pos.up(2))) {
					world.playSound(null, pos, TELEPORT_EVENT, SoundCategory.BLOCKS, 0.2F, 1.0F);
					this.teleport(this.getPos().x, pos.up().getY()+1, this.getPos().z);
					break;
				}else{
					pos = pos.up();
				}
			}
		}
	}
}
