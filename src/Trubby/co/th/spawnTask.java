package Trubby.co.th;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

public class spawnTask extends BukkitRunnable {
	
	int amount;
	int counter = 0;
	
	Random ran = new Random();
	
	ArrayList<Location> spawners = new ArrayList<>();
	ArrayList<EntityType> types = new ArrayList<EntityType>(); //TODO Entity DG
	
	public spawnTask(int amount, ArrayList<Location> spawners, ArrayList<EntityType> types) {
		this.amount = amount;
		this.spawners = spawners;
		this.types = types;
		
		Bukkit.broadcastMessage("runspawntask!");
	}

	@Override
	public void run() {
		counter++;
		if(counter <= amount){
			for (int i = 0; i < 2; i++) {
				
				Location ranloc = spawners.get(ran.nextInt(spawners.size()));
				ranloc.getWorld().spawnEntity(ranloc, types.get(ran.nextInt(types.size())));
				
				
				Bukkit.broadcastMessage("spawnmob " + counter);
			}
			
		}else{
			Bukkit.broadcastMessage("cancel");
			this.cancel();
		}
		
		// TODO Auto-generated method stub
		
	}
	
	

}
