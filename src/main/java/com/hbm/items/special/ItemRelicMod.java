package com.hbm.items.special;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.armor.ItemArmorMod;
import com.hbm.util.EnumUtil;
import com.hbm.util.Tuple.Pair;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemRelicMod extends ItemArmorMod {

	public ItemRelicMod() {
		super(ArmorModHandler.extra, false, false, false, false);
	}
	
	@Override
	public boolean isModApplicable(int armor, int slot, ItemStack mod) {
		return false; //TODO: make dependent on NBT
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor, ItemStack mod) {
		
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor, ItemStack mod) {
		
	}
	
	@Override
	public Multimap getModifiers(ItemStack armor, ItemStack mod) {
		Multimap multimap = super.getAttributeModifiers(armor);
				
		for(Pair<TraitEnum, Double> trait : getTraitsNBT(mod)) {
			
		}
		
		return multimap;
	}
	
	/* like everything related to implementation of traits, subject to change */
	private ArrayList<Pair<TraitEnum, Double>> getTraitsNBT(ItemStack mod) {
		ArrayList traits = new ArrayList<Pair<TraitEnum, Double>>();
		
		if(mod == null) return traits; //reconsider
		if(!mod.hasTagCompound()) return traits;
		
		NBTTagCompound tag = mod.stackTagCompound.getCompoundTag("traits");
		if(tag.hasNoTags()) return traits;
		
		final int count = tag.getInteger("trait_number");
		
		for(int i = 0; i < count; i++) {
			final int ordinal = tag.getInteger("t_eff" + i);
			final double magnitude = tag.getDouble("t_mag" + i);
			
			traits.add(new Pair(EnumUtil.grabEnumSafely(TraitEnum.class, ordinal), magnitude));
		}
		
		return traits;
	}
	
	//Might need to change this to take the tag directly to prevent redundant checks when generating
	private void addTraitNBT(ItemStack mod, TraitEnum effect, double magnitude) {
		if(mod == null) return; //reconsider
		if(!mod.hasTagCompound()) return;
		
		NBTTagCompound tag = mod.stackTagCompound.getCompoundTag("traits");
		if(tag.hasNoTags()) return;
		
		final int count = tag.getInteger("trait_number") + 1;
		tag.setInteger("trait_number", count);
		
		tag.setInteger("t_eff" + count, effect.ordinal());
		tag.setDouble("t_mag" + count, magnitude);
		
		mod.stackTagCompound.setTag("traits", tag);
	}
		
	private void removeTraitsNBT(ItemStack mod, int... remove) {
		if(mod == null) return; //reconsider
		if(!mod.hasTagCompound()) return;
		
		NBTTagCompound tag = mod.stackTagCompound.getCompoundTag("traits");
		if(tag.hasNoTags()) return;
		
		for(int index : remove) {
			tag.removeTag("t_eff" + index);
			tag.removeTag("t_mag" + index);
		}
		
		final int count = tag.getInteger("trait_number");
		int validIndex = 0; //which index to shift to, or to not shift at all if equal
		
		for(int i = 0; i < count; i++) {
			
			if(!tag.hasKey("t_eff" + i) || !tag.hasKey("t_mag" + i)) continue;
			
			if(validIndex < i) {
				final int ordinal = tag.getInteger("t_eff" + i);
				final double magnitude = tag.getDouble("t_mag" + i);
				
				tag.setInteger("t_eff" + validIndex, ordinal);
				tag.setDouble("t_mag" + validIndex, magnitude);
			}
			
			validIndex++;
			
			if(i >= count - remove.length) { //get rid of any straggler tags
				tag.removeTag("t_eff" + i);
				tag.removeTag("t_mag" + i);
			}
		}
		
		tag.setInteger("trait_number", count - remove.length); //update amount
		mod.stackTagCompound.setTag("traits", tag);
	}
	
	public enum StyleEnum {
		MAGICAL,
		MYSTICAL,
		MECHANICAL,
		CYBERNETIC
	}
	
	/*private static HashMultimap lambdaMap = HashMultimap.create();
	
	static {
		lambdaMap.put(TraitEnum.MAX_HEALTH, null); //todo fix this
	}
	
	@FunctionalInterface
	private static interface ModUpdate {
		public void onModUpdate(EntityLivingBase entity, ItemStack armor, ItemStack mod);
	}
	
	@FunctionalInterface
	private static interface ModDamage {
		public void onModDamage(LivingHurtEvent event, ItemStack armor, ItemStack mod);
	}
	
	@FunctionalInterface
	private static interface Modifiers {
		public void getModifiers(Multimap map, double effect);
	}*/
	
	public enum TraitEnum {
		MAX_HEALTH, //basic sharedmonsterattribute-related traits, proof of concept mostly
		KNOCKBACK,
		SPEED,
		DAMAGE
	}
}
