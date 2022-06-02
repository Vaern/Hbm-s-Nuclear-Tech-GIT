package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeAssembler;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineNukeAssembler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUINukeAssembler extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_core_caster.png");
	private TileEntityMachineNukeAssembler assembler;
	
	public GUINukeAssembler(InventoryPlayer invPlayer, TileEntityMachineNukeAssembler tedf) {
		super(new ContainerNukeAssembler(invPlayer, tedf));
		assembler = tedf;
		
		this.xSize = 190;
		this.ySize = 253;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float inter, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
	}
	
	
	
}
