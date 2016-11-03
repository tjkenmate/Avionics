package com.collinriggs.avionics.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ModWorkbenchRecipes {
	
	public void addRecipes(WorkbenchCraftingManager manager) {
		manager.addRecipe(new ItemStack(Blocks.FURNACE), new Object[] {"MMM", "MMM", "MMM", 'M', Blocks.COBBLESTONE});
	}
	
}
