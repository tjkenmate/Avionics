package com.collinriggs.avionics.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static NewCraftingTable newWorkbench;

    public static void init() {
        newWorkbench = new NewCraftingTable();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        newWorkbench.initModel();
    }

}
