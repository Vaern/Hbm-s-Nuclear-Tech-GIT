package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.block.ICrucibleAcceptor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBessemer extends TileEntityMachineBase implements ICrucibleAcceptor, IControlReceiver { //implements IGUIProvider {:3
	//please help i'm going mental :3
	public List<MaterialStack> metalStack = new ArrayList();
	public MaterialStack additiveStack = null; //just carbon for now
	
	public static int metalCapacity = MaterialShapes.BLOCK.q(24);
	public static int additiveCapacity = MaterialShapes.BLOCK.q(12);
	
	public boolean isProgressing = false;
	
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
			
			//isItemSmeltable(slots[0]);
			
			if(canConvert()) {
				
				int converted = iron.amount >= MaterialShapes.INGOT.q(1) ? Math.max(iron.amount, MaterialShapes.INGOT.q(1)) : iron.amount;
				converted = Math.min(converted, carbon);
				
				carbon -= converted;
				iron.amount -= converted;
				steel.amount += converted;
				
			} else {
				isProgressing = false;
			}
			
			//Whether or not it'll just check for steel content or will also check for unconverted iron, i don't know. i'll figure it out laterr
			if(automatic && !canConvert() && steel.amount > 0) //wait until all potential steel is converted
				isPouring = true;
			
			/*if(angle >= maxAngle && isPouring && steel.amount > 0) {
				//empty as much of contents as possible until target block is filled/this is empty
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				List<MaterialStack> pourStack = new ArrayList() { { add(steel); } };
				MaterialStack returnStack = this.pour(worldObj, progressSpeed, carbonCapacity, metalCapacity, carbonCapacity, carbon, angle, dir, iron)
				MaterialStack returnStack = ICrucibleAcceptor.tryPour(worldObj, xCoord + 0.5D + dir.offsetX * 1.75D, yCoord + 0.25D, zCoord + 0.5D + dir.offsetZ * 1.75D, 6, false, pourStack, MaterialShapes.INGOT.q(1), impact);
				
				if(returnStack == null || (returnStack != null && returnStack.amount <= 0))
					isPouring = false;
			}*/
			
			NBTTagCompound data = new NBTTagCompound();
			//TODO materialstacks 
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
		
		automatic = data.getBoolean("auto");
		isPouring = data.getBoolean("pour");
		angle = data.getFloat("angle"); //see above
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		//TODO materialstacks 
		nbt.setBoolean("auto", automatic);
		nbt.setBoolean("pour", isPouring);
		nbt.setFloat("angle", angle);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		//TODO materialstacks 
		automatic = nbt.getBoolean("auto");
		isPouring = nbt.getBoolean("pour");
		angle = nbt.getFloat("angle");
	}
	
	//ICrucibleAcceptor methods; Bessemer always exclusively accepts iron and pours steel
	
	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		if(side != ForgeDirection.UP) return false;
		if(stack.material != iron.material && iron.amount + steel.amount >= metalCapacity) return false;
		//TODO: check where the pour is hitting here using forge direction; also check isPouring
		if(isPouring || angle > 0) return false;
		return true;
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		if(stack.amount + iron.amount <= metalCapacity) {
			iron.amount += stack.amount;
			return null;
		}
		
		int required = metalCapacity - (iron.amount + steel.amount);
		iron.amount += required;
		
		stack.amount -= required;
		
		return stack;
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return false;
	}

	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return stack;
	}
	
	private boolean canConvert() {
		return !isPouring && metalStack.contains(additiveStack);
	}
	
	private void convert() {
		
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.getBoolean("toggle"))
			this.automatic = !automatic;
		
		if(data.getBoolean("pour") && !isPouring && !isProgressing)
			isPouring = true;
	}
}
