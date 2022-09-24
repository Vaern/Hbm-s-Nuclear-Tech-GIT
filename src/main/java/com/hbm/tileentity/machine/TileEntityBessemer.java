package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBessemer extends TileEntityMachineBase implements IControlReceiver { //implements IGUIProvider {:3
	//please help i'm going mental :3
	public final int metalCapacity = MaterialShapes.BLOCK.q(24);
	public final int carbonCapacity = MaterialShapes.BLOCK.q(20);
	public int steel = 0;
	public int iron = 0;
	public int carbon = 0; //We only care about the amount of carbon, not if it was from petcoke or coal coke or whatever
	
	public boolean isProgressing = false;
	public int progressSpeed = 20;
	
	public boolean automatic = false;
	public boolean isPouring = false;
	
	public float angle;
	public final float maxAngle = 90F;
	@SideOnly(Side.CLIENT)
	public float prevAngle;
	
	public TileEntityBessemer() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.machineBessemer";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			isItemSmeltable(slots[0]);
			
			if(canConvert()) {
				
				int converted = iron >= MaterialShapes.INGOT.q(1) ? Math.max(iron / 8, MaterialShapes.INGOT.q(1)) : iron;
				converted = Math.min(converted, carbon);
				
				carbon -= converted;
				iron -= converted;
				steel += converted;
				
			} else {
				isProgressing = false;
			}
			
			//Whether or not it'll just check for steel content or will also check for unconverted iron, i don't know. i'll figure it out laterr
			if(!isPouring && automatic) //add shit to see if all contained iron has been converted/there's any steel inside
				isPouring = true;
			
			if(angle >= maxAngle && isPouring && steel > 0) {
				//empty as much of contents as possible until target block is filled/this is empty
				
				//if(shit's empty || target's full) {
				isPouring = false;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("iron", iron);
			data.setInteger("steel", steel);
			data.setInteger("carbon", carbon);
			data.setBoolean("auto", automatic);
			data.setBoolean("pour", isPouring);
			data.setFloat("angle", angle); //see below
			this.networkPack(data, 50);
		} else {
			
			if(isProgressing) {
				//literal fire particle effects :333333333333333333333
			}
			
			prevAngle = angle;
		}
		
		//no idea if this is 'good convention' but i don't think anyone will care since it's the fucking angle for a steel-making machine
		//if bob has a better solution, hit me
		//in retrospect i guarantee this will get out of sync eventually but i don't give a shit i'll just ask bob
		if(angle < maxAngle || angle > 0)
			angle += isPouring ? 1.8F : -1.8F; //1.8 degrees every tick > 50 ticks for full 90 degrees
		else
			angle = angle >= maxAngle ? maxAngle : 0;
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		iron = data.getInteger("iron");
		steel = data.getInteger("steel");
		carbon = data.getInteger("carbon");
		automatic = data.getBoolean("auto");
		isPouring = data.getBoolean("pour");
		angle = data.getFloat("angle"); //see above
	}
	
	public boolean canConvert() {
		
		if(isPouring)
			return false;
		
		return iron >= 1 && carbon >= 1; //more efficient :3
	}
	
	MaterialStack[] acceptedInput = new MaterialStack[] { 
			new MaterialStack(Mats.MAT_COALCOKE, 72), new MaterialStack(Mats.MAT_PETCOKE, 108) };
	
	//Only used for coke input :3
	public boolean isItemSmeltable(ItemStack stack) {
		List<MaterialStack> materials = Mats.getMaterialsFromItem(stack);
		
		if(materials.isEmpty())
			return false;
		
		for(MaterialStack mat : materials) {
			int input = getQuantaFromType(acceptedInput, mat.material);
			if(carbon + input <= carbonCapacity) {
				carbon += input;
				
				stack.stackSize--;
				
				if(stack.stackSize <= 0)
					stack = null;
			}			
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
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.getBoolean("toggle"))
			this.automatic = !automatic;
		
		if(data.getBoolean("pour") && !isPouring && steel > 0) //add shit to see if all contained iron has been converted/there's any steel inside
			isPouring = true;
	}
}
