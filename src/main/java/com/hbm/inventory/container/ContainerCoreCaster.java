package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineCoreCaster;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCoreCaster extends Container {
	
	private TileEntityMachineCoreCaster caster;
	
	public ContainerCoreCaster(InventoryPlayer playerInv, TileEntityMachineCoreCaster tile) {
		caster = tile;
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 2; j++) {
				this.addSlotToContainer(new InputSlot(tile, j + i * 2, 13 + j * 18, 26 + i * 18));
			}
		}
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 2; j++) {
				this.addSlotToContainer(new InputSlot(tile, 8 + j + i * 2, 55 + j * 18, 26 + i * 18));
			}
		}
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 2; j++) {
				this.addSlotToContainer(new InputSlot(tile, 16 + j + i * 2, 97 + j * 18, 26 + i * 18));
			}
		}
		
		this.addSlotToContainer(new SlotMachineOutput(tile, 24, 143, 30));
		this.addSlotToContainer(new SlotMachineOutput(tile, 25, 143, 58));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 171));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return caster.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack returnStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			returnStack = stack.copy();

			if(index <= 25) {
				if(!this.mergeItemStack(stack, 26, this.inventorySlots.size(), false)) {
					return null;
				}
			} else {
				if(caster.selection == -1)
					return null;
				
				if(caster.gunStyleMap.containsKey(stack.getItem())) {
					if(!this.mergeItemStack(stack, 8, 16, false))
						return null;
				} else {
					return null;
				}
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return returnStack;
	}
	
	public class InputSlot extends Slot {
		
		public InputSlot(IInventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return caster.isItemValidForSlot(getSlotIndex(), stack);
		}
		
		@Override
		public void onSlotChanged() {
			super.onSlotChanged();
			caster.slotHasChanged = true;
		}
	}
}