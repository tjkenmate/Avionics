package com.collinriggs.avionics.proxy;

import com.collinriggs.avionics.Avionics;
import com.collinriggs.avionics.blocks.GuiHandler;
import com.collinriggs.avionics.blocks.ModBlocks;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        ModBlocks.init();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Avionics.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}