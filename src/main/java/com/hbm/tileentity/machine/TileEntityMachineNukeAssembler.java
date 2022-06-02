package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachineNukeAssembler extends TileEntityMachineBase {
	
	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	
	public TileEntityMachineNukeAssembler() {
		super(21);
	}
	
	@Override
	public String getName() {
		return "container.nukeAssembler";
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(i < 20) {
			return true;
		}
		
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_io;
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 20;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			this.networkPack(data, 50);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		
	}
	
	
}
