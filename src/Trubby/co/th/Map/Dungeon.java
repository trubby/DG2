package Trubby.co.th.Map;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Dungeon {
	
	public ArrayList<Player> players = new ArrayList<>();
	public ArrayList<Room> rooms = new ArrayList<>();
	public int step = 0;
	
	public void spawnMobs(Room room){
		
		Room r = rooms.get(step);
		r.runSpawn();
		
	}

}
