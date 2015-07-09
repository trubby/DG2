package Trubby.co.th;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import Trubby.co.th.Map.Dungeon;
import Trubby.co.th.Map.Room;

public class PlayerListener implements Listener{

	Vector vMin = null;
	Vector vMax = null;
	
	public void onInteract(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		if(p.getItemInHand().getType() == Material.APPLE){
			
			vMin = p.getLocation().getBlock().getLocation().toVector();
			p.sendMessage("vMin Set!" + vMin);
		}else if(p.getItemInHand().getType() == Material.GOLDEN_APPLE){
			
			vMax = p.getLocation().getBlock().getLocation().toVector();
			p.sendMessage("vMax Set!" + vMax);
		}
		
		
	}
	
    public void onPlayerMove(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        final Vector pVec = e.getTo().toVector();
 
        if ((e.getFrom().getBlockX() != e.getTo().getBlockX()) || (e.getFrom().getBlockZ() != e.getTo().getBlockZ())) {
 
            if (vMin != null && vMax != null) {
                if (contains(vMin, vMax, pVec)) {
                    System.out.println("player is in region");
                } else {
                    System.out.println(" player is not in region ");
                }
            }else{
            	System.out.println("null");
            }
 
        }
    }

    
	public boolean contains(Vector vMin, Vector vMax, Vector pt) {
		double x = pt.getX();
		double y = pt.getY();
		double z = pt.getZ();
		
		if(vMin == null){
			Bukkit.broadcastMessage("min null");
			return false;
		}
		
		if(vMax == null){
			Bukkit.broadcastMessage("max null");
			return false;
		}
		
		/*boolean a = (x >= vMin.getBlockX());
		boolean b = (x < this.vMax.getBlockX() + 1);
		boolean c = (y >= this.vMin.getBlockY());
		boolean d = (y < this.vMax.getBlockY() + 1);
		boolean e = (z >= this.vMin.getBlockZ());
		boolean f = (z < this.vMax.getBlockZ() + 1);;
		
		Bukkit.broadcastMessage(a + " " + b + " " + c + " " + d + " " + e + " " + f );*/
		
		return (x >= vMin.getBlockX()) && (x < vMax.getBlockX() + 1)
				&& (y >= vMin.getBlockY())
				&& (y < vMax.getBlockY() + 1)
				&& (z >= vMin.getBlockZ())
				&& (z < vMax.getBlockZ() + 1);
	}
    
	
	@EventHandler
    public void onPlayerMoveInDungeon(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        
        Dungeon d = DG.plugin.dm.getDungeon(p);
        if(d != null){
            final Vector pVec = p.getLocation().toVector();
            
            if ((e.getFrom().getBlockX() != e.getTo().getBlockX()) || (e.getFrom().getBlockZ() != e.getTo().getBlockZ())) {
            	
            	if(d.step >= d.rooms.size()){
            		return;
            	}
            	
            	
            	Room r = d.rooms.get(d.step);
            	if(contains(r.min, r.max, pVec)){
            		p.sendMessage("You're enter "+ d.step);
            		d.spawnMobs(r);
            		
            		d.step++;
            		
            		return;
            	}
            }
        }
    }
	
}
