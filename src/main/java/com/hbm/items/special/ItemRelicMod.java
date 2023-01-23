package com.hbm.items.special;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.armor.ItemArmorMod;

import net.minecraft.item.ItemStack;

public class ItemRelicMod extends ItemArmorMod {

	public ItemRelicMod() {
		super(ArmorModHandler.extra, false, false, false, false);
	}
	
	
	
	@Override
	public boolean isModApplicable(int armor, int slot, ItemStack mod) {
		return false; //TODO: make dependent on NBT
	}
}
