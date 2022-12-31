package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityBessemer;
import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBessemer extends Container {
	
	protected TileEntityBessemer bessemer;
	
	public ContainerBessemer(InventoryPlayer invPlayer, TileEntityBessemer bessemer) {
		this.bessemer = bessemer;
		
		this.addSlotToContainer(new Slot(bessemer, 0, 96, 59)); //template
		this.addSlotToContainer(new Slot(bessemer, 1, 78, 59)); //coke
		
		for(int i = 0; i < 3; i++) { //smelt slots
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new Slot(bessemer, j + i * 3 + 1, 10 + j * 18, 23 + i * 18));
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 125 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 183));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack originalStack = slot.getStack();
			stack = originalStack.copy();

			if(index <= 10) {
				if(!this.mergeItemStack(originalStack, 11, this.inventorySlots.size(), false)) {
					return null;
				}
				
			} else if(!InventoryUtil.mergeItemStack(this.inventorySlots, originalStack, 0, 10, false)) {
				return null;
			}

			if(originalStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return stack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return bessemer.isUseableByPlayer(player);
	}
	
}
