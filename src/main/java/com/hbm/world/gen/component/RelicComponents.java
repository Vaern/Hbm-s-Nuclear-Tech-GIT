package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class RelicComponents {
	
	public static class RelicSmall extends Component {
				
		private BlockSelector placeholderSelector; //TODO: again, create another base class / method that does this better.
		
		public RelicSmall() {
			super(0);
		}
		
		public RelicSmall(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 10, 7, 14);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!this.setAverageHeight(world, this.boundingBox, this.boundingBox.minY))
				return false;
			
			//TODO: implement bounding box checks, keeping in mind rotation. make a method?
			//if(box.intersectsWith(new StructureBoundingBox())) {
			
			final int stairMetaW = getStairMeta(0);
			final int stairMetaE = getStairMeta(1);
			final int stairMetaN = getStairMeta(2);
			
			//Right Wall
			fillWithBlocks(world, box, 1, 0, 6, 1, 4, 15, ModBlocks.brick_relic);
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
			fillWithBlocks(world, box, 11, 0, 6, 11, 4, 15, ModBlocks.brick_relic);
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
				fillWithBlocks(world, box, 1, 0, 2, 3, 0, 4, ModBlocks.brick_relic);
				fillWithBlocks(world, box, 9, 0, 2, 11, 0, 4, ModBlocks.brick_relic);
				fillWithBlocks(world, box, 5, 0, 1, 7, 0, 5, ModBlocks.brick_relic);
				fillWithBlocks(world, box, 2, 0, 6, 10, 0, 15, ModBlocks.brick_relic);
			}
			
			fillWithBlocks(world, box, 5, 8, 1, 7, 8, 1, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 1, 8, 2, 11, 8, 15, ModBlocks.brick_relic);
			
			//TODO: deco
			
			//}
			
			
			
			return true;
		}
		
	}
	
	//TODO: make a class that's better than the block selector. like straight up.
	//Block selectors: Wall, Floor, Debris
	/*static class RelicWall extends StructureComponent.BlockSelector {
		
		private boolean damage = false;
		private int side = 
		public 
		
		RelicWall(boolean damaged, int facingSide) {
			
		}
		
		/** Selects blocks */
		/*@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();
			
			if(damage) {
				if(chance < 0.6F)
					this.field_151562_a = ModBlocks.brick_relic;
				else if(chance < 0.8F)
					this.field_151562_a = ModBlocks.brick_relic_stairs;
				else
					this.field_151562_a = ModBlocks.brick_slab;
			} else {
				if(chance < 0.95F)
					this.field_151562_a = ModBlocks.brick_relic;
				else {
					this.field_151562_a = ModBlocks.brick_relic_stairs;
				}
			}
		}
		
		public int getSelectedBlockMetaData() {
			if(damage) {
				
			}
			
			return field_15162_a == ModBlocks.brick_relic ? 0 : ;
		}
	}*/
}
