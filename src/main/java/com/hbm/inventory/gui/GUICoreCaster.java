package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCoreCaster;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilConstructionRecipe;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AnvilCraftPacket;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineCoreCaster;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUICoreCaster extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_core_caster.png");
	private TileEntityMachineCoreCaster caster;
	
	List<ItemStack> recipes = new ArrayList();
	int size;
	int index;
	int selection; //what's selected at the moment?
	boolean mode; //for alt button
	
	public GUICoreCaster(InventoryPlayer invPlayer, TileEntityMachineCoreCaster tedf) {
		super(new ContainerCoreCaster(invPlayer, tedf));
		caster = tedf;
		
		this.xSize = 190;
		this.ySize = 253;
		
		this.mode = caster.getMode();
		this.selection = caster.getSelection();
		this.recipes = caster.getOutputsForDisplay();
		this.size = this.recipes.size() - 3;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] infoText = I18nUtil.resolveKeyArray("desc.gui.coreCaster." + selection);
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 131, guiTop + 135, 59, 16, mouseX, mouseY, infoText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//String name = this.caster.hasCustomInventoryName() ? this.caster.getInventoryName() : I18n.format(this.caster.getInventoryName());
		final String[] labels = { "Weight", "Criticality" };
		
		//this.fontRendererObj.drawString(name, 87 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 15, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString(labels[0], 160 - this.fontRendererObj.getStringWidth(labels[0]) / 2, 7, 15066597);
		this.fontRendererObj.drawString(labels[1], 160 - this.fontRendererObj.getStringWidth(labels[1]) / 2, 39, 15066597);
		
		this.fontRendererObj.drawString(Double.toString((double)caster.quantity * 0.5D), 139, 22, 16766976, false);
		this.fontRendererObj.drawString(Double.toString((double)caster.criticalMass / 100000D), 139, 54, 16766976, false);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int k) {
		super.mouseClicked(x, y, k);
		
		if(guiLeft + 4 <= x && guiLeft + 4 + 13 > x && guiTop + 113 < y && guiTop + 113 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.index > 0)
				this.index--;
			
			return;
		}
		
		if(guiLeft + 71 <= x && guiLeft + 71 + 13 > x && guiTop + 113 < y && guiTop + 113 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.index < this.size)
				this.index++;
			
			return;
		}
		
		if(guiLeft + 90 <= x && guiLeft + 90 + 28 > x && guiTop + 99 < y && guiTop + 99 + 28 >= y) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setBoolean("craft", true);
			
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(nbt, caster.xCoord, caster.yCoord, caster.zCoord));
			return;
		}
		
		if(guiLeft + 91 <= x && guiLeft + 91 + 26 > x && guiTop + 130 < y && guiTop + 130 + 15 >= y) {
			this.mode = !mode;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setBoolean("mode", mode);
			
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(nbt, caster.xCoord, caster.yCoord, caster.zCoord));
			return;
		}
		
		for(int i = index; i < index + 3; i++) {
			
			if(i >= this.recipes.size())
				break;
			
			int ind = i - index;
			
			int ix = 17 + 18 * ind;
			if(guiLeft + ix <= x && guiLeft + ix + 18 > x && guiTop + 113 < y && guiTop + 113 + 19 >= y) {
				
				if(this.selection != i)
					this.selection = i;
				else
					this.selection = -1;
				
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("selection", this.selection);
				
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(nbt, caster.xCoord, caster.yCoord, caster.zCoord));
				return;
			}
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float inter, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		
		if(mode) //Alt Button
			this.drawTexturedModalRect(guiLeft + 91, guiTop + 131, 213, 15, 26, 15);
		
		if(guiLeft + 90 <= mX && guiLeft + 90 + 28 > mX && guiTop + 99 < mY && guiTop + 99 + 28 >= mY) //Craft Button
			this.drawTexturedModalRect(guiLeft + 93, guiTop + 103, 190, 15, 22, 22);
		
		if(guiLeft + 4 <= mX && guiLeft + 4 + 13 > mX && guiTop + 113 < mY && guiTop + 113 + 18 >= mY) //Left Arrow
			this.drawTexturedModalRect(guiLeft + 7, guiTop + 117, 190, 0, 7, 12);
		
		if(guiLeft + 71 <= mX && guiLeft + 71 + 13 > mX && guiTop + 113 < mY && guiTop + 113 + 18 >= mY) //Right Arrow
			this.drawTexturedModalRect(guiLeft + 74, guiTop + 117, 231, 0, 7, 12);
		
		if(guiLeft + 131 <= mX && guiLeft + 131 + 59 > mX && guiTop + 135 < mY && guiTop + 135 + 16 >= mY) //Info
			this.drawTexturedModalRect(guiLeft + 131, guiTop + 136, 190, 38, 59, 16);
		
		for(int i = index; i < index + 3; i++) {
			if(i >= recipes.size())
				break;
			
			int ind = i - index;
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			
			ItemStack display = recipes.get(i);
			
			FontRenderer font = null;
			if (display != null) font = display.getItem().getFontRenderer(display);
			if (font == null) font = fontRendererObj;
			
			itemRender.zLevel = 100.0F;
			itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), display, guiLeft + 18 + 18 * ind, guiTop + 115);
			itemRender.zLevel = 0.0F;
			
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			this.zLevel = 300.0F;
			
			if(selection == i)
				this.drawTexturedModalRect(guiLeft + 17 + 18 * ind, guiTop + 114, 190, 55, 18, 18);
		}
	}
}