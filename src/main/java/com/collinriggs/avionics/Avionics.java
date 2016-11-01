package com.collinriggs.avionics;

import com.collinriggs.avionics.blocks.GuiHandler;
import com.collinriggs.avionics.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Ref.modid, name = Ref.modname, version = Ref.version)
public class Avionics {
	
	@Instance(value = Ref.modid)
	public static Avionics instance;
	
	@SidedProxy(clientSide = "com.collinriggs.avionics.proxy.ClientProxy", serverSide = "com.collinriggs.avionics.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);

		//ModBlocks.init();
		//ModBlocks.register();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		//Crafting
	}
}
