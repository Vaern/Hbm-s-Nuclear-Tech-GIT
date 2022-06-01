package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.recipes.CoreCasterRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import com.hbm.util.Tuple.Pair;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityMachineCoreCaster extends TileEntityMachineBase implements IControlReceiver {
	
	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	
	public boolean mode = false;
	public int selection = -1;
	
	//TODO: Once necessary, move all of this to an int array.
	public int quantity = 0;
	public int criticalMass = 0;
	public int radiation = 0;
	
	public boolean slotHasChanged = true;
	public ItemStack[] consumedItems = new ItemStack[24];
	
	/** Quantity; Critical Mass; Radioactivity */
	public static HashMap<Item, int[]> gunStyleMap = new HashMap<Item, int[]>();
	
	public TileEntityMachineCoreCaster() {
		super(23);
	}
	
	@Override
	public String getName() {
		return "container.coreCaster";
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(selection == -1)
			return false;
		
		if(i < 6) {
			
		} else if(i < 12) {
			return gunStyleMap.containsKey(stack.getItem());
		} else if(i < 20) {
			
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
		nbt.setBoolean("mode", mode);
		nbt.setInteger("selection", selection);
		nbt.setInteger("quantity", quantity);
		nbt.setInteger("criticalMass", criticalMass);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.mode = nbt.getBoolean("mode");
		this.selection = nbt.getInteger("selection");
		this.quantity = nbt.getInteger("quantity");
		this.criticalMass = nbt.getInteger("criticalMass");
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			
			if(slotHasChanged) {
				getValues();
				slotHasChanged = false;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("mode", mode);
			data.setInteger("selection", selection);
			data.setInteger("quantity", quantity);
			data.setInteger("criticalMass", criticalMass);
			this.networkPack(data, 50);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.mode = data.getBoolean("mode");
		this.selection = data.getInteger("selection");
		this.quantity = data.getInteger("quantity");
		this.criticalMass = data.getInteger("criticalMass");
	}
	
	//mmmm procedural programming go! (ignoring all of the objects)
	private void getValues() {
		Pair<int[], ItemStack[]> pair;
		
		switch(selection) {
		case 0:
			pair = CoreCasterRecipes.calculateGunStyleValues(slots, 100000);
			consumedItems = pair.getValue();
			quantity = pair.getKey()[0];
			criticalMass = pair.getKey()[1];
			radiation = pair.getKey()[2];
			return;
		case 1:
			pair = CoreCasterRecipes.calculateGunStyleValues(slots, 215000);
			consumedItems = pair.getValue();
			quantity = pair.getKey()[0];
			criticalMass = pair.getKey()[1];
			radiation = pair.getKey()[2];
			return;
		default:
			consumedItems = null;
			quantity = 0;
			criticalMass = 0;
			radiation = 0;
			return;
		}
	}
	
	private void getOutput() {
		if(slots[20] != null)
			return; //Something is occupying the output
		
		switch(selection) {
		case 0:
			slots[20] = CoreCasterRecipes.getGunStyleOutput(ModItems.custom_nuke_target, quantity, criticalMass, radiation);	break;
		case 1:
			slots[20] = CoreCasterRecipes.getGunStyleOutput(ModItems.custom_nuke_bullet, quantity, criticalMass, radiation);	break;
		default:
			return;
		}
		
		if(slots[20] == null)
			return; //Failed to craft
		
		for(int i = 0; i < 20; i++) {
			if(consumedItems[i] != null)
				decrStackSize(i, consumedItems[i].stackSize);
		}
	}
	
	public static void registerHashmaps() {
		gunStyleMap.put(ModItems.ingot_u233, new int[] { 9, 9 * 3333, 5000 }); //U233 block = way too many critical masses
		gunStyleMap.put(ModItems.billet_u233, new int[] { 6, 6 * 3333, 2500 });
		gunStyleMap.put(ModItems.nugget_u233, new int[] { 1, 3333, 500 }); //15 kg
		
		gunStyleMap.put(Item.getItemFromBlock(ModBlocks.block_u235), new int[] { 81, 9 * 9 * 1042, 10000 });
		gunStyleMap.put(ModItems.ingot_u235, new int[] { 9, 9 * 1042, 1000 });
		gunStyleMap.put(ModItems.billet_u235, new int[] { 6, 6 * 1042, 500 });
		gunStyleMap.put(ModItems.nugget_u235, new int[] { 1, 1042, 100 }); //~48 kg; may be 93.5% u235 critical mass
		
		gunStyleMap.put(Item.getItemFromBlock(ModBlocks.block_neptunium), new int[] { 81, 9 * 9 * 943, 25000 });
		gunStyleMap.put(ModItems.ingot_neptunium, new int[] { 9, 9 * 943, 2500 });
		gunStyleMap.put(ModItems.billet_neptunium, new int[] { 6, 6 * 943, 1250 });
		gunStyleMap.put(ModItems.nugget_neptunium, new int[] { 1, 943, 250 }); //57 kg, +/- 4 kg; mercy's sake, 53 kg
	}
	
	//GUI stuff
	
	public boolean getMode() {
		return this.mode;
	}
	
	public int getSelection() {
		return this.selection;
	}
	
	public static List<ItemStack> getOutputsForDisplay() {
		List<ItemStack> outputs = new ArrayList();
		
		outputs.add(new ItemStack(ModItems.custom_nuke_target));
		outputs.add(new ItemStack(ModItems.custom_nuke_bullet));
		outputs.add(new ItemStack(ModItems.gadget_core));
		outputs.add(new ItemStack(ModItems.man_core));
		
		return outputs;
	}
	
	//Control Packets
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("selection")) {
			this.selection = data.getInteger("selection");
			this.slotHasChanged = true;
		}
		
		if(data.hasKey("mode")) {
			this.mode = data.getBoolean("mode");
			//TODO: for relevant items, check if you can actually change mode
		}
		
		if(data.hasKey("craft")) {
			getValues();
			getOutput();
		}
	}
}