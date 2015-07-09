package Trubby.co.th.Map;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import Trubby.co.th.DG;
import Trubby.co.th.spawnTask;

public class Room {
	
	Random ran = new Random();
	
	public ArrayList<Location> spawners = new ArrayList<>();
	public ArrayList<EntityType> types = new ArrayList<EntityType>(); //TODO Entity DG
	
	public Vector min;
	public Vector max;
	
	public Room(Vector min, Vector max) {
		
		this.min = min;
		this.max = max;
		
		types.add(EntityType.CREEPER);
		types.add(EntityType.ZOMBIE);
		types.add(EntityType.SKELETON);
	}
	
	@SuppressWarnings("unused")
	public void runSpawn(){
		
		int amount = spawners.size()/2;
		BukkitTask task = new spawnTask(amount, this.spawners, this.types).runTaskTimer(DG.plugin, 20L, 40L);
	}

}
