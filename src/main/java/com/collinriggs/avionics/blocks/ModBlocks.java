package com.collinriggs.avionics.blocks;

import static net.minecraftforge.fml.common.registry.GameRegistry.registerTileEntity;

import net.minecraft.block.Block;

public class ModBlocks {
	
	public static Block newWorkbench;
	
	public static void init() {
		newWorkbench = new NewCraftingTable();
	}
	
	public static void register() {
		registerTileEntity(TileEntityNewWorkbench.class, "modBench");
	}

}
