package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FuelHandler;
import com.hbm.inventory.container.ContainerBessemer;
import com.hbm.inventory.gui.GUIBessemer;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.CrucibleRecipes.CrucibleRecipe;
import com.hbm.items.ModItems;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ItemStackUtil;

import api.hbm.block.ICrucibleAcceptor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBessemer extends TileEntityMachineBase implements ICrucibleAcceptor, IGUIProvider { //, IControlReceiver {
	
	public int maxBurnTime;
	public int burnTime;
	public int progress;
	
	public List<MaterialStack> recipeStack = new ArrayList();
	
	public static int recipeCapacity = MaterialShapes.BLOCK.q(32);
	public static int processTime = 20_000; //The Bessemer's main benefit is not caring about burn heat; but as a drawback, smelting cannot be sped up.
	
	public TileEntityBessemer() {
		super(8);
	}
	
	@Override
	public String getName() {
		return "container.bessemer";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			/* Burn items */
			if(burnTime <= 0) {
				
				if(slots[1] != null) {
					
					int fuel = getBurnTime(slots[1]);
					
					if(fuel > 0) {
						this.maxBurnTime = this.burnTime = fuel;
						slots[1].stackSize--;

						if(slots[1].stackSize == 0) {
							slots[1] = slots[1].getItem().getContainerItem(slots[1]);
						}
					}
				}
			}
			
			/* Smelt items */
			if(!trySmelt()) {
				this.progress = 0;
			}
			
			
			/* Convert steel */
			
			
			/* Pouring */
			
			
			/* Network Pack */
			NBTTagCompound data = new NBTTagCompound();
			int[] rec = new int[recipeStack.size() * 2];
			for(int i = 0; i < recipeStack.size(); i++) { MaterialStack sta = recipeStack.get(i); rec[i * 2] = sta.material.id; rec[i * 2 + 1] = sta.amount; }
			data.setIntArray("rec", rec);
			data.setInteger("maxBurnTime", maxBurnTime);
			data.setInteger("burnTime", burnTime);
			data.setInteger("progress", progress);
			this.networkPack(data, 25);
		}
		
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {

		this.recipeStack.clear();
		
		int[] rec = nbt.getIntArray("rec");
		for(int i = 0; i < rec.length / 2; i++) {
			recipeStack.add(new MaterialStack(Mats.matById.get(rec[i * 2]), rec[i * 2 + 1]));
		}
		
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.progress = nbt.getInteger("progress");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		int[] rec = nbt.getIntArray("rec");
		for(int i = 0; i < rec.length / 2; i++) {
			recipeStack.add(new MaterialStack(Mats.matById.get(rec[i * 2]), rec[i * 2 + 1]));
		}
		
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.progress = nbt.getInteger("progress");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		int[] rec = new int[recipeStack.size() * 2];
		for(int i = 0; i < recipeStack.size(); i++) { MaterialStack sta = recipeStack.get(i); rec[i * 2] = sta.material.id; rec[i * 2 + 1] = sta.amount; }
		nbt.setIntArray("rec", rec);
		nbt.setInteger("maxBurnTime", maxBurnTime);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("progress", progress);
	}
	
	/* BURNING / SMELTING METHODS */
	
	/** Restricts burn options to coal and coke. ModuleBurnTime isn't really meant for this sort of restriction, so I've settled for a hardcoded method. */
	private int getBurnTime(ItemStack stack) {
		if(stack == null) return 0;
		
		int fuel = FuelHandler.getBurnTimeFromCache(stack);
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			if(name.contains("Coke"))		return (int)(fuel * 0.75D);
			if(name.contains("Coal"))		return (int)(fuel * 1.5D);
		}
		
		return 0;
	}
	
	private boolean trySmelt() {
		
		return true;
	}
	
	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ,
			ForgeDirection side, MaterialStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side,
			MaterialStack stack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		// TODO Auto-generated method stub
		return null;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord - 3,
					zCoord - 5,
					xCoord + 6,
					yCoord + 5,
					zCoord + 6
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerBessemer(player.inventory, this);
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIBessemer(player.inventory, this);
	}
	
}

	/*
	
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
	
	//TODO: throw all of this out, start from ground zero.
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			/*isItemSmeltable(slots[0]);
			
			if(!tryConvert())
				
			else if(automatic && !canConvert() && steel.amount > 0) //wait until all potential steel is converted
				isPouring = true;
			
			if(angle >= maxAngle && isPouring && steel.amount > 0) {
				//empty as much of contents as possible until target block is filled/this is empty
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				List<MaterialStack> pourStack = new ArrayList() { { add(steel); } };
				MaterialStack returnStack = this.pour(worldObj, progressSpeed, carbonCapacity, metalCapacity, carbonCapacity, carbon, angle, dir, iron)
				MaterialStack returnStack = ICrucibleAcceptor.tryPour(worldObj, xCoord + 0.5D + dir.offsetX * 1.75D, yCoord + 0.25D, zCoord + 0.5D + dir.offsetZ * 1.75D, 6, false, pourStack, MaterialShapes.INGOT.q(1), impact);
				
				if(returnStack == null || (returnStack != null && returnStack.amount <= 0))
					isPouring = false;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			//TODO materialstacks 
			data.setBoolean("auto", automatic);
			data.setBoolean("pour", isPouring);
			data.setFloat("angle", angle); //see below
			this.networkPack(data, 50);
		} /*else {
			
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
			angle = angle >= maxAngle ? maxAngle : 0;*/
	/*}
	
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
		//if(stack.material != Mats.MAT_IRON && getQuantaFromType() >= metalCapacity) return false;
		//TODO: check where the pour is hitting here using forge direction; also check isPouring
		if(isPouring || angle > 0) return false;
		return true;
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		
		if(stack.material == additiveStack.material) {
			if(additiveStack.amount + stack.amount <= additiveCapacity) {
				additiveStack.amount += stack.amount;
				return null;
			}
			
			int required = additiveCapacity - additiveStack.amount;
			additiveStack.amount += required;
			
			stack.amount -= required;
			
			return stack;
		}
		
		int metalAmount = getQuantaFromType(metalStack, null);
		
		if(metalAmount + stack.amount <= metalCapacity) {
			addToStack(metalStack, stack);
			return null;
		}
		
		int required = metalCapacity - metalAmount;
		
		for(MaterialStack stack0ν0 : metalStack) {
			if(stack0ν0.material == stack.material) {
				stack0ν0.amount += stack.amount; break;
			}
			
			metalStack.add(new MaterialStack(stack.material, required));
		}
		
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
	
	private boolean tryConvert() {
		
		if(worldObj.getTotalWorldTime() % 2 > 0) return false;
		//this code is so fucking exceedingly abysmal to me and it's pretty par for the course. someone shoot me
		int iron = Math.min(getQuantaFromType(metalStack, Mats.MAT_IRON), MaterialShapes.INGOT.q(2));
		int additive = additiveStack.amount;
		
		if(iron < MaterialShapes.NUGGET.q(3)) return false;
		if(additive < MaterialShapes.NUGGET.q(1)) return false;
		
		int converted = Math.min(iron, additive * 3);
		
		for(MaterialStack stack : metalStack) {
			if(stack.material == Mats.MAT_IRON)
				stack.amount -= converted;
		}
		
		additiveStack.amount -= converted / 3;
		
		for(MaterialStack stack : this.metalStack) {
			if(stack.material == Mats.MAT_STEEL) {
				stack.amount += converted;
				break;
			}
			
			this.metalStack.add(new MaterialStack(Mats.MAT_STEEL, converted));
		}
		
		return true;
	}
	
	public int getQuantaFromType(List<MaterialStack> stacks, NTMMaterial mat) {
		int Σ = 0;
		for(MaterialStack oωo : stacks) {
			if(oωo.material == mat) {
				return oωo.amount;
			}
			if(mat == null) {
				Σ += oωo.amount;
			}
		}
		return Σ;
	}
	
	public void addToStack(List<MaterialStack> stack, MaterialStack matStack) {	
		for(MaterialStack mat : stack) {
			if(mat.material == matStack.material) {
				mat.amount += matStack.amount;
				return;
			}
		}
		
		stack.add(matStack.copy());
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
}*/
