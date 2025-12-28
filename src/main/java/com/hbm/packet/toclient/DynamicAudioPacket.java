package com.hbm.packet.toclient;

import com.hbm.sound.AudioDynamic;
import com.hbm.sound.DynamicAudioHandler;
import com.hbm.util.BufferUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class DynamicAudioPacket implements IMessage {
	
	private boolean looping;
	private int entityID;
	private String soundLoc;
	private float volume;
	private float pitch;
	private float range;
	
	public DynamicAudioPacket(boolean loop, Entity entity, String sound, float vol, float pit, float ran) {
		this.looping = loop;
		this.entityID = entity.getEntityId();
		this.soundLoc = sound;
		this.volume = vol;
		this.pitch = pit;
		this.range = ran;
	}
	
	/* Structure:
	 * Repeating boolean > Entity ID > ResourceLoc > Volume > Pitch > Range
	 * (keep alive must keep creation info because players may enter range *after* creation)
	 * sound will persist for a second, keep alive will come in every ~half second most likely
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(looping);
		buf.writeInt(entityID);
		BufferUtil.writeString(buf, soundLoc);
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
		buf.writeFloat(range);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.looping = buf.readBoolean();
		this.entityID = buf.readInt();
		this.soundLoc = BufferUtil.readString(buf);
		this.volume = buf.readFloat();
		this.pitch = buf.readFloat();
		this.range = buf.readFloat();
	}
	
	public static class Handler implements IMessageHandler<DynamicAudioPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(DynamicAudioPacket m, MessageContext ctx) {
			try {
				Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(m.entityID);
				if(entity == null) return null;
				if(m.soundLoc == null) return null;
				ResourceLocation loc = new ResourceLocation(m.soundLoc);
				
				if(!m.looping) {
					AudioDynamic sound = DynamicAudioHandler.createSound(entity, loc, m.volume, m.pitch, m.range);
					sound.start();
					
					return null;
				}
				
				DynamicAudioHandler.refreshLoopedSound(entity, loc, m.volume, m.pitch, m.range);
				
			} catch(Exception x) { } finally { }
			return null;
		}
	}
}
