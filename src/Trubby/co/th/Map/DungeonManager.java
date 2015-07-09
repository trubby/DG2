package Trubby.co.th.Map;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class DungeonManager {
	
	public ArrayList<Dungeon> dungeons = new ArrayList<>();
	
	public void create(){
		
		//TODO
		
	}

	//GET PLAYER DUNGEON
	public Dungeon getDungeon(Player p){
		for(Dungeon d : dungeons){
			if(d.players.contains(p)){
				return d;
			}
		}
		return null;
	}
	
	
}
