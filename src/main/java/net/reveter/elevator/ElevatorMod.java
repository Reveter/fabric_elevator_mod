package net.reveter.elevator;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.reveter.elevator.block.ElevatorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ElevatorMod implements ModInitializer {

    public static final Block ELEVATOR_BLOCK = new ElevatorBlock(FabricBlockSettings.of(Material.WOOL).hardness(1.0F).breakByHand(true).sounds(BlockSoundGroup.WOOL));
    public static ModConfig CONFIG;
    public static final Identifier TELEPORT_ID = new Identifier("elevator:teleport");
    public static SoundEvent TELEPORT_EVENT = new SoundEvent(TELEPORT_ID);

    @Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

        Registry.register(Registry.BLOCK, new Identifier("elevator", "elevator_block"), ELEVATOR_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("elevator", "elevator_block"), new BlockItem(ELEVATOR_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.SOUND_EVENT, ElevatorMod.TELEPORT_ID, TELEPORT_EVENT);
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
	}


}
