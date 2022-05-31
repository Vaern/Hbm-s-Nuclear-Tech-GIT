package com.hbm.inventory.recipes;

import com.hbm.tileentity.machine.TileEntityMachineCoreCaster;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.ItemStack;

public class CoreCasterRecipes {
	
	public static Pair<int[], ItemStack[]> calculateGunStyleValues(ItemStack[] inputs, int criticalLimit) {
		ItemStack[] consumed = new ItemStack[inputs.length];
		int quantity = 0;
		int criticalMass = 0;
		for(byte i = 6; i < 12; i++) {
			if(inputs[i] == null)
				continue;
			
			int[] values = TileEntityMachineCoreCaster.gunStyleMap.get(inputs[i].getItem()).clone(); //ha ha
			
			if(values == null)
				continue;
			
			//NWFAQ; this is the *unreflected* limit, reflection allows for a higher assembled value
			if(criticalMass + values[1] * inputs[i].stackSize < criticalLimit) {
				quantity += values[0] * inputs[i].stackSize;
				criticalMass += values[1] * inputs[i].stackSize;
				consumed[i] = inputs[i].copy();
			} else {
				return new Pair<>(new int[] { 0, 0 }, null);
			}
		}
		
		return new Pair<>(new int[] { quantity, criticalMass }, consumed);
	}
	
	
	
}
