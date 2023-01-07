package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class RelicComponents {
	
	public static class RelicSmall extends Component {
		
		private boolean reinforced;
		
		private BlockSelector placeholderSelector; //TODO: again, create another base class / method that does this better.
		
		public RelicSmall() {
			super(0);
		}
		
		public RelicSmall(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 10, 7, 14);
			
			this.reinforced = rand.nextInt(5) == 0;
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!this.setAverageHeight(world, this.boundingBox, this.boundingBox.minY))
				return false;
			
			//Floor
			fillWithRandomizedBlocks(world, box, 2, 0, 2, 8, 0, 12, rand, placeholderSelector);
			//Walls
			//Front
			fillWithRandomizedBlocks(world, box, 1, 0, 1, 9, 0, 1, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 1, 1, 1, 3, 3, 1, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 7, 1, 1, 9, 3, 1, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 1, 4, 1, 9, 4, 1, rand, placeholderSelector);
			//Left
			fillWithRandomizedBlocks(world, box, 9, 0, 2, 9, 0, 13, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 9, 1, 2, 9, 3, 3, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 9, 1, 7, 9, 3, 13, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 9, 4, 2, 9, 4, 13, rand, placeholderSelector);
			//Back
			fillWithRandomizedBlocks(world, box, 2, 0, 13, 8, 0, 13, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 7, 1, 13, 8, 3, 13, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 2, 1, 13, 3, 3, 13, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 2, 4, 13, 8, 4, 13, rand, placeholderSelector);
			//Right
			fillWithRandomizedBlocks(world, box, 1, 0, 2, 1, 0, 13, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 1, 1, 10, 1, 3, 13, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 1, 3, 9, 1, 3, 9, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 1, 1, 7, 1, 3, 8, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 1, 1, 2, 1, 3, 3, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 1, 4, 2, 1, 4, 13, rand, placeholderSelector);
			//Outer Pillars and Cornice
			//Front
			fillWithRandomizedBlocks(world, box, 0, 0, 0, 0, 4, 1, rand, placeholderSelector); //Right pillar
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 1, 0, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 5, 1, 3, 0, box);
			fillWithRandomizedBlocks(world, box, 1, 4, 0, 2, 4, 0, rand, placeholderSelector);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 5, 3, 4, 0, box);
			fillWithMetadataBlocks(world, box, 4, 4, 0, 6, 4, 0, ModBlocks.brick_slab, 15);
			fillWithRandomizedBlocks(world, box, 10, 0, 0, 10, 4, 1, rand, placeholderSelector); //Left pillar
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 9, 0, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 4, 9, 3, 0, box);
			fillWithRandomizedBlocks(world, box, 8, 4, 0, 9, 4, 0, rand, placeholderSelector);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 4, 7, 4, 0, box);
			//Left
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 10, 0, 2, box); //Right pillar
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 7, 10, 3, 2, box);
			fillWithRandomizedBlocks(world, box, 10, 4, 2, 10, 4, 3, rand, placeholderSelector);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 7, 10, 4, 4, box);
			fillWithMetadataBlocks(world, box, 10, 4, 5, 10, 4, 7, ModBlocks.brick_slab, 15);
			fillWithRandomizedBlocks(world, box, 10, 0, 11, 10, 3, 12, rand, placeholderSelector); //Left pillar
			fillWithRandomizedBlocks(world, box, 10, 4, 9, 10, 4, 13, rand, placeholderSelector);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 10, 0, 13, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 7, 10, 3, 13, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 10, 0, 10, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 6, 10, 3, 10, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 6, 10, 4, 8, box);
			//Right
			fillWithRandomizedBlocks(world, box, 0, 0, 11, 0, 3, 12, rand, placeholderSelector); //Right pillar
			fillWithRandomizedBlocks(world, box, 0, 4, 9, 0, 4, 13, rand, placeholderSelector);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 0, 0, 13, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 7, 0, 3, 13, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 0, 0, 10, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 6, 0, 3, 10, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 6, 0, 4, 8, box);
			fillWithMetadataBlocks(world, box, 0, 4, 5, 0, 4, 7, ModBlocks.brick_slab, 15);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 0, 0, 2, box); //Left Pillar
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 7, 0, 3, 2, box);
			fillWithRandomizedBlocks(world, box, 0, 4, 2, 0, 4, 3, rand, placeholderSelector);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 7, 0, 4, 4, box);
			//Debris/Destroyed section
			fillWithRandomizedBlocks(world, box, 2, 0, 14, 8, 0, 14, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 9, 0, 14, 9, 3, 14, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 1, 0, 14, 1, 3, 14, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 8, 4, 14, 10, 4, 14, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 0, 4, 14, 4, 4, 14, rand, placeholderSelector);
			//Upper Floor
			//Floor
			fillWithRandomizedBlocks(world, box, 2, 4, 2, 8, 4, 3, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 2, 4, 4, 2, 4, 4, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 4, 4, 4, 8, 4, 5, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 2, 4, 6, 8, 4, 7, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 2, 4, 8, 7, 4, 9, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 2, 4, 10, 4, 4, 12, rand, placeholderSelector);
			//Front-facing Wall w/ slit
			fillWithRandomizedBlocks(world, box, 2, 5, 2, 2, 5, 4, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 2, 6, 2, 2, 6, 3, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 3, 5, 1, 7, 5, 1, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 3, 6, 1, 3, 6, 1, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 7, 6, 1, 7, 6, 1, rand, placeholderSelector);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 5, 4, 6, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 15, 5, 6, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 4, 6, 6, 1, box);
			fillWithMetadataBlocks(world, box, 4, 7, 1, 5, 7, 1, ModBlocks.brick_slab, 7);
			fillWithRandomizedBlocks(world, box, 8, 5, 2, 8, 5, 5, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 8, 6, 2, 8, 6, 3, rand, placeholderSelector);
			//Rear-facing Wall w/ slit
			//Pillars
			fillWithBlocks(world, box, 9, 5, 11, 10, 5, 12, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 9, 6, 11, 9, 6, 12, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 10, 6, 12, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 1, 10, 6, 11, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 9, 7, 11, box);
			fillWithBlocks(world, box, 0, 5, 11, 1, 5, 12, ModBlocks.brick_relic);
			fillWithBlocks(world, box, 0, 6, 11, 0, 6, 12, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 1, 6, 12, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 1, 6, 11, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 0, 7, 11, box);
			//Walls
			fillWithRandomizedBlocks(world, box, 2, 5, 12, 8, 5, 12, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 7, 6, 12, 8, 6, 12, rand, placeholderSelector);
			fillWithMetadataBlocks(world, box, 4, 6, 12, 6, 6, 12, ModBlocks.brick_slab, 15);
			fillWithRandomizedBlocks(world, box, 2, 6, 12, 3, 6, 12, rand, placeholderSelector);
			fillWithMetadataBlocks(world, box, 6, 7, 12, 7, 7, 12, ModBlocks.brick_slab, 7);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 1, 5, 7, 12, box);
			fillWithBlocks(world, box, 3, 7, 12, 4, 7, 12, ModBlocks.brick_relic);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 2, 7, 12, box);
			fillWithMetadataBlocks(world, box, 9, 5, 10, 10, 5, 10, ModBlocks.brick_slab, 7);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 9, 5, 8, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 2, 9, 5, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic, 0, 1, 5, 10, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_relic_stairs, 2, 1, 5, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 0, 5, 10, box);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 1, 6, 10, box);
			//Debris
			placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 7, 0, 5, 13, box);
			fillWithRandomizedBlocks(world, box, 1, 5, 13, 9, 5, 13, rand, placeholderSelector);
			fillWithRandomizedBlocks(world, box, 4, 5, 2, 7, 5, 9, rand, placeholderSelector);
			//Decoration (including reinforcements)
			if(reinforced) {
				//upper floor
				fillWithBlocks(world, box, 3, 4, 4, 3, 4, 5, ModBlocks.concrete_smooth);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 2, 4, 5, box);
				fillWithBlocks(world, box, 8, 4, 8, 8, 4, 9, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 5, 4, 10, 8, 4, 12, ModBlocks.concrete_smooth);
				//TODO: port multiblock method over, the below will not work
				fillWithMetadataBlocks(world, box, 4, 5, 6, 5, 5, 7, ModBlocks.turret_howard_damaged, 4);
				//Front-facing
				fillWithBlocks(world, box, 3, 5, 2, 3, 5, 5, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 3, 6, 2, 3, 6, 4, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 4, 5, 2, 6, 5, 2, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 7, 5, 2, 7, 5, 5, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 7, 6, 2, 7, 6, 4, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 3, 7, 2, 7, 7, 4, ModBlocks.concrete_slab);
				//Rear-facing
				fillWithBlocks(world, box, 2, 5, 6, 2, 5, 7, ModBlocks.concrete_slab);
				fillWithBlocks(world, box, 2, 5, 8, 2, 5, 11, ModBlocks.concrete_smooth);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 2, 6, 9, box);
				fillWithBlocks(world, box, 2, 6, 10, 2, 6, 11, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 3, 5, 11, 3, 6, 11, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 4, 5, 11, 6, 5, 11, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 7, 5, 11, 7, 6, 11, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 8, 5, 6, 8, 5, 7, ModBlocks.concrete_slab);
				fillWithBlocks(world, box, 8, 5, 8, 8, 5, 11, ModBlocks.concrete_smooth);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 8, 6, 9, box);
				fillWithBlocks(world, box, 8, 6, 10, 8, 6, 11, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 2, 7, 9, 8, 7, 11, ModBlocks.concrete_smooth);
				//Below floor
				fillWithBlocks(world, box, 4, 1, 2, 6, 1, 2, ModBlocks.concrete_smooth);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 4, 6, 2, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 5, 2, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 5, 4, 2, 2, box);
				fillWithBlocks(world, box, 4, 3, 2, 6, 3, 2, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 2, 1, 4, 2, 1, 6, ModBlocks.concrete_smooth);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 7, 2, 2, 4, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 2, 2, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 6, 2, 2, 6, box);
				fillWithBlocks(world, box, 2, 3, 4, 2, 3, 6, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 5, 1, 4, 5, 3, 5, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 4, 1, 7, 4, 3, 7, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 6, 1, 7, 6, 3, 7, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 8, 1, 4, 8, 1, 6, ModBlocks.concrete_smooth);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 7, 8, 2, 4, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 8, 2, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 6, 8, 2, 6, box);
				fillWithBlocks(world, box, 8, 3, 4, 8, 3, 6, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, 4, 1, 12, 6, 1, 12, ModBlocks.concrete_smooth);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 5, 4, 2, 12, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 5, 2, 12, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 4, 6, 2, 12, box);
				fillWithBlocks(world, box, 4, 3, 12, 6, 3, 12, ModBlocks.concrete_smooth);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 1, 1, 9, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 1, 2, 9, box);
				fillWithMetadataBlocks(world, box, 4, 1, 1, 6, 1, 1, ModBlocks.barbed_wire, 5);
				fillWithMetadataBlocks(world, box, 1, 1, 4, 1, 1, 6, ModBlocks.barbed_wire, 2);
				fillWithMetadataBlocks(world, box, 9, 1, 4, 9, 1, 6, ModBlocks.barbed_wire, 3);
				fillWithMetadataBlocks(world, box, 4, 1, 13, 6, 1, 13, ModBlocks.barbed_wire, 4);
			}
			//TODO: actual, further decoration. chests + lore books + loot and such.
			
			
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
