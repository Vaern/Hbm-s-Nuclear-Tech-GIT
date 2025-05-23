// Date: 10.12.2015 21:01:30
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSteelRoof extends ModelBase {

	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;

	public ModelSteelRoof() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.Shape1 = new ModelRenderer(this, 0, 0);
		this.Shape1.addBox(0F, 0F, 0F, 16, 1, 16);
		this.Shape1.setRotationPoint(-8F, 23F, -8F);
		this.Shape1.setTextureSize(64, 32);
		this.Shape1.mirror = true;
		setRotation(this.Shape1, 0F, 0F, 0F);
		this.Shape2 = new ModelRenderer(this, 30, 15);
		this.Shape2.addBox(0F, 0F, 0F, 1, 1, 16);
		this.Shape2.setRotationPoint(-3F, 22F, -8F);
		this.Shape2.setTextureSize(64, 32);
		this.Shape2.mirror = true;
		setRotation(this.Shape2, 0F, 0F, 0F);
		this.Shape3 = new ModelRenderer(this, 0, 17);
		this.Shape3.addBox(0F, 0F, 0F, 16, 2, 2);
		this.Shape3.setRotationPoint(-8F, 21F, 2F);
		this.Shape3.setTextureSize(64, 32);
		this.Shape3.mirror = true;
		setRotation(this.Shape3, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		this.renderModel(scaleFactor);
	}

	public void renderModel(float scaleFactor) {

		this.Shape1.render(scaleFactor);
		this.Shape2.render(scaleFactor);
		this.Shape3.render(scaleFactor);
	}

	private static void setRotation(ModelRenderer model, float x, float y, float z) {

		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
