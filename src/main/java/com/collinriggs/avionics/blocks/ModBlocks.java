package com.collinriggs.avionics.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import static net.minecraftforge.fml.common.registry.GameRegistry.registerTileEntity;

public class ModBlocks {
	
	public static Block newWorkbench;
	
	public static void init() {
		newWorkbench = new NewCraftingTable();
	}
	
	public static void register() {
		registerBlock(newWorkbench);
		
		registerTileEntity(TileEntityNewWorkbench.class, "modBench");
	}
	
	private static void registerBlock(Block block) {
        GameRegistry.register(block);
        Item item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        GameRegistry.register(item);
    }

}
