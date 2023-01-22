package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockNameplate extends BlockContainer implements ILookOverlay, ITooltipProvider {
	
	public BlockNameplate(Material mat) {
		super(mat);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityNameplate();
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon iconPlate;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconPlate = iconRegister.registerIcon(getTextureName() + "_plate");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == metadata ? iconPlate : blockIcon;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		/* remove this later*/
		if(!(te instanceof TileEntityNameplate))
			return;
		
		TileEntityNameplate hah = (TileEntityNameplate) te;
		hah.title = "lol,";
		hah.desc = "lmao";
		/* */
		/*if(!(te instanceof TileEntityNameplate && stack.hasTagCompound()))
			return;
		
		TileEntityNameplate plate = (TileEntityNameplate) te;
		NBTTagCompound nbt = stack.stackTagCompound;
		
		plate.titleI18n = nbt.getString("titleI18n");
		plate.title = nbt.getString("title");
		plate.descI18n = nbt.getString("descI18n");
		plate.desc = nbt.getString("desc");*/
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		
		if(!player.capabilities.isCreativeMode && !world.isRemote && willHarvest) {
			TileEntity te = world.getTileEntity(x, y, z);
			
			ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
			
			if(te instanceof TileEntityNameplate) {
				TileEntityNameplate plate = (TileEntityNameplate) te;
				NBTTagCompound nbt = new NBTTagCompound();
				//TODO: figure out why the strings are always empty when harvested
				/* remember kids, the nbt methods save coords and id! you don't want that interfering */
				if(plate.titleI18n != null) nbt.setString("titleI18n", plate.titleI18n);
				if(plate.title != null) nbt.setString("title", plate.title);
				if(plate.descI18n != null) nbt.setString("descI18n", plate.descI18n);
				if(plate.desc != null) nbt.setString("desc", plate.desc);
				
				stack.stackTagCompound = nbt;
			}
			
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack));
		}
		
		return world.setBlockToAir(x, y, z);
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}
	
	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityNameplate))
			return;
		
		if(Minecraft.getMinecraft().objectMouseOver.sideHit != te.blockMetadata)
			return;
		
		TileEntityNameplate plate = (TileEntityNameplate) te;
		
		String title = plate.descI18n.isEmpty() ? plate.title : I18nUtil.resolveKey(plate.titleI18n, plate.title);
		List<String> text = new ArrayList();
		
		if(!plate.descI18n.isEmpty())
			for(String line : I18nUtil.resolveKeyArray(plate.descI18n, plate.desc)) text.add(line);
		else
			for(String line : plate.desc.split("\\$")) text.add(line);
		
		ILookOverlay.printGeneric(event, title, 0xffff00, 0x404000, text);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		if(!stack.hasTagCompound())
			return;
		
		NBTTagCompound nbt = stack.stackTagCompound;
		
		final String titleI18n = nbt.getString("titleI18n");
		final String title = nbt.getString("title");
		
		if(!titleI18n.isEmpty())
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey(titleI18n, title));
		else
			list.add(EnumChatFormatting.YELLOW + title);
		
		final String descI18n = nbt.getString("descI18n");
		final String desc = nbt.getString("desc");
		
		if(!descI18n.isEmpty())
			for(String s : I18nUtil.resolveKeyArray(descI18n, desc)) list.add(s);
		else
			for(String s : desc.split("\\$")) list.add(s);
	}
	
	public static class TileEntityNameplate extends TileEntity {
		
		public String titleI18n = "";
		public String title = "";
		public String descI18n = "";
		public String desc = "";
		
		public TileEntityNameplate setLocalization(String keyTitle, String keyDesc) {
			this.titleI18n = keyTitle != null ? keyTitle : "";
			this.descI18n = keyDesc != null ? keyDesc : "";
			return this;
		}
		
		public TileEntityNameplate setCustomStrings(String title, String desc) {
			this.title = title != null ? title : "";
			this.desc = desc != null ? desc : "";
			return this;
		}
		
		@Override
		public boolean canUpdate() {
			return false;
		}
		
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			
			if(titleI18n != null) nbt.setString("titleI18n", titleI18n);
			if(title != null) nbt.setString("title", title);
			if(descI18n != null) nbt.setString("descI18n", descI18n);
			if(desc != null) nbt.setString("desc", desc);
		}
		
		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			
			this.titleI18n = nbt.getString("titleI18n");
			this.title = nbt.getString("title");
			this.descI18n = nbt.getString("descI18n");
			this.desc = nbt.getString("desc");
		}
		
		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}
		
		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}
	}
}
