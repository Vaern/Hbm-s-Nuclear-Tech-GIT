package com.hbm.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenPlants extends WorldGenerator {
	
	/** Target block to 'scatter' in a group on the surface. */
	protected Block target;
	protected int metadata; 
	/** How many attempts to find a suitable location. Changing this should change the amount per generation. */
	protected int iterations;
	
	public WorldGenPlants(Block block) {
		this(block, 0, 64);
	}
	
	public WorldGenPlants(Block block, int meta) {
		this(block, meta, 64);
	}
	
	public WorldGenPlants(Block block, int meta, int attempts) {
		this.target = block;
		this.metadata = meta;
		this.iterations = attempts;
	}
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		for(int i = 0; i < iterations; i++) {
			final int posX = x + rand.nextInt(8) - rand.nextInt(8);
			final int posY = y + rand.nextInt(4) - rand.nextInt(4);
			final int posZ = z + rand.nextInt(8) - rand.nextInt(8);
			
			if(world.isAirBlock(posX, posY, posZ) && (!world.provider.hasNoSky || posY < 255) && target.canBlockStay(world, posX, posY, posZ))
				world.setBlock(posX, posY, posZ, target, metadata, 2); //Worldgen should *not* cause block updates.
		}
		
		return true;
	}
	
	public boolean locateAndGenerate(World world, Random rand, int x, int z) {
		final int posX = x + rand.nextInt(16);
		final int posZ = z + rand.nextInt(16);
		final int posY = world.getHeightValue(posX, posZ);
		
		return generate(world, rand, posX, posY, posZ);
	}
}
