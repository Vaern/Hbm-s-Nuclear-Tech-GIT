package com.hbm.items.special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.armor.ItemArmorMod;
import com.hbm.util.EnumUtil;
import com.hbm.util.Tuple.Pair;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemRelicMod extends ItemArmorMod {

	public ItemRelicMod() {
		super(ArmorModHandler.extra, true, true, true, true);
	}
	
	@Override
	public boolean isModApplicable(int armor, int slot, ItemStack mod) {
		return slot == ArmorModHandler.extra; //TODO: make dependent on NBT
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor, ItemStack mod) {
		if(!mod.hasTagCompound()) return;
		
		for(NBTTagCompound tag : getTraitsFromNBT(mod.stackTagCompound)) {
			TraitEnum num = EnumUtil.grabEnumSafely(TraitEnum.class, tag.getInteger(ENUM_KEY));
			
			if(num.update != null) num.update.onModUpdate(entity, armor, tag);
		}
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor, ItemStack mod) {
		if(!mod.hasTagCompound()) return;
		
		for(NBTTagCompound tag : getTraitsFromNBT(mod.stackTagCompound)) {
			TraitEnum num = EnumUtil.grabEnumSafely(TraitEnum.class, tag.getInteger(ENUM_KEY));
			
			if(num.damage != null) num.damage.onModDamage(event, armor, tag);
		}
	}
	
	@Override
	public Multimap getModifiers(ItemStack armor, ItemStack mod) {
		Multimap multimap = super.getAttributeModifiers(armor);
		if(!mod.hasTagCompound()) return multimap;
		
		for(NBTTagCompound tag : getTraitsFromNBT(mod.stackTagCompound)) {
			TraitEnum num = EnumUtil.grabEnumSafely(TraitEnum.class, tag.getInteger(ENUM_KEY));
			
			if(num.mod != null) num.mod.getModifiers(multimap, armor, tag);
		}
		
		return multimap;
	}
	
	/** TEST */
	public static ItemStack generateRelic(Random rand, Item item) {
		ItemStack stack = new ItemStack(item);
		stack.stackTagCompound = new NBTTagCompound();
		
		NBTTagCompound[] tags = new NBTTagCompound[3];
		
		for(int i = 0; i < 3; i++) {
			tags[i] = new NBTTagCompound();
			
			final TraitEnum num = EnumUtil.grabEnumSafely(TraitEnum.class, rand.nextInt());
			final double mag = rand.nextDouble() * 2;
			final int mode = Math.abs(rand.nextInt() % 3);
			System.out.println(num.name() + ", Magnitude: " + mag + ", Mode: " + mode);
			
			tags[i].setInteger(ENUM_KEY, num.ordinal());
			tags[i].setDouble("effect", mag);
			tags[i].setInteger("mode", mode);
		}
		
		addTraitsToNBT(stack.stackTagCompound, tags);
		return stack;
	}
	
	/*private static final String T_COMPOUND_KEY = "traits";
	private static final String T_EFFECT_KEY = "t_eff_";
	private static final String T_MAGNITUDE_KEY = "t_mag_";*/
	
	/** balls */
	private static final String TRAIT_KEY = "trait_list";
	private static final String ENUM_KEY = "enum_ordinal";
	
	private static ArrayList<NBTTagCompound> getTraitsFromNBT(NBTTagCompound nbt) {
		if(nbt == null) return new ArrayList();
		
		NBTTagList tagList = nbt.getTagList(TRAIT_KEY, 10); //taglist of tagcompounds
		ArrayList<NBTTagCompound> list = new ArrayList<NBTTagCompound>();
		
		for(int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			if(!tag.hasNoTags()) list.add(tag);
		}
		
		return list;
	}
	
	private static void addTraitsToNBT(NBTTagCompound nbt, NBTTagCompound...traits) {
		if(nbt == null || traits == null) return;
		
		NBTTagList tagList = nbt.getTagList(TRAIT_KEY, 10);
		
		for(int i = 0; i < traits.length; i++) {
			if(!traits[i].hasNoTags()) tagList.appendTag(traits[i]);
		}
		
		nbt.setTag(TRAIT_KEY, tagList);
	}
	
	private static void removeTraitsFromNBT(NBTTagCompound nbt, int...indices) {
		if(nbt == null || indices == null) return;
		
		NBTTagList tagList = nbt.getTagList(TRAIT_KEY, 10);
		
		for(int i = 0; i < indices.length; i++) {
			tagList.removeTag(i);
		}
		
		nbt.setTag(TRAIT_KEY, tagList);
	}
	
	public static enum StyleEnum {
		MAGICAL,
		MYSTICAL,
		MECHANICAL,
		CYBERNETIC;
	}
	
	public static enum TraitEnum {
		//TODO: create a manager / handler for this instead of stuffing it all into here. Could work in/with ArmorModHandler?
		//basic sharedmonsterattribute-related traits, proof of concept mostly
		MAX_HEALTH((map, a, tag) -> map.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)a.getItem()).armorType],
				"NTM Armor Mod Health", tag.getDouble("effect"), tag.getInteger("mode")))),
		KNOCKBACK((map, a, tag) -> map.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)a.getItem()).armorType],
				"NTM Armor Mod Knockback", tag.getDouble("effect"), tag.getInteger("mode")))),
		SPEED((map, a, tag) -> map.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)a.getItem()).armorType],
				"NTM Armor Mod Speed", tag.getDouble("effect"), tag.getInteger("mode")))),
		DAMAGE((map, a, tag) -> map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)a.getItem()).armorType],
				"NTM Armor Mod Damage", tag.getDouble("effect"), tag.getInteger("mode"))));
		
		protected ModUpdate update;
		protected ModDamage damage;
		protected Modifiers mod;
		
		private TraitEnum(ModUpdate update, ModDamage damage, Modifiers mod) {
			this.update = update;
			this.damage = damage;
			this.mod = mod;
		}
		
		private TraitEnum(Modifiers mod) { this(null, null, mod); }
	}
	
	@FunctionalInterface
	private static interface ModUpdate {
		public void onModUpdate(EntityLivingBase entity, ItemStack armor, NBTTagCompound tag);
	}
	
	@FunctionalInterface
	private static interface ModDamage {
		public void onModDamage(LivingHurtEvent event, ItemStack armor, NBTTagCompound tag);
	}
	
	@FunctionalInterface
	private static interface Modifiers {
		public void getModifiers(Multimap map, ItemStack armor, NBTTagCompound tag);
	}
	
	/** balls*/
	
	/* like everything related to implementation of traits, subject to change */
	/*private static ArrayList<Pair<TraitEnum, Double>> getTraitsNBT(ItemStack mod) {
		ArrayList traits = new ArrayList<Pair<TraitEnum, Double>>();
		
		if(mod == null) return traits; //reconsider
		if(!mod.hasTagCompound()) return traits;
		
		NBTTagCompound tag = mod.stackTagCompound.getCompoundTag(T_COMPOUND_KEY);
		if(tag.hasNoTags()) return traits;
		
		final int count = tag.getInteger("trait_number");
		
		for(int i = 0; i < count; i++) {
			final int ordinal = tag.getInteger(T_EFFECT_KEY + i);
			final double magnitude = tag.getDouble(T_MAGNITUDE_KEY + i); //might be worth it to have a 'mode' one but idk about that
			
			traits.add(new Pair(EnumUtil.grabEnumSafely(TraitEnum.class, ordinal), magnitude));
		}
		
		return traits;
	}
	
	//Might need to change this to take the tag directly to prevent redundant checks when generating
	private static void addTraitNBT(ItemStack mod, TraitEnum effect, double magnitude) {
		if(mod == null) return; //reconsider
		if(!mod.hasTagCompound()) return;
		
		NBTTagCompound tag = mod.stackTagCompound.getCompoundTag(T_COMPOUND_KEY);
		
		final int count = tag.getInteger("trait_number") + 1;
		tag.setInteger("trait_number", count);
		
		tag.setInteger(T_EFFECT_KEY + (count - 1), effect.ordinal());
		tag.setDouble(T_MAGNITUDE_KEY + (count - 1), magnitude);
		
		mod.stackTagCompound.setTag(T_COMPOUND_KEY, tag);
	}
	
	private static void removeTraitsNBT(ItemStack mod, int... remove) {
		if(mod == null) return; //reconsider
		if(!mod.hasTagCompound()) return;
		
		NBTTagCompound tag = mod.stackTagCompound.getCompoundTag(T_COMPOUND_KEY);
		if(tag.hasNoTags()) return;
		
		for(int index : remove) {
			tag.removeTag(T_EFFECT_KEY + index);
			tag.removeTag(T_MAGNITUDE_KEY + index);
		}
		
		final int count = tag.getInteger("trait_number");
		int validIndex = 0; //which index to shift to, or to not shift at all if equal
		
		for(int i = 0; i < count; i++) {
			
			if(!tag.hasKey(T_EFFECT_KEY + i) || !tag.hasKey(T_MAGNITUDE_KEY + i)) continue;
			
			if(validIndex < i) {
				final int ordinal = tag.getInteger(T_EFFECT_KEY + i);
				final double magnitude = tag.getDouble(T_MAGNITUDE_KEY + i);
				
				tag.setInteger(T_EFFECT_KEY + validIndex, ordinal);
				tag.setDouble(T_MAGNITUDE_KEY + validIndex, magnitude);
			}
			
			validIndex++;
			
			if(i >= count - remove.length) { //get rid of any straggler tags
				tag.removeTag(T_EFFECT_KEY + i);
				tag.removeTag(T_MAGNITUDE_KEY + i);
			}
		}
		
		tag.setInteger("trait_number", count - remove.length); //update amount
		mod.stackTagCompound.setTag(T_COMPOUND_KEY, tag);
	}*/
	
	/*private static final String STYLE_KEY = "style_";
	
	//0 for primary, 1 for secondary
	private static void setStyleNBT(ItemStack mod, StyleEnum num, int priority) {
		if(mod == null) return; //reconsider
		if(!mod.hasTagCompound()) return;
		
		mod.stackTagCompound.setInteger(STYLE_KEY + priority, num.ordinal());
	}
	
	private static int[] getStylesNBT(ItemStack mod) {
		int[] styles = new int[2];
		
		if(mod == null) return styles; //reconsider
		if(!mod.hasTagCompound()) return styles;
		
		for(int i = 0; i <= 1; i++) {
			if(mod.stackTagCompound.hasKey(STYLE_KEY + i))
				styles[i] = mod.stackTagCompound.getInteger(STYLE_KEY + i);
		}
		
		return styles;
	}*/
	
	//might actually be optimal to make a full 32 (4 * 8) UUID-strong thing for modifiers. hell? yes. flexible? also yes.
	
	/*private static HashMap<TraitEnum, Modifiers> modifierTraits = new HashMap<TraitEnum, Modifiers>();
	
	static {
	//UUIDs are stinky and annoying, so unless we limit it to the special slot, we'd need 16 (4 armor slots per armor)
		modifierTraits.put(TraitEnum.MAX_HEALTH, (map, effect, armor, mod) -> map.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), 
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)armor.getItem()).armorType], "NTM Armor Mod Health", 4, 0)));
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
		public void getModifiers(Multimap map, double effect, ItemStack armor, ItemStack mod);
	}*/
}
