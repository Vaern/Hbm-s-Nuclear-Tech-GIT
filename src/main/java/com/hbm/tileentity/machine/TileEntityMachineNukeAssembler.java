package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityMachineNukeAssembler extends TileEntityMachineBase implements IControlReceiver {
	
	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	
	public boolean slotHasChanged = true;
	
	public TileEntityMachineNukeAssembler() {
		super(23);
	}
	
	@Override
	public String getName() {
		return "container.nukeAssembler";
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(i < 22) {
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
		return i == 22;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		super.setInventorySlotContents(i, itemStack);
		this.slotHasChanged = true; //For automation
	}
	
	@Override
	public ItemStack decrStackSize(int i, int amount) {
		this.slotHasChanged = true; //For crafting method
		return super.decrStackSize(i, amount);
	}
	
	@Override
	public int getInventoryStackLimit() {
		this.slotHasChanged = true; //For automation
		return 64;
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
			
			if(slotHasChanged) {
				//getValues();
				slotHasChanged = false;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			this.networkPack(data, 50);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		
	}
	
	private void getValues() {
		
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		
		if(data.hasKey("craft")) {
			//getValues();
			//getOutput();
		}
	}
	
	
}
