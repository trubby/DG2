package Trubby.co.th;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MobsListener implements Listener {

	@EventHandler
	public void onCreeperDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Creeper) {
			Entity en = e.getEntity();
			Bukkit.getScheduler().scheduleSyncDelayedTask(DG.plugin,
					new Runnable() {

						@Override
						public void run() {
							Location loc = en.getLocation();
							en.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 3f, false, false);
							en.remove();
						}
					}, 35L);
		}
	}
	
	@EventHandler
	public void onCreeperTarget(EntityTargetLivingEntityEvent e){
		if(e.getEntity() instanceof Creeper){
			if(e.getTarget() instanceof Player){
				LivingEntity le = (LivingEntity) e.getEntity();
				le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2, true, true));
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		event.blockList().clear(); // clears list of blocks to be blown up
	}
	
	@EventHandler
	public void onItemDestroy(EntityDamageEvent e){
	}
	
	

}
