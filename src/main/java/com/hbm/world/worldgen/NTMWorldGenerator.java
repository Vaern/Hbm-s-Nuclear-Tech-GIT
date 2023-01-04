package com.hbm.world.worldgen;

import java.util.Random;

import com.hbm.config.StructureConfig;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class NTMWorldGenerator implements IWorldGenerator {
	
	private MapGenNTMFeatures scatteredFeatureGenerator = new MapGenNTMFeatures();
	
	private final Random rand = new Random(); //A central random, used to cleanly generate our stuff without affecting vanilla or modded seeds.
	
	/** Inits all MapGen upon the loading of a new world. Hopefully clears out structureMaps and structureData when a different world is loaded. */
	@SubscribeEvent
	public void onLoad(WorldEvent.Load event) {
		scatteredFeatureGenerator = (MapGenNTMFeatures) TerrainGen.getModdedMapGen(new MapGenNTMFeatures(), EventType.CUSTOM);
		hasPopulationEvent = false;
	}
	
	/** Called upon the initial population of a chunk. Called in the pre-population event first; called again if pre-population didn't occur (flatland) */
	private void setRandomSeed(World world, int chunkX, int chunkZ) {
		rand.setSeed(world.getSeed());
		final long i = rand.nextLong() / 2L * 2L + 1L;
		final long j = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed((long)chunkX * i + (long)chunkZ * j ^ world.getSeed());
	}
	
	/*
	 * Pre-population Events / Structure Generation
	 * Used to generate structures without unnecessary intrusion by biome decoration, like trees.
	 */
	
	private boolean hasPopulationEvent = false; // Does the given chunkGenerator have a population event? If not (flatlands), default to using generate.
	
	@SubscribeEvent
	public void generateStructures(PopulateChunkEvent.Pre event) {
		setRandomSeed(event.world, event.chunkX, event.chunkZ); //Set random for population down the line.
		hasPopulationEvent = true;
		
		if(!StructureConfig.enableStructures) return;
		
		switch (event.world.provider.dimensionId) {
		case -1:
			break;
		case 0:
			generateOverworldStructures(event.world, event.chunkProvider, event.chunkX, event.chunkZ);
			break;
		case 1:
			break;
		}
	}
	
	protected void generateOverworldStructures(World world, IChunkProvider chunkProvider, int chunkX, int chunkZ) {
		Block[] ablock = new Block[65536]; //ablock isn't actually used for anything in MapGenStructure
		
		this.scatteredFeatureGenerator.func_151539_a(chunkProvider, world, chunkX, chunkZ, ablock);
		this.scatteredFeatureGenerator.generateStructuresInChunk(world, rand, chunkX, chunkZ);
	}
	
	/*
	 * Post-Vanilla / Modded Generation
	 * Used to generate features that don't care about intrusions (ores, craters, caves, etc.)
	 */
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		switch (world.provider.dimensionId) {
		case -1:
			generateNether(world, rand, chunkGenerator, chunkX, chunkZ); break;
		case 0:
			generateSurface(world, rand, chunkGenerator, chunkX, chunkZ); break;
		case 1:
			generateEnd(world, rand, chunkGenerator, chunkX, chunkZ); break;
		}
	}
	
	private void generateNether(World world, Random rand, IChunkProvider chunkGenerator, int chunkX, int chunkZ) { }
	
	private void generateSurface(World world, Random rand, IChunkProvider chunkGenerator, int chunkX, int chunkZ) { //TODO fiix this 
		if(!hasPopulationEvent) { //If we've failed to generate any structures (flatlands)
			setRandomSeed(world, chunkX, chunkZ); //Reset the random seed to compensate
			if(StructureConfig.enableStructures) generateOverworldStructures(world, chunkGenerator, chunkX, chunkZ); //Do it through the post-population generation directly
		}
		
		
		
	}

	private void generateEnd(World world, Random rand, IChunkProvider chunkGenerator, int chunkX, int chunkZ) { }
}
