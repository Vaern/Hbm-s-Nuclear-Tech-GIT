// Date: 31.01.2017 19:23:38
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMultitoolFist extends ModelBase {

	ModelRenderer Base;
	ModelRenderer BTop;
	ModelRenderer BBottom;
	ModelRenderer BLeft;
	ModelRenderer BRight;
	ModelRenderer RTop;
	ModelRenderer RBottom;
	ModelRenderer RLeft;
	ModelRenderer RRight;
	ModelRenderer GPivot;
	ModelRenderer GBase;
	ModelRenderer F31;
	ModelRenderer F21;
	ModelRenderer F41;
	ModelRenderer F51;
	ModelRenderer F11;
	ModelRenderer F22;
	ModelRenderer F32;
	ModelRenderer F42;
	ModelRenderer F52;
	ModelRenderer F12;
	ModelRenderer F23;
	ModelRenderer F33;
	ModelRenderer F43;
	ModelRenderer F53;
	ModelRenderer F13;
	ModelRenderer WireL;
	ModelRenderer WireR;
	ModelRenderer Gauge1;
	ModelRenderer Gauge2;
	ModelRenderer WireB;

	public ModelMultitoolFist() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.Base = new ModelRenderer(this, 0, 0);
		this.Base.addBox(0F, 0F, 0F, 3, 8, 8);
		this.Base.setRotationPoint(-3F, -4F, -4F);
		this.Base.setTextureSize(64, 64);
		this.Base.mirror = true;
		setRotation(this.Base, 0F, 0F, 0F);
		this.BTop = new ModelRenderer(this, 0, 16);
		this.BTop.addBox(0F, 0F, 0F, 4, 2, 8);
		this.BTop.setRotationPoint(-3F, -4F, -4F);
		this.BTop.setTextureSize(64, 64);
		this.BTop.mirror = true;
		setRotation(this.BTop, 0F, 0F, -0.2617994F);
		this.BBottom = new ModelRenderer(this, 0, 26);
		this.BBottom.addBox(0F, -2F, 0F, 4, 2, 8);
		this.BBottom.setRotationPoint(-3F, 4F, -4F);
		this.BBottom.setTextureSize(64, 64);
		this.BBottom.mirror = true;
		setRotation(this.BBottom, 0F, 0F, 0.2617994F);
		this.BLeft = new ModelRenderer(this, 0, 36);
		this.BLeft.addBox(0F, 0F, 0F, 4, 8, 2);
		this.BLeft.setRotationPoint(-3F, -4F, -4F);
		this.BLeft.setTextureSize(64, 64);
		this.BLeft.mirror = true;
		setRotation(this.BLeft, 0F, 0.2617994F, 0F);
		this.BRight = new ModelRenderer(this, 12, 36);
		this.BRight.addBox(0F, 0F, -2F, 4, 8, 2);
		this.BRight.setRotationPoint(-3F, -4F, 4F);
		this.BRight.setTextureSize(64, 64);
		this.BRight.mirror = true;
		setRotation(this.BRight, 0F, -0.2617994F, 0F);
		this.RTop = new ModelRenderer(this, 24, 0);
		this.RTop.addBox(0F, 0F, 0F, 3, 2, 10);
		this.RTop.setRotationPoint(4F, -6F, -6F);
		this.RTop.setTextureSize(64, 64);
		this.RTop.mirror = true;
		setRotation(this.RTop, 0F, 0F, 0F);
		this.RBottom = new ModelRenderer(this, 24, 12);
		this.RBottom.addBox(0F, 0F, 0F, 3, 2, 10);
		this.RBottom.setRotationPoint(4F, 4F, -4F);
		this.RBottom.setTextureSize(64, 64);
		this.RBottom.mirror = true;
		setRotation(this.RBottom, 0F, 0F, 0F);
		this.RLeft = new ModelRenderer(this, 0, 46);
		this.RLeft.addBox(0F, 0F, 0F, 3, 10, 2);
		this.RLeft.setRotationPoint(4F, -4F, -6F);
		this.RLeft.setTextureSize(64, 64);
		this.RLeft.mirror = true;
		setRotation(this.RLeft, 0F, 0F, 0F);
		this.RRight = new ModelRenderer(this, 10, 46);
		this.RRight.addBox(0F, 0F, 0F, 3, 10, 2);
		this.RRight.setRotationPoint(4F, -6F, 4F);
		this.RRight.setTextureSize(64, 64);
		this.RRight.mirror = true;
		setRotation(this.RRight, 0F, 0F, 0F);
		this.GPivot = new ModelRenderer(this, 24, 24);
		this.GPivot.addBox(0F, 0F, 0F, 3, 4, 4);
		this.GPivot.setRotationPoint(-6F, -2F, -2F);
		this.GPivot.setTextureSize(64, 64);
		this.GPivot.mirror = true;
		setRotation(this.GPivot, 0F, 0F, 0F);
		this.GBase = new ModelRenderer(this, 24, 32);
		this.GBase.addBox(-2F, -3F, -4F, 4, 3, 8);
		this.GBase.setRotationPoint(-6F, 0F, 1F);
		this.GBase.setTextureSize(64, 64);
		this.GBase.mirror = true;
		setRotation(this.GBase, 0F, 0F, 0.6108652F);
		this.F31 = new ModelRenderer(this, 20, 52);
		this.F31.addBox(-3F, -1F, 0F, 3, 2, 2);
		this.F31.setRotationPoint(-6F, -2.8F, -1F);
		this.F31.setTextureSize(64, 64);
		this.F31.mirror = true;
		setRotation(this.F31, 0F, 0F, -0.5235988F);
		this.F21 = new ModelRenderer(this, 30, 52);
		this.F21.addBox(-3F, -1F, -2F, 3, 2, 2);
		this.F21.setRotationPoint(-6F, -2.8F, -1.2F);
		this.F21.setTextureSize(64, 64);
		this.F21.mirror = true;
		setRotation(this.F21, 0F, 0F, -0.5235988F);
		this.F41 = new ModelRenderer(this, 40, 52);
		this.F41.addBox(-3F, -1F, 0F, 3, 2, 2);
		this.F41.setRotationPoint(-6F, -2.8F, 1.2F);
		this.F41.setTextureSize(64, 64);
		this.F41.mirror = true;
		setRotation(this.F41, 0F, 0F, -0.5235988F);
		this.F51 = new ModelRenderer(this, 50, 52);
		this.F51.addBox(-3F, -1F, 0F, 3, 2, 2);
		this.F51.setRotationPoint(-6F, -2.8F, 3.4F);
		this.F51.setTextureSize(64, 64);
		this.F51.mirror = true;
		setRotation(this.F51, 0F, 0F, -0.5235988F);
		this.F11 = new ModelRenderer(this, 48, 38);
		this.F11.addBox(-1F, -1F, -3F, 2, 2, 3);
		this.F11.setRotationPoint(-5F, -1F, -2.5F);
		this.F11.setTextureSize(64, 64);
		this.F11.mirror = true;
		setRotation(this.F11, 1.22173F, 1.745329F, -1.047198F);
		this.F22 = new ModelRenderer(this, 20, 56);
		this.F22.addBox(-3F, -1F, -1F, 3, 2, 2);
		this.F22.setRotationPoint(-8.5F, -2F, -2.2F);
		this.F22.setTextureSize(64, 64);
		this.F22.mirror = true;
		setRotation(this.F22, 0F, 0F, -1.919862F);
		this.F32 = new ModelRenderer(this, 30, 56);
		this.F32.addBox(-3F, -1F, -1F, 3, 2, 2);
		this.F32.setRotationPoint(-8.5F, -2F, 0F);
		this.F32.setTextureSize(64, 64);
		this.F32.mirror = true;
		setRotation(this.F32, 0F, 0F, -1.919862F);
		this.F42 = new ModelRenderer(this, 40, 56);
		this.F42.addBox(-3F, -1F, -1F, 3, 2, 2);
		this.F42.setRotationPoint(-8.5F, -2F, 2.2F);
		this.F42.setTextureSize(64, 64);
		this.F42.mirror = true;
		setRotation(this.F42, 0F, 0F, -1.919862F);
		this.F52 = new ModelRenderer(this, 50, 56);
		this.F52.addBox(-3F, -1F, -1F, 3, 2, 2);
		this.F52.setRotationPoint(-8.5F, -2F, 4.4F);
		this.F52.setTextureSize(64, 64);
		this.F52.mirror = true;
		setRotation(this.F52, 0F, 0F, -1.919862F);
		this.F12 = new ModelRenderer(this, 48, 34);
		this.F12.addBox(-1F, -1F, -2F, 2, 2, 2);
		this.F12.setRotationPoint(-6F, 0.5F, -4.5F);
		this.F12.setTextureSize(64, 64);
		this.F12.mirror = true;
		setRotation(this.F12, 1.22173F, 2.935045F, -1.047198F);
		this.F23 = new ModelRenderer(this, 20, 60);
		this.F23.addBox(-3F, -1F, -1F, 3, 2, 2);
		this.F23.setRotationPoint(-8F, 0.5F, -2.2F);
		this.F23.setTextureSize(64, 64);
		this.F23.mirror = true;
		setRotation(this.F23, 0F, 0F, -2.879793F);
		this.F33 = new ModelRenderer(this, 30, 60);
		this.F33.addBox(-3F, -1F, -1F, 3, 2, 2);
		this.F33.setRotationPoint(-8F, 0.5F, 0F);
		this.F33.setTextureSize(64, 64);
		this.F33.mirror = true;
		setRotation(this.F33, 0F, 0F, -2.879793F);
		this.F43 = new ModelRenderer(this, 40, 60);
		this.F43.addBox(-3F, -1F, -1F, 3, 2, 2);
		this.F43.setRotationPoint(-8F, 0.5F, 2.2F);
		this.F43.setTextureSize(64, 64);
		this.F43.mirror = true;
		setRotation(this.F43, 0F, 0F, -2.879793F);
		this.F53 = new ModelRenderer(this, 50, 60);
		this.F53.addBox(-3F, -1F, -1F, 3, 2, 2);
		this.F53.setRotationPoint(-8F, 0.5F, 4.4F);
		this.F53.setTextureSize(64, 64);
		this.F53.mirror = true;
		setRotation(this.F53, 0F, 0F, -2.879793F);
		this.F13 = new ModelRenderer(this, 48, 30);
		this.F13.addBox(-1F, -1F, -2F, 2, 2, 2);
		this.F13.setRotationPoint(-7F, 1F, -4F);
		this.F13.setTextureSize(64, 64);
		this.F13.mirror = true;
		setRotation(this.F13, 0.5235988F, 2.617994F, -1.047198F);
		this.WireL = new ModelRenderer(this, 38, 30);
		this.WireL.addBox(0F, 0F, 0F, 4, 1, 1);
		this.WireL.setRotationPoint(0F, -5.5F, 0F);
		this.WireL.setTextureSize(64, 64);
		this.WireL.mirror = true;
		setRotation(this.WireL, 0F, 0F, 0F);
		this.WireR = new ModelRenderer(this, 38, 28);
		this.WireR.addBox(0F, 0F, 0F, 4, 1, 1);
		this.WireR.setRotationPoint(0F, -5.5F, 2F);
		this.WireR.setTextureSize(64, 64);
		this.WireR.mirror = true;
		setRotation(this.WireR, 0F, 0F, 0F);
		this.Gauge1 = new ModelRenderer(this, 20, 47);
		this.Gauge1.addBox(-1.5F, -1F, -2F, 3, 1, 4);
		this.Gauge1.setRotationPoint(-1F, -4F, 4F);
		this.Gauge1.setTextureSize(64, 64);
		this.Gauge1.mirror = true;
		setRotation(this.Gauge1, -0.7853982F, 0F, 0F);
		this.Gauge2 = new ModelRenderer(this, 34, 48);
		this.Gauge2.addBox(-2F, -1F, -1.5F, 4, 1, 3);
		this.Gauge2.setRotationPoint(-1F, -4F, 4F);
		this.Gauge2.setTextureSize(64, 64);
		this.Gauge2.mirror = true;
		setRotation(this.Gauge2, -0.7853982F, 0F, 0F);
		this.WireB = new ModelRenderer(this, 48, 49);
		this.WireB.addBox(0F, 0F, 0F, 4, 2, 1);
		this.WireB.setRotationPoint(0F, -1F, -5.5F);
		this.WireB.setTextureSize(64, 64);
		this.WireB.mirror = true;
		setRotation(this.WireB, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		this.Base.render(scaleFactor);
		this.BTop.render(scaleFactor);
		this.BBottom.render(scaleFactor);
		this.BLeft.render(scaleFactor);
		this.BRight.render(scaleFactor);
		this.RTop.render(scaleFactor);
		this.RBottom.render(scaleFactor);
		this.RLeft.render(scaleFactor);
		this.RRight.render(scaleFactor);
		this.GPivot.render(scaleFactor);
		this.GBase.render(scaleFactor);
		this.F31.render(scaleFactor);
		this.F21.render(scaleFactor);
		this.F41.render(scaleFactor);
		this.F51.render(scaleFactor);
		this.F11.render(scaleFactor);
		this.F22.render(scaleFactor);
		this.F32.render(scaleFactor);
		this.F42.render(scaleFactor);
		this.F52.render(scaleFactor);
		this.F12.render(scaleFactor);
		this.F23.render(scaleFactor);
		this.F33.render(scaleFactor);
		this.F43.render(scaleFactor);
		this.F53.render(scaleFactor);
		this.F13.render(scaleFactor);
		this.WireL.render(scaleFactor);
		this.WireR.render(scaleFactor);
		this.Gauge1.render(scaleFactor);
		this.Gauge2.render(scaleFactor);
		this.WireB.render(scaleFactor);
	}

	private static void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
