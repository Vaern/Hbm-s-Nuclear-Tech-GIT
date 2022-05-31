package com.hbm.items.nuke;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ItemNukeGunBullet extends Item {
	
	public ItemNukeGunBullet() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		list.add(new ItemStack(item, 1, 0));
		
		//there's no fucking information on the unreflected critical mass of 80% HEU, so 1042 * 0.8 here we go
		if(item == ModItems.custom_nuke_bullet)
			list.add(getCustomNBT(item, 1, 77, 834)); //little boy 38.53 kg/30.82 kg pure
		if(item == ModItems.custom_nuke_target)
			list.add(getCustomNBT(item, 1, 51, 834)); //little boy 25.6 kg/20.48 kg pure
	}
	
	//Static utility method for prefabs
	public static ItemStack getCustomNBT(Item item, int meta, int weight, int criticalmass) {
		ItemStack output = new ItemStack(item, 1, meta);
		output.stackTagCompound = new NBTTagCompound();
		output.stackTagCompound.setInteger("quantity", weight);
		output.stackTagCompound.setInteger("criticalMass", weight * criticalmass);
		
		return output;
	}
	
	private IIcon icon;
	//TODO: Boron textures/ability to add them in core caster
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg); //For boron sabots/Inserts
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass) {
		return super.getIcon(stack, pass);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		return super.getIconIndex(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(stack, player, list, bool);
		
		String unloc = "item.custom_nuke_gunstyle.desc";
		unloc = I18nUtil.resolveKey(unloc);
		list.add(unloc);
		
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.stackTagCompound;

			unloc = "item.custom_nuke_gunstyle.nbt";
			double[] values = { (double)nbt.getInteger("quantity") * 0.5D, (double)nbt.getInteger("criticalMass") / 100000D };
			
			String[] locs = I18nUtil.resolveKeyArray(unloc, values[0], values[1]);

			for(String loc : locs) {
				list.add(loc);
			}
		}
	}
}
