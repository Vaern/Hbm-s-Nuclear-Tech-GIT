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
	int selection;
	
	public GUICoreCaster(InventoryPlayer invPlayer, TileEntityMachineCoreCaster tedf) {
		super(new ContainerCoreCaster(invPlayer, tedf));
		caster = tedf;
		
		this.xSize = 245;
		this.ySize = 195;
		
		this.selection = caster.getSelection();
		this.recipes = caster.getOutputsForDisplay();
		this.size = this.recipes.size() - 3;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.caster.hasCustomInventoryName() ? this.caster.getInventoryName() : I18n.format(this.caster.getInventoryName());
		final String[] labels = { "Weight", "Critical", "Mass" };
		
		this.fontRendererObj.drawString(name, 87 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString(labels[0], 210 - this.fontRendererObj.getStringWidth(labels[0]) / 2, 5, 15066597);
		this.fontRendererObj.drawString(labels[1], 210 - this.fontRendererObj.getStringWidth(labels[1]) / 2, 36, 15066597);
		this.fontRendererObj.drawString(labels[2], 210 - this.fontRendererObj.getStringWidth(labels[2]) / 2, 45, 15066597);
		
		this.fontRendererObj.drawString(Double.toString((double)caster.quantity * 0.5D), 189, 21, 65313, false);
		this.fontRendererObj.drawString(Double.toString((double)caster.criticalMass / 100000D), 189, 61, 65313, false);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int k) {
		super.mouseClicked(x, y, k);
		
		if(guiLeft + 177 <= x && guiLeft + 177 + 7 > x && guiTop + 96 < y && guiTop + 96 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.index > 0)
				this.index--;
			
			return;
		}
		
		if(guiLeft + 238 <= x && guiLeft + 238 + 7 > x && guiTop + 96 < y && guiTop + 96 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.index < this.size)
				this.index++;
			
			return;
		}
		
		for(int i = index; i < index + 3; i++) {
			
			if(i >= this.recipes.size())
				break;
			
			int ind = i - index;
			
			int ix = 184 + 18 * ind;
			if(guiLeft + ix <= x && guiLeft + ix + 18 > x && guiTop + 96 < y && guiTop + 96 + 18 >= y) {
				
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
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
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
			itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), display, guiLeft + 185 + 18 * ind, guiTop + 97);
			itemRender.zLevel = 0.0F;
			
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			this.zLevel = 300.0F;
			
			if(selection == i)
				this.drawTexturedModalRect(guiLeft + 184 + 18 * ind, guiTop + 96, 0, 195, 18, 18);
		}
	}
}