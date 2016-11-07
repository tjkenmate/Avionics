package com.collinriggs.avionics.proxy;

import com.collinriggs.avionics.Avionics;
import com.collinriggs.avionics.blocks.GuiHandler;
import com.collinriggs.avionics.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        ModBlocks.init();

        MinecraftForge.EVENT_BUS.register(new ServerEventsHandler(e.getModLog()));
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Avionics.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) { }

    private class ServerEventsHandler {
        private Logger log;

        ServerEventsHandler(Logger log) {
            this.log = log;
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public void onPlayerInteract(final PlayerInteractEvent.RightClickBlock e) {
            if (e.getWorld().isRemote) {
                return; // don't care about client side
            }

            IBlockState state = e.getWorld().getBlockState(e.getPos());
            if (state != null) {
                Block block = state.getBlock();
                if (block != null) {
                    if ((Avionics.disableFurnace && ((block == Blocks.FURNACE) || (block.getRegistryName().equals(Blocks.FURNACE.getRegistryName()))))
                            || (Avionics.disableCraftingTable && ((block == Blocks.CRAFTING_TABLE) || (block.getRegistryName().equals(Blocks.CRAFTING_TABLE.getRegistryName()))))) {
                        e.setCanceled(true);
                        this.log.info("Denied access to: '" + block.getRegistryName() + "'.");
                    }
                }
            }
        }
    }}
