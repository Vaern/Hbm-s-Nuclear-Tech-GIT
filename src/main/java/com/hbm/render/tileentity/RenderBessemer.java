package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderBessemer extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		
		GL11.glTranslated(0.5D, -3D, 0);
		double rotation = System.currentTimeMillis() / 20D % (90D);
		renderCommon(rotation);
		
		GL11.glPopMatrix();
		
	}
	
	//TODO: figure out why the lighting on the cogs and basins are shafted; not vertex, inverted, etc.
	private void renderCommon(double rot) {
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		bindTexture(new ResourceLocation(RefStrings.MODID + ":block_steel"));
		//bindTexture(ResourceManager.steam_engine_tex);
		ResourceManager.bessemer.renderPart("Supports");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 4.125D, -1.625D);
		GL11.glRotated(rot * 3.2D, 1, 0, 0);
		GL11.glTranslated(0, -4.125D, 1.625D);
		ResourceManager.bessemer.renderPart("Cogs");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 5D, 0);
		GL11.glRotated(rot, -1, 0, 0);
		GL11.glTranslated(0, -5D, 0);
		ResourceManager.bessemer.renderPart("Basin");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_bessemer);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glRotatef(90, 0F, -1F, 0F);
				GL11.glTranslated(0, -1.5, 0);
				double scale = 1.25D;
				GL11.glScaled(scale, scale, scale);
			}
			public void renderNonInv() {
				double scale = 0.5D;
				GL11.glScaled(scale, scale, scale);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glRotatef(90, 0F, 1F, 0F);
				RenderBessemer.this.renderCommon(/*cog ? System.currentTimeMillis() % 3600 * 0.1D : */0);
			}};
	}
	
}
