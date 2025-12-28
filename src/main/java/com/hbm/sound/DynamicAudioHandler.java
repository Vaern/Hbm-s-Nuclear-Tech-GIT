package com.hbm.sound;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class DynamicAudioHandler {
	/* List of all currently synced looping sounds, lifted straight from ItemGunBaseNBT 
	 * Only allows one loop per entity, this could be changed in the future if necessary */
	public static ConcurrentHashMap<Entity, AudioDynamic> loops = new ConcurrentHashMap();
	
	/** Creates an AudioDynamic, meant for non-looping packets.*/
	public static AudioDynamic createSound(Entity entity, ResourceLocation loc, float volume ,float pitch, float range) {
		AudioDynamic sound = new AudioDynamic(loc);
		sound.setPosition((float) entity.posX, (float) entity.posY, (float) entity.posZ);
		sound.attachTo(entity);
		sound.setVolume(volume); sound.setPitch(pitch); sound.setRange(range);
		return sound;
	}
	
	/** If a looped sound already exists, set keepAlive for the respective sound/entity. Otherwise, add it. */
	public static void refreshLoopedSound(Entity entity, ResourceLocation loc, float volume, float pitch, float range) {
		AudioDynamic sound = loops.get(entity);
		
		if(sound == null) {
			sound = new AudioDynamic(loc);
			sound.setPosition((float) entity.posX, (float) entity.posY, (float) entity.posZ); //prevent weirdness
			sound.attachTo(entity);
			sound.setVolume(volume); sound.setPitch(pitch); sound.setRange(range);
			sound.setKeepAlive(20); // lenient latency 4 packets; subject 2 change if needed
			loops.put(entity, sound);
			
			sound.start();
			return;
		}
		
		if(sound.getPositionedSoundLocation().equals(loc)) { //unlikely for "different" sounds to use the same .ogg
			sound.keepAlive();
		} else { //screw you, we got a new sound to play
			sound.stop();
			
			sound = new AudioDynamic(loc); //thank u reference types
			sound.setPosition((float) entity.posX, (float) entity.posY, (float) entity.posZ);
			sound.attachTo(entity);
			sound.setVolume(volume); sound.setPitch(pitch); sound.setRange(range);
			sound.setKeepAlive(20);
			
			sound.start();
		}
	}
	
	//TODO: ask bob if a stop method is wise or fucking stupid (hint: probably wise)
	
	//suboptimal but i'd like to avoid a circular dependency between some kinda wrapper/sound.
	public static void removeDeadSounds() {
		if(loops.isEmpty()) return;
		
		Iterator<Entity> itr = loops.keySet().iterator();
		while(itr.hasNext()) {
			Entity entity = itr.next();
			if(!loops.get(entity).isPlaying())
				itr.remove();
		}
	}
	
}
