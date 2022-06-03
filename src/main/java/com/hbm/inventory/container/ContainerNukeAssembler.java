package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineNukeAssembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNukeAssembler extends Container {
	
	private TileEntityMachineNukeAssembler assembler;
	
	public ContainerNukeAssembler(InventoryPlayer playerInv, TileEntityMachineNukeAssembler tile) {
		assembler = tile;
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 6; j++) {
				this.addSlotToContainer(new InputSlot(tile, j + i * 3, 9 + j * 18, 20 + i * 18));
			}
		}
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 4; j++) {
				this.addSlotToContainer(new InputSlot(tile, 12 + j + i * 3, 9 + j * 18, 56 + i * 18));
			}
		}
		
		this.addSlotToContainer(new InputSlot(tile, 20, 27, 107));
		this.addSlotToContainer(new InputSlot(tile, 21, 45, 107));
		
		this.addSlotToContainer(new SlotMachineOutput(tile, 22, 96, 71));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 15 + j * 18, 171 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 15 + i * 18, 229));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return assembler.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack returnStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			returnStack = stack.copy();

			if(index <= 22) {
				if(!this.mergeItemStack(stack, 23, this.inventorySlots.size(), false)) {
					return null;
				}
			} else if(!this.mergeItemStack(stack, 0, 23, false)) {
					return null;
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
		public void onSlotChanged() {
			super.onSlotChanged();
			assembler.slotHasChanged = true;
		}
	}
}
