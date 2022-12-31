package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerBessemer;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityBessemer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIBessemer extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_bessemer.png");
	private TileEntityBessemer bessemer;

	public GUIBessemer(InventoryPlayer invPlayer, TileEntityBessemer tedf) {
		super(new ContainerBessemer(invPlayer, tedf));
		bessemer = tedf;
		
		this.xSize = 190;
		this.ySize = 207;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.bessemer.hasCustomInventoryName() ? this.bessemer.getInventoryName() : I18n.format(this.bessemer.getInventoryName());
		
		this.fontRendererObj.drawString(name, 65 - this.fontRendererObj.getStringWidth(name) / 2, 12, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		
		
	}
	
}
