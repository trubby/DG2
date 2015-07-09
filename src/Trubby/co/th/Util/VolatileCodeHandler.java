package Trubby.co.th.Util;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public abstract interface VolatileCodeHandler {
	public abstract void PlaySoundAtLocation(Location paramLocation, String paramString, float paramFloat1, float paramFloat2);

	public abstract void CreateFireworksExplosion(Location paramLocation, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int[] paramArrayOfInt1,
			int[] paramArrayOfInt2, int paramInt2);

	public abstract void doParticleEffect(Location paramLocation, String paramString, float paramFloat1, float paramFloat2, int paramInt1, float paramFloat3,
			float paramFloat4, int paramInt2);

	public abstract void setMaxHealth(Entity paramEntity, double paramDouble);

	public abstract void setFollowRange(Entity paramEntity, double paramDouble);

	public abstract void setKnockBackResistance(Entity paramEntity, double paramDouble);

	public abstract void setMobSpeed(Entity paramEntity, double paramDouble);

	public abstract void setAttackDamage(Entity paramEntity, double paramDouble);

	public abstract void setCreeperFuseTicks(Creeper paramCreeper, int paramInt);

	public abstract void setCreeperExplosionRadius(Creeper paramCreeper, int paramInt);

	public abstract void setZombieSpawnReinforcements(Zombie paramZombie, double paramDouble);

	public abstract void setEntitySilent(Entity paramEntity);

	public abstract ItemStack setItemUnbreakable(ItemStack paramItemStack);

	public abstract ItemStack setItemHideFlags(ItemStack paramItemStack);

	public abstract ItemStack setItemAttribute(String paramString, double paramDouble, ItemStack paramItemStack);

	public abstract void listItemAttributes(Player paramPlayer);

	public abstract void setChickenHostile(Chicken paramChicken);

	public abstract void setTarget(LivingEntity paramLivingEntity1, LivingEntity paramLivingEntity2);

	public abstract void aiGoalSelectorHandler(LivingEntity paramLivingEntity, List<String> paramList);

	public abstract void aiTargetSelectorHandler(LivingEntity paramLivingEntity, List<String> paramList);

	public abstract void sendTitleToPlayer(Player paramPlayer, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3);

	public abstract void sendActionBarMessageToPlayer(Player paramPlayer, String paramString);

	public static enum Attributes {
		DAMAGE(0, "generic.attackDamage"), MOVEMENT_SPEED(2, "generic.movementSpeed"), KNOCKBACK_RESISTANCE(2, "generic.knockbackResistance"), MAX_HEALTH(0,
				"generic.maxHealth"), FOLLOW_RANGE(0, "generic.followRange");

		public int op;
		public String name;

		private Attributes(int op, String name) {
			this.op = op;
			this.name = name;
		}

		public static Attributes get(String s) {
			for (Attributes a : Attributes.values()) {
				if (a.name().toLowerCase().equalsIgnoreCase(s)) {
					return a;
				}
			}
			return null;
		}

		public static Attributes getByMCName(String s) {
			for (Attributes a : Attributes.values()) {
				if (a.name.equalsIgnoreCase(s)) {
					return a;
				}
			}
			return null;
		}
	}
}
