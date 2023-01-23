package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.stats.StatList;
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
		this.blockIcon = iconRegister.registerIcon(this.getTextureName());
		this.iconPlate = iconRegister.registerIcon(this.getTextureName() + "_plate");
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
		IPersistentNBT.restoreData(world, x, y, z, stack);
		world.markBlockForUpdate(x, y, z);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return IPersistentNBT.getDrops(world, x, y, z, this);
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		
		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			harvesters.set(null);
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}
	
	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityNameplate))
			return;
		
		if(Minecraft.getMinecraft().objectMouseOver.sideHit != te.getBlockMetadata())
			return;
		
		TileEntityNameplate plate = (TileEntityNameplate) te;
		
		String title = plate.titleI18n.isEmpty() ? plate.title : I18nUtil.resolveKey(plate.titleI18n, plate.title);
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
		
		NBTTagCompound nbt = stack.stackTagCompound.getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY);
		
		final String titleI18n = nbt.getString("titleI18n");
		final String title = nbt.getString("title");
		
		if(!titleI18n.isEmpty())
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey(titleI18n, title));
		else if(!title.isEmpty())
			list.add(EnumChatFormatting.YELLOW + title);
		
		final String descI18n = nbt.getString("descI18n");
		final String desc = nbt.getString("desc");
		
		if(!descI18n.isEmpty())
			for(String s : I18nUtil.resolveKeyArray(descI18n, desc)) list.add(s);
		else if(!desc.isEmpty())
			for(String s : desc.split("\\$")) list.add(s);
	}
	
	public static class TileEntityNameplate extends TileEntity implements IPersistentNBT {
		
		public String titleI18n = "";
		public String title = "";
		public String descI18n = "";
		public String desc = "";
		
		public TileEntityNameplate setTitle(String keyTitle, String title) {
			this.titleI18n = keyTitle != null ? keyTitle : "";
			this.title = title != null ? title : "";
			return this;
		}
		
		public TileEntityNameplate setDesc(String keyDesc, String desc) {
			this.descI18n = keyDesc != null ? keyDesc : "";
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
			
			this.titleI18n = nbt.getString("titleI18n");
			this.title = nbt.getString("title");
			this.descI18n = nbt.getString("descI18n");
			this.desc = nbt.getString("desc");
		}
		
		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			
			if(titleI18n != null) nbt.setString("titleI18n", titleI18n);
			if(title != null) nbt.setString("title", title);
			if(descI18n != null) nbt.setString("descI18n", descI18n);
			if(desc != null) nbt.setString("desc", desc);
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

		@Override
		public void writeNBT(NBTTagCompound nbt) {
			NBTTagCompound data = new NBTTagCompound();
			if(titleI18n != null) data.setString("titleI18n", titleI18n);
			if(title != null) data.setString("title", title);
			if(descI18n != null) data.setString("descI18n", descI18n);
			if(desc != null) data.setString("desc", desc);
			
			nbt.setTag(NBT_PERSISTENT_KEY, data);
		}

		@Override
		public void readNBT(NBTTagCompound nbt) {
			NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
			this.titleI18n = data.getString("titleI18n");
			this.title = data.getString("title");
			this.descI18n = data.getString("descI18n");
			this.desc = data.getString("desc");
		}
	}
}
