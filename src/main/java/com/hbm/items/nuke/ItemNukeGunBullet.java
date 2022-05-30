package com.hbm.items.nuke;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNukeGunBullet extends Item {
	
	public ItemNukeGunBullet() {
		this.setMaxStackSize(1);
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
		
		if(stack.hasTagCompound()) {
			//TODO: this tooltip when crafting's added
		}
	}
}
