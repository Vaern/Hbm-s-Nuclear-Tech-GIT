package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class RelicComponents {
	
	public static void registerComponents() {
		MapGenStructureIO.func_143031_a(RelicOutpost.class, "NTMRelicOutpost");
		//MapGenStructureIO.func_143031_a(RelicCastle.class, "NTMRelicCastle");
	}
	
	public static class RelicOutpost extends Component {
		
		long seed;
		
		public RelicOutpost() {
			super(0);
		}
		
		public RelicOutpost(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 12, 15, 14);
			
			this.seed = rand.nextLong();
		}
		
		/** Set to NBT */
		protected void func_143012_a(NBTTagCompound nbt) {
			nbt.setInteger("HPos", this.hpos);
			nbt.setLong("seed", seed);
		}
		
		/** Get from NBT */
		protected void func_143011_b(NBTTagCompound nbt) {
			this.hpos = nbt.getInteger("HPos");
			this.seed = nbt.getLong("seed");
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(hpos < 0 && !this.setAverageHeight(world, this.boundingBox, this.boundingBox.minY))
				return false;
			
			fillWithAir(world, box, 2, 1, 2, 4, 12, 4);
			fillWithAir(world, box, 6, 1, 4, 10, 3, 5);
			fillWithAir(world, box, 4, 1, 6, 10, 3, 12);
			fillWithAir(world, box, 11, 1, 7, 11, 3, 9);
			fillWithAir(world, box, 6, 1, 13, 8, 3, 13);
			
			//Tower
			//Right side
			fillWithBlocks(world, box, 1, 0, 0, 1, 13, 2, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 1, 0, 4, 1, 13, 6, ModBlocks.brick_relic);
			for(int i = 0; i <= 12; i += 4)
				fillWithBlocks(world, box, 1, i, 3, 1, 1 + i, 3, ModBlocks.brick_relic);
			for(int i = 2; i <= 10; i += 4)
				fillWithAir(world, box, 1,  i, 3, 1, 1 + i, 3);
			fillWithBlocks(world, box, 0, 0, 1, 0, 13, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 0, 5, 0, 13, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 4, 2, 0, 4, 4, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 13, 2, 0, 13, 4, ModBlocks.brick_relic);
			//Left side
			fillWithBlocks(world, box, 5, 0, 0, 5, 4, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 5, 5, 0, 5, 5, 6, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 5, 6, 4, 5, 13, 6, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 5, 6, 0, 5, 13, 2, ModBlocks.brick_relic);
			for(int i = 6; i <= 10; i += 4)
				fillWithAir(world, box, 5, i, 3, 5, 1 + i, 3);
			for(int i = 8; i <= 12; i += 4)
				fillWithBlocks(world, box, 5, i, 3, 5, 1 + i, 3, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 6, 0, 1, 6, 13, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 6, 5, 5, 6, 13, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 6, 13, 2, 6, 13, 4, ModBlocks.brick_relic);
			//Front side
			fillWithBlocks(world, box, 4, 0, 1, 4, 13, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 0, 1, 2, 13, 1, ModBlocks.brick_relic);
			for(int i = 0; i <= 12; i += 4)
				fillWithBlocks(world, box, 3, i, 1, 3, 1 + i, 1, ModBlocks.brick_relic);
			for(int i = 2; i <= 10; i += 4)
				fillWithAir(world, box, 3,  i, 1, 3, 1 + i, 1);
			fillWithBlocks(world, box, 2, 4, 0, 4, 4, 0, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 13, 0, 4, 13, 0, ModBlocks.brick_relic);
			//Back side
			fillWithAir(world, box, 4, 1, 5, 4, 2, 5);
			fillWithBlocks(world, box, 2, 0, 5, 3, 9, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 4, 3, 5, 4, 4, 5, ModBlocks.brick_relic);
			fillWithAir(world, box, 4, 5, 5, 4, 6, 5);
			fillWithBlocks(world, box, 4, 7, 5, 4, 12, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 10, 5, 2, 11, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 12, 5, 3, 12, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 13, 5, 4, 13, 6, ModBlocks.brick_relic);
			
			//Main room thingy
			//Right side
			fillWithBlocks(world, box, 3, 0, 6, 3, 1, 14, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 3, 2, 12, 3, 3, 14, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 3, 4, 6, 3, 4, 14, ModBlocks.brick_relic);
			for(int i = 6; i <= 10; i += 2) {
				fillWithBlocks(world, box, 3, 2, i, 3, 3, i, ModBlocks.brick_relic);
				fillWithAir(world, box, 3, 2, 1 + i, 3, 3, 1 + i);
			}
			//Back Side
			fillWithBlocks(world, box, 2, 0, 13, 2, 4, 13, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 4, 0, 13, 5, 4, 13, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 6, 0, 14, 8, 1, 14, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 6, 2, 14, 6, 3, 14, ModBlocks.brick_relic);
			fillWithAir(world, box, 7, 2, 14, 7, 3, 14);
			fillWithBlocks(world, box, 8, 2, 14, 8, 3, 14, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 6, 4, 14, 8, 4, 14, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 9, 0, 13, 10, 4, 13, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 0, 13, 12, 4, 13, ModBlocks.brick_relic);
			//Left side
			fillWithBlocks(world, box, 11, 0, 10, 11, 1, 14, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 11, 2, 12, 11, 3, 14, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 11, 2, 10, 11, 3, 10, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 11, 4, 10, 11, 4, 14, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 0, 9, 12, 3, 9, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 4, 7, 12, 4, 9, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 0, 7, 12, 3, 7, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 11, 0, 2, 11, 1, 6, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 11, 2, 6, 11, 3, 6, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 11, 2, 2, 11, 3, 4, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 11, 4, 2, 11, 4, 6, ModBlocks.brick_relic);
			fillWithAir(world, box, 11, 2, 11, 11, 3, 11);
			fillWithAir(world, box, 12, 1, 8, 12, 3, 8);
			fillWithAir(world, box, 11, 2, 5, 11, 3, 5);
			//Front side
			fillWithBlocks(world, box, 12, 0, 3, 12, 4, 3, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 6, 0, 3, 10, 1, 3, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 10, 2, 3, 10, 3, 3, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 6, 4, 3, 10, 4, 3, ModBlocks.brick_relic);
			for(int i = 6; i <= 8; i += 2) {
				fillWithBlocks(world, box, i, 2, 3, i, 3, 3, ModBlocks.brick_relic);
				fillWithAir(world, box, 1 + i, 2, 3, 1 + i, 3, 3);
			}
			
			//deco stair shit idfk and Battlements
			final int stairMetaW = getStairMeta(0);
			final int stairMetaE = getStairMeta(1);
			final int stairMetaN = getStairMeta(2);
			final int stairMetaS = getStairMeta(3);
			
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE | 4, 2, 3, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW | 4, 4, 3, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaN, 3, 5, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaS | 4, 0, 3, 2, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaN | 4, 0, 3, 4, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW, 0, 5, 3, box);
			for(int i = 0; i <= 6; i += 6) {
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaS | 4, i, 12, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaN | 4, i, 12, 4, box);
				for(int j = 1; j <= 5; j += 2) {
					placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, i, 14, j, box);
					placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, i, 15, j, box);
				}
				for(int j = 2; j <= 4; j += 2)
					placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, i > 0 ? stairMetaE : stairMetaW, i, 14, j, box);
			}
			for(int i = 0; i <= 6; i += 6) {
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE | 4, 2, 12, i, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW | 4, 4, 12, i, box);
				for(int j = 1; j <= 5; j += 2) {
					placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, j, 14, i, box);
					placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, j, 15, i, box);
				}
				for(int j = 2; j <= 4; j += 2)
					placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, i > 0 ? stairMetaS : stairMetaN, j, 14, i, box);
			}
			
			//why are we over 150 lines now. it's literally only been walls and stairs
			//Right side
			for(int i = 6; i <= 12; i += 2) {
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW, 2, 5, i, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 2, 5, 1 + i, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 2, 6, 1 + i, box);
			}
			for(int i = 7; i <= 11; i += 2)
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW | 4, 2, 4, i, box);
			//Back side
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW | 4, 5, 4, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE | 4, 9, 4, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 3, 5, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 3, 6, 14, box);
			for(int i = 4; i <= 10; i += 2) {
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaS, i, 5, 14, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 1 + i, 5, 14, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 1 + i, 6, 14, box);
			}
			//Left side
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE, 12, 0, 8, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE | 4, 12, 3, 8, box);
			for(int i = 4; i <= 10; i += 6) {
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaS | 4, 12, 4, i, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaN | 4, 12, 4, 2 + i, box);
			}
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 12, 5, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 12, 6, 3, box);
			for(int i = 4; i <= 12; i += 2) {
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE, 12, 5, i, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 12, 5, 1 + i, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 12, 6, 1 + i, box);
			}
			//Front Side
			for(int i = 7; i <= 9; i += 2)
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaN | 4, i, 4, 2, box);
			for(int i = 6; i <= 10; i += 2) {
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaN, i, 5, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 1 + i, 5, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 1 + i, 6, 2, box);
			}
			
			//Floors
			for(int i = 0; i <= 4; i += 4) {
				fillWithBlocks(world, box, 6, i, 4, 10, i, 5, ModBlocks.brick_relic);
				fillWithBlocks(world, box, 4, i, 6, 10, i, 12, ModBlocks.brick_relic);
				fillWithBlocks(world, box, 11, i, 7, 11, i, 9, ModBlocks.brick_relic);
				fillWithBlocks(world, box, 6, i, 13, 8, i, 13, ModBlocks.brick_relic);
			}
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 4, 0, 5, box);
			fillWithBlocks(world, box, 2, 0, 2, 4, 0, 4, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 4, 2, 3, 4, 2, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 4, 3, 4, 4, 4, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 8, 2, 3, 8, 2, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 8, 3, 4, 8, 4, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 13, 2, 3, 13, 2, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 13, 3, 4, 13, 4, ModBlocks.brick_relic);
			
			//Foundation
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 1, 1, 5, 5, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 6, 3, 11, 5, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 3, 6, 11, 13, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 12, 7, 12, 9, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 12, 3, 12, 3, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 11, 2, 11, 2, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 6, 1, 6, 1, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 5, 0, 5, 0, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 1, 0, 1, 0, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 0, 1, 0, 1, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 0, 5, 0, 5, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 1, 6, 1, 6, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 2, 13, 2, 13, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 3, 14, 3, 14, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 11, 14, 11, 14, -1, box);
			placeFoundationUnderneath(world, ModBlocks.brick_relic, 0, 12, 13, 12, 13, -1, box);
			
			//Finally, deco
			//Doors
			placeDoor(world, box, ModBlocks.door_bunker, 2, false, false, 11, 1, 8);
			placeDoor(world, box, ModBlocks.door_bunker, 3, false, false, 4, 5, 5);
			//Ladder
			//TODO ladder meta method
			//Nameplates
			placeRelicNameplate(world, box, 5, 12, 2, 7, "nameplate.relic_outpost.title", seed);
			placeRelicNameplate(world, box, 5, 12, 2, 9, "nameplate.relic_outpost.title", seed);
			
			return true;
		}
	}
	
	public static class RelicCastle extends Component {
				
		private BlockSelector placeholderSelector; //TODO: again, create another base class / method that does this better.
		
		public RelicCastle() {
			super(0);
		}
		
		public RelicCastle(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 25, 19, 47);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(hpos < 0 && !this.setAverageHeight(world, this.boundingBox, this.boundingBox.minY))
				return false;
			
			//TODO: implement bounding box checks, keeping in mind rotation. make a method?
			//end game would be a proper switch to make this all nice and neat
			//if(box.intersectsWith(new StructureBoundingBox())) { (0, 0)
			
			final int stairMetaW = getStairMeta(0);
			final int stairMetaE = getStairMeta(1);
			final int stairMetaN = getStairMeta(2);
			
			fillWithAir(world, box, 5, 1, 1, 7, 7, 1);
			fillWithAir(world, box, 1, 1, 2, 11, 7, 15);
			
			//Right Wall
			fillWithBlocks(world, box, 1, 0, 5, 1, 4, 15, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 0, 4, 0, 8, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 0, 3, 0, 1, 3, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 4, 3, 0, 5, 3, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 0, 8, 3, box);
			fillWithBlocks(world, box, 0, 0, 2, 0, 8, 2, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 5, 1, 0, 8, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 5, 6, 0, 5, 15, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 8, 6, 0, 8, 15, ModBlocks.brick_relic);
			
			for(int i = 0; i < 5; i++) {
				fillWithBlocks(world, box, 0, 6, 6 + i * 2, 0, 7, 6 + i * 2, ModBlocks.brick_relic);
				fillWithAir(world, box, 0, 6, 7 + i * 2, 0, 7, 7 + i * 2);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW | 4, 0, 4, 6 + i * 2, box);
			}
				
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, getStairMeta(6), 0, 4, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 0, 9, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 0, 10, 1, box);
			//Battlements
			for(int i = 0; i < 7; i++) {
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW, 0, 9, 2 + i * 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 0, 9, 3 + i * 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 0, 10, 3 + i * 2, box);
			}
			
			//Left Wall
			fillWithBlocks(world, box, 11, 0, 5, 11, 4, 15, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 0, 4, 12, 8, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 0, 3, 12, 1, 3, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 4, 3, 12, 5, 3, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 12, 8, 3, box);
			fillWithBlocks(world, box, 12, 0, 2, 12, 8, 2, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 5, 1, 12, 8, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 5, 6, 12, 5, 15, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 12, 8, 6, 12, 8, 15, ModBlocks.brick_relic);
			
			for(int i = 0; i < 5; i ++) {
				fillWithBlocks(world, box, 12, 6, 6 + i * 2, 12, 7, 6 + i * 2, ModBlocks.brick_relic);
				fillWithAir(world, box, 12, 6, 7 + i * 2, 12, 7, 7 + i * 2);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE | 4, 12, 4, 6 + i * 2, box);
			}
				
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, getStairMeta(6), 12, 4, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 12, 9, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 12, 10, 1, box);
			//Battlements
			for(int i = 0; i < 7; i++) {
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE, 12, 9, 2 + i * 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 12, 9, 3 + i * 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 12, 10, 3 + i * 2, box);
			}
			
			//Front Wall
			fillWithBlocks(world, box, 1, 0, 1, 4, 1, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 1, 2, 1, 1, 3, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 3, 2, 1, 4, 3, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 1, 4, 1, 4, 8, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 4, 0, 2, 4, 4, 4, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 0, 5, 4, 0, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 3, 1, 5, 4, 2, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 2, 3, 5, 4, 4, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 8, 0, 1, 11, 1, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 8, 2, 1, 9, 3, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 11, 2, 1, 11, 3, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 8, 4, 1, 11, 8, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 8, 0, 2, 8, 4, 4, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 8, 0, 5, 10, 0, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 8, 1, 5, 9, 2, 5, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 8, 3, 5, 10, 4, 5, ModBlocks.brick_relic);
			
			fillWithBlocks(world, box, 4, 0, 0, 8, 1, 0, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 4, 2, 0, 4, 4, 0, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 5, 4, 0, 5, 8, 0, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 6, 2, 0, 6, 5, 0, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 6, 8, 0, box);
			fillWithBlocks(world, box, 7, 4, 0, 7, 8, 0, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 8, 2, 0, 8, 4, 0, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 3, 4, 0, box);
			fillWithBlocks(world, box, 2, 4, 0, 3, 4, 0, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 1, 5, 0, 2, 8, 0, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 9, 4, 0, 10, 4, 0, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 10, 5, 0, 11, 8, 0, ModBlocks.brick_relic);
			
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW | 4, 1, 4, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW | 4, 3, 3, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE | 4, 9, 3, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE | 4, 11, 4, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE, 3, 5, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 15, 3, 8, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW | 4, 4, 8, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaW, 9, 5, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE | 4, 8, 8, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 15, 9, 8, 0, box);
			//Battlements
			fillWithBlocks(world, box, 10, 9, 0, 11, 9, 0, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE, 11, 10, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 10, 10, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaN, 9, 9, 0, box);
			fillWithBlocks(world, box, 7, 9, 0, 8, 10, 0, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaN, 6, 9, 0, box);
			fillWithBlocks(world, box, 4, 9, 0, 5, 10, 0, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaN, 3, 9, 0, box);
			fillWithBlocks(world, box, 1, 9, 0, 2, 9, 0, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, stairMetaE, 1, 10, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 2, 10, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 1, 9, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 11, 9, 1, box);
			
			//Floors
			for(int i = 0; i <= 4; i += 4) {
				fillWithBlocks(world, box, 1, i, 2, 3, i, 4, ModBlocks.brick_relic);
				fillWithBlocks(world, box, 9, i, 2, 11, i, 4, ModBlocks.brick_relic);
				fillWithBlocks(world, box, 5, i, 1, 7, i, 5, ModBlocks.brick_relic);
				fillWithBlocks(world, box, 2, i, 6, 10, i, 15, ModBlocks.brick_relic);
			}
			
			fillWithBlocks(world, box, 5, 8, 1, 7, 8, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 1, 8, 2, 11, 8, 15, ModBlocks.brick_relic);
			
			//TODO: deco
			
			//}
			
			//if(box.intersectsWith(new StructureBoundingBox())) { (0, 1)
			
			
			
			//}
			
			
			return true;
		}
		
	}
}
