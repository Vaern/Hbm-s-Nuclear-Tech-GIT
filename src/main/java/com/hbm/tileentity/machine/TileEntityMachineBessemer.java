package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachineBessemer extends TileEntityMachineBase implements IGUIProvider {
	
	
	
	public TileEntityMachineBessemer() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.bessemer";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			
			NBTTagCompound data = new NBTTagCompound();
			
			this.networkPack(data, 25);
		}
	}
	
	public void networkUnpack() {
		
	}
	
	MaterialStack[] acceptedInput = new MaterialStack[] { 
			new MaterialStack(Mats.MAT_COALCOKE, 10), new MaterialStack(Mats.MAT_PETCOKE, 10) };
	
	//Only used for coke input
	public boolean isItemSmeltable(ItemStack stack) {
		List<MaterialStack> materials = Mats.getMaterialsFromItem(stack);
		
		if(materials.isEmpty())
			return false;
		
		for(MaterialStack mat : materials) {
			
			int input = getQuantaFromType(acceptedInput, mat.material);
			//TODO: actually figure this out instead of being sleepy
		}
		
		return true;
	}
	
	public int getQuantaFromType(MaterialStack[] stacks, NTMMaterial mat) {
		for(MaterialStack stack : stacks) {
			if(mat == null || stack.material == mat) {
				return stack.amount;
			}
		}
		return 0;
	}
}
