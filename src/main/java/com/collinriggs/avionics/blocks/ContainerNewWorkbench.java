package com.collinriggs.avionics.blocks;

import javax.annotation.Nullable;

import com.collinriggs.avionics.recipe.WorkbenchCraftingManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerNewWorkbench extends Container {

    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    public ItemStackHandler bookSlot = new ItemStackHandler() {
        @Override
        public void setStackInSlot(int slot, ItemStack stack) {
            if (false == ContainerNewWorkbench.this.isValidBook(stack)) {
                return;
            }
            super.setStackInSlot(slot, stack);
            ContainerNewWorkbench.this.onCraftMatrixChanged(ContainerNewWorkbench.this.craftMatrix);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (false == ContainerNewWorkbench.this.isValidBook(stack)) {
                return stack;
            }
            ItemStack result = super.insertItem(slot, stack, simulate);
            ContainerNewWorkbench.this.onCraftMatrixChanged(ContainerNewWorkbench.this.craftMatrix);
            return result;
        }
    };

    private World worldObj;

    public ContainerNewWorkbench(InventoryPlayer playerInventory, World worldIn) {
        this.worldObj = worldIn;
        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));

        for (int i = 0; i < 3; ++i) { //i = y
            for (int j = 0; j < 3; ++j) { //j = x
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for (int k = 0; k < 3; ++k) { //k = y
            for (int i1 = 0; i1 < 9; ++i1) { //i1 = x
                this.addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) { //l = x
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
        }

        this.onCraftMatrixChanged(this.craftMatrix);

        this.addSlotToContainer(new SlotItemHandler(this.bookSlot, 0, 8, 35));
    }

    private boolean isValidBook(ItemStack stack) {
        return (stack != null) && (stack.getItem() == Items.BOOK);
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    public void onCraftMatrixChanged(IInventory inventoryIn) {
        ItemStack toCraft = null;
        if (this.isValidBook(this.bookSlot.getStackInSlot(0))) {
            // only handle custom recipes if we have a book
            toCraft = WorkbenchCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj);
        }
        if (toCraft == null)
        {
            // test for Vanilla Recipe
            toCraft = CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj);
        }
        this.craftResult.setInventorySlotContents(0, toCraft);
    }

    @Nullable
    @Override
    public Slot getSlotFromInventory(IInventory inv, int slotIn) {
        if (this.inventoryItemStacks == null) {
            getSlotFromInventory(inv, slotIn).inventory.clear();
        }
        return super.getSlotFromInventory(inv, slotIn);
    }

    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);

        if (!this.worldObj.isRemote) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

                if (itemstack != null) {
                    playerIn.dropItem(itemstack, false);
                }
            }
            ItemStack bookStack = this.bookSlot.getStackInSlot(0);
            if (bookStack != null) {
                playerIn.dropItem(bookStack, false);
            }
        }
    }

    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 10 && index < 37) {
                if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
                    return null;
                }
            } else if (index >= 37 && index < 46) {
                if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }

    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}