package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeAssembler;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineNukeAssembler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUINukeAssembler extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_nuke_assembler.png");
	private TileEntityMachineNukeAssembler assembler;
	
	public GUINukeAssembler(InventoryPlayer invPlayer, TileEntityMachineNukeAssembler tedf) {
		super(new ContainerNukeAssembler(invPlayer, tedf));
		assembler = tedf;
		
		this.xSize = 190;
		this.ySize = 253;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//String name = this.caster.hasCustomInventoryName() ? this.caster.getInventoryName() : I18n.format(this.caster.getInventoryName());
		final String[] labels = { "Weight", "Yield" };
		
		//this.fontRendererObj.drawString(name, 87 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 15, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString(labels[0], 160 - this.fontRendererObj.getStringWidth(labels[0]) / 2, 7, 15066597);
		this.fontRendererObj.drawString(labels[1], 160 - this.fontRendererObj.getStringWidth(labels[1]) / 2, 39, 15066597);
		
		//this.fontRendererObj.drawString(Double.toString((double)caster.quantity * 0.5D), 139, 22, 16766976, false);
		//this.fontRendererObj.drawString(Double.toString((double)caster.criticalMass / 100000D), 139, 54, 16766976, false);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float inter, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
	}
	
	
	
}
