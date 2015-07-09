package Trubby.co.th.Util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeModifiable;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftChicken;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreeper;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class VolatileCodeEnabled_v1_8_R3 implements VolatileCodeHandler {
	public VolatileCodeEnabled_v1_8_R3() {
		try {
			this.packet63Fields[0] = PacketPlayOutWorldParticles.class.getDeclaredField("a");
			this.packet63Fields[1] = PacketPlayOutWorldParticles.class.getDeclaredField("b");
			this.packet63Fields[2] = PacketPlayOutWorldParticles.class.getDeclaredField("c");
			this.packet63Fields[3] = PacketPlayOutWorldParticles.class.getDeclaredField("d");
			this.packet63Fields[4] = PacketPlayOutWorldParticles.class.getDeclaredField("e");
			this.packet63Fields[5] = PacketPlayOutWorldParticles.class.getDeclaredField("f");
			this.packet63Fields[6] = PacketPlayOutWorldParticles.class.getDeclaredField("g");
			this.packet63Fields[7] = PacketPlayOutWorldParticles.class.getDeclaredField("h");
			this.packet63Fields[8] = PacketPlayOutWorldParticles.class.getDeclaredField("i");
			this.packet63Fields[9] = PacketPlayOutWorldParticles.class.getDeclaredField("j");
			this.packet63Fields[10] = PacketPlayOutWorldParticles.class.getDeclaredField("k");
			for (int i = 0; i <= 10; i++) {
				this.packet63Fields[i].setAccessible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void PlaySoundAtLocation(Location location, String sound, float volume, float pitch) {
		((CraftWorld) location.getWorld()).getHandle().makeSound(location.getX(), location.getY(), location.getZ(), sound, volume, pitch);
	}

	public void CreateFireworksExplosion(Location location, boolean flicker, boolean trail, int type, int[] colors, int[] fadeColors, int flightDuration) {
		net.minecraft.server.v1_8_R3.ItemStack item = new net.minecraft.server.v1_8_R3.ItemStack(Item.getById(401), 1, 0);

		NBTTagCompound tag = item.getTag();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		NBTTagCompound explTag = new NBTTagCompound();
		explTag.setByte("Flicker", (byte) (flicker ? 1 : 0));
		explTag.setByte("Trail", (byte) (trail ? 1 : 0));
		explTag.setByte("Type", (byte) type);
		explTag.setIntArray("Colors", colors);
		explTag.setIntArray("FadeColors", fadeColors);

		NBTTagCompound fwTag = new NBTTagCompound();
		fwTag.setByte("Flight", (byte) flightDuration);
		NBTTagList explList = new NBTTagList();
		explList.add(explTag);
		fwTag.set("Explosions", explList);
		tag.set("Fireworks", fwTag);

		item.setTag(tag);

		EntityFireworks fireworks = new EntityFireworks(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), item);
		((CraftWorld) location.getWorld()).getHandle().addEntity(fireworks);
		if (flightDuration == 0) {
			((CraftWorld) location.getWorld()).getHandle().broadcastEntityEffect(fireworks, (byte) 17);
			fireworks.die();
		}
	}

	Field[] packet63Fields = new Field[11];

	public void setMaxHealth(org.bukkit.entity.Entity e, double health) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.maxHealth);
		attributes.setValue(health);
		LivingEntity l = (LivingEntity) e;
		l.setHealth(l.getMaxHealth());
	}

	public void setFollowRange(org.bukkit.entity.Entity e, double range) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
		if (attributes != null) {
			attributes.setValue(range);
		}
	}

	public void setKnockBackResistance(org.bukkit.entity.Entity e, double knock) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.c);
		if (attributes != null) {
			attributes.setValue(knock);
		}
	}

	public void setMobSpeed(org.bukkit.entity.Entity e, double speed) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
		if (attributes != null) {
			attributes.setValue(speed);
		}
	}

	public void setAttackDamage(org.bukkit.entity.Entity e, double damage) {
		AttributeInstance attributes = ((EntityInsentient) ((CraftLivingEntity) e).getHandle()).getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
		if (attributes != null) {
			attributes.setValue(damage);
		}
	}

	public org.bukkit.inventory.ItemStack setItemAttribute(String attr, double amount, org.bukkit.inventory.ItemStack item) {
		VolatileCodeHandler.Attributes a = VolatileCodeHandler.Attributes.get(attr);
		int op = -1;

		net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
		NBTTagList attrmod = getItemAttributeList(nms);
		NBTTagCompound c = new NBTTagCompound();
		if (op == -1) {
			op = a.op;
		}
		c.set("Name", new NBTTagString(attr));
		c.set("AttributeName", new NBTTagString(a.name));
		c.set("Amount", new NBTTagDouble(amount));
		c.set("Operation", new NBTTagInt(op));
		UUID randUUID = UUID.randomUUID();
		c.set("UUIDMost", new NBTTagLong(randUUID.getMostSignificantBits()));
		c.set("UUIDLeast", new NBTTagLong(randUUID.getLeastSignificantBits()));
		attrmod.add(c);
		nms.getTag().set("AttributeModifiers", attrmod);
		org.bukkit.inventory.ItemStack i = CraftItemStack.asCraftMirror(nms);

		return i;
	}

	public NBTTagList getItemAttributeList(net.minecraft.server.v1_8_R3.ItemStack nms) {
		if (nms.getTag() == null) {
			nms.setTag(new NBTTagCompound());
		}
		NBTTagList attrmod = nms.getTag().getList("AttributeModifiers", 10);
		if (attrmod == null) {
			nms.getTag().set("AttributeModifiers", new NBTTagList());
		}
		return nms.getTag().getList("AttributeModifiers", 10);
	}

	public void listItemAttributes(Player player) {
	}

	public void setCreeperFuseTicks(Creeper c, int t) {
		EntityCreeper entCreeper = ((CraftCreeper) c).getHandle();
		Field fuseF = null;
		try {
			fuseF = EntityCreeper.class.getDeclaredField("maxFuseTicks");
		} catch (Exception ex) {
		}
		fuseF.setAccessible(true);
		try {
			fuseF.setInt(entCreeper, t);
		} catch (Exception ex) {
		}
	}

	public void setCreeperExplosionRadius(Creeper c, int r) {
		EntityCreeper entCreeper = ((CraftCreeper) c).getHandle();
		Field radiusF = null;
		try {
			radiusF = EntityCreeper.class.getDeclaredField("explosionRadius");
		} catch (Exception ex) {
		}
		radiusF.setAccessible(true);
		try {
			radiusF.setInt(entCreeper, r);
		} catch (Exception ex) {
		}
	}

	public void setZombieSpawnReinforcements(Zombie z, double d) {
		EntityZombie zombie = ((CraftZombie) z).getHandle();

		AttributeModifiable ar = (AttributeModifiable) zombie.getAttributeMap().a("Spawn Reinforcements Chance");

		ar.setValue(d);
	}

	public void setEntitySilent(org.bukkit.entity.Entity e) {
		net.minecraft.server.v1_8_R3.Entity ee = ((CraftEntity) e).getHandle();
		ee.b(true);
	}

	public void setChickenHostile(Chicken c) {
		EntityChicken chicken = ((CraftChicken) c).getHandle();

		chicken.i(true);
	}

	public void setTarget(LivingEntity entity, LivingEntity target) {
		if (target == null) {
			return;
		}
		if ((entity instanceof Creature)) {
			((Creature) entity).setTarget(target);
		}
		((EntityInsentient) ((CraftLivingEntity) entity).getHandle()).setGoalTarget(((CraftLivingEntity) target).getHandle());
	}

	/*
	public void aiGoalSelectorHandler(LivingEntity entity, List<String> aiMods) {
		try {
			EntityInsentient e = (EntityInsentient) ((CraftLivingEntity) entity).getHandle();

			Field goalsField = EntityInsentient.class.getDeclaredField("goalSelector");
			goalsField.setAccessible(true);
			PathfinderGoalSelector goals = (PathfinderGoalSelector) goalsField.get(e);

			int i = 0;
			int j = 0;
			for (String str : aiMods) {
				i++;
				String[] split = str.split(" ");
				String data2;
				String goal;
				if (split[0].matches("[0-9]*")) {
					j = Integer.parseInt(split[0]);
					goal = split[1];
					String data;
					if (split.length > 2) {
						data = split[2];
					} else {
						data = null;
					}
					if (split.length > 3) {
						data2 = split[3];
					} else {
						data2 = null;
					}
				} else {
					j = i;
					goal = split[0];
					String data;
					if (split.length > 1) {
						data = split[1];
					} else {
						data = null;
					}
					if (split.length > 2) {
						data2 = split[2];
					} else {
						data2 = null;
					}
				}
				switch (goal.toLowerCase()) {
				case "reset":
				case "clear":
					Field listField = PathfinderGoalSelector.class.getDeclaredField("b");
					listField.setAccessible(true);
					List list = (List) listField.get(goals);
					list.clear();
					listField = PathfinderGoalSelector.class.getDeclaredField("c");
					listField.setAccessible(true);
					list = (List) listField.get(goals);
					list.clear();

					break;
				case "arrowattack":
					if ((e instanceof IRangedEntity)) {
						goals.a(j, new PathfinderGoalArrowAttack((IRangedEntity) e, 1.0D, 20, 60, 15.0F));
					}
					break;
				case "breakdoor":
					goals.a(j, new PathfinderGoalBreakDoor(e));
					break;
				case "eatgrass":
					goals.a(j, new PathfinderGoalEatTile(e));
					break;
				case "fleegolems":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalAvoidTarget((EntityCreature) e, EntityIronGolem.class, 6.0F, 1.0D, 1.2D));
					}
					break;
				case "fleevillagers":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalAvoidTarget((EntityCreature) e, EntityVillager.class, 6.0F, 1.0D, 1.2D));
					}
					break;
				case "fleesun":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalFleeSun((EntityCreature) e, 1.0D));
					}
					break;
				case "float":
				case "swim":
					goals.a(j, new PathfinderGoalFloat(e));
					break;
				case "lookatplayers":
					goals.a(j, new PathfinderGoalLookAtPlayer(e, EntityHuman.class, 5.0F, 1.0F));
					break;
				case "meleeattack":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalMeleeAttack((EntityCreature) e, 1.0D, true));
					}
					break;
				case "movethroughvillage":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalMoveThroughVillage((EntityCreature) e, 0.6D, true));
					}
					break;
				case "movetowardstarget":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalMoveTowardsTarget((EntityCreature) e, 0.9D, 32.0F));
					}
					break;
				case "opendoor":
				case "opendoors":
					goals.a(j, new PathfinderGoalOpenDoor(e, true));
					break;
				case "randomlookaround":
				case "lookaround":
					goals.a(j, new PathfinderGoalRandomLookaround(e));
					break;
				case "randomstroll":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalRandomStroll((EntityCreature) e, 1.0D));
					}
					break;
				case "restrictsun":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalRestrictSun((EntityCreature) e));
					}
					break;
				}
			}
		} catch (Exception ex) {
			EntityInsentient e;
			PathfinderGoalSelector goals;
			int i;
			int j;
		}
	}

	public void aiTargetSelectorHandler(LivingEntity entity, List<String> aiMods) {
		try {
			EntityInsentient e = (EntityInsentient) ((CraftLivingEntity) entity).getHandle();

			Field goalsField = EntityInsentient.class.getDeclaredField("targetSelector");
			goalsField.setAccessible(true);
			PathfinderGoalSelector goals = (PathfinderGoalSelector) goalsField.get(e);

			int i = 0;
			int j = 0;
			for (String str : aiMods) {
				i++;
				String[] split = str.split(" ");
				String data;
				String goal;
				if (split[0].matches("[0-9]*")) {
					j = Integer.parseInt(split[0]);
					goal = split[1];
					if (split.length > 2) {
						data = split[2];
					} else {
						data = "";
					}
				} else {
					j = i;
					goal = split[0];
					if (split.length > 1) {
						data = split[1];
					} else {
						data = "";
					}
				}
				switch (goal.toLowerCase()) {
				case "reset":
				case "clear":
					Field listField = PathfinderGoalSelector.class.getDeclaredField("b");
					listField.setAccessible(true);
					List list = (List) listField.get(goals);
					list.clear();
					listField = PathfinderGoalSelector.class.getDeclaredField("c");
					listField.setAccessible(true);
					list = (List) listField.get(goals);
					list.clear();

					break;
				case "hurtbytarget":
				case "damager":
				case "attacker":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalHurtByTarget((EntityCreature) e, false, new Class[0]));
					}
					break;
				case "ownerhurttarget":
				case "ownertarget":
					if ((e instanceof EntityTameableAnimal)) {
						goals.a(j, new PathfinderGoalHurtByTarget((EntityCreature) e, false, new Class[0]));
					}
					break;
				case "monsters":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalNearestAttackableTarget((EntityCreature) e, EntityInsentient.class, 0, true, false, IMonster.d));
					}
					break;
				case "players":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalNearestAttackableTarget((EntityCreature) e, EntityHuman.class, true));
					}
					break;
				case "villagers":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalNearestAttackableTarget((EntityCreature) e, EntityVillager.class, false));
					}
					break;
				case "iron_golems":
				case "golems":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalNearestAttackableTarget((EntityCreature) e, EntityIronGolem.class, false));
					}
					break;
				case "otherfaction":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalNearestAttackableOtherFactionTarget((EntityCreature) e, EntityInsentient.class, 0, true));
						goals.a(j, new PathfinderGoalNearestAttackableOtherFactionTarget((EntityCreature) e, EntityHuman.class, 0, true));
					}
					break;
				case "otherfactionmonsters":
					if ((e instanceof EntityCreature)) {
						goals.a(j,
								new PathfinderGoalNearestAttackableOtherFactionTarget((EntityCreature) e, EntityInsentient.class, 0, true, false, IMonster.d));
					}
					break;
				case "otherfactionvillagers":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalNearestAttackableOtherFactionTarget((EntityCreature) e, EntityVillager.class, 0, false));
					}
					break;
				case "specificfaction":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalNearestAttackableSpecificFactionTarget((EntityCreature) e, EntityInsentient.class, data, 0, false));
					}
					break;
				case "specificfactionmonsters":
					if ((e instanceof EntityCreature)) {
						goals.a(j, new PathfinderGoalNearestAttackableSpecificFactionTarget((EntityCreature) e, EntityInsentient.class, data, 0, true, false,
								IMonster.d));
					}
					break;
				}
			}
		} catch (Exception ex) {
			EntityInsentient e;
			PathfinderGoalSelector goals;
			int i;
			int j;
		}
	}

	public class PathfinderGoalNearestAttackableOtherFactionTarget extends PathfinderGoalNearestAttackableTarget {
		public PathfinderGoalNearestAttackableOtherFactionTarget(EntityCreature entitycreature, Class oclass, int i, boolean flag) {
			this(entitycreature, oclass, i, flag, false);
		}

		public PathfinderGoalNearestAttackableOtherFactionTarget(EntityCreature entitycreature, Class oclass, int i, boolean flag, boolean flag1) {
			this(entitycreature, oclass, i, flag, flag1, (Predicate) null);
		}

		public PathfinderGoalNearestAttackableOtherFactionTarget(EntityCreature entitycreature, Class oclass, int i, boolean flag, boolean flag1,
				Predicate ientityselector) {
			super(entitycreature, oclass, i, flag, flag1, ientityselector);
		}*/

		/*
		
		public boolean a() {
			int pB = ((Integer) MythicUtil.getPrivateField("g", PathfinderGoalNearestAttackableTarget.class, this)).intValue();
			if ((pB > 0) && (MythicMobs.r.nextInt(pB) != 0)) {
				return false;
			}
			Class pA = (Class) MythicUtil.getPrivateField("a", PathfinderGoalNearestAttackableTarget.class, this);
			PathfinderGoalNearestAttackableTarget.DistanceComparator pE = (PathfinderGoalNearestAttackableTarget.DistanceComparator) MythicUtil
					.getPrivateField("b", PathfinderGoalNearestAttackableTarget.class, this);
			Predicate pF = (Predicate) MythicUtil.getPrivateField("c", PathfinderGoalNearestAttackableTarget.class, this);

			double d0 = f();
			List list = this.e.world.a(pA, this.e.getBoundingBox().grow(d0, 4.0D, d0), pF);
			list.remove(this.c);
			if (list.isEmpty()) {
				return false;
			}
			Collections.sort(list, pE);

			ActiveMob am = ActiveMobHandler.getMythicMobInstance((LivingEntity) this.e.getBukkitEntity());
			if (am.getType().hasFaction()) {
				for (int i = 0; i < list.size(); i++) {
					EntityLiving el = (EntityLiving) list.get(i);
					if ((ActiveMobHandler.isRegisteredMob((LivingEntity) el.getBukkitEntity()))
							&& (ActiveMobHandler.getMythicMobInstance((LivingEntity) el.getBukkitEntity()).getType().hasFaction())) {
						if (!ActiveMobHandler.getMythicMobInstance((LivingEntity) el.getBukkitEntity()).getType().getFaction()
								.equals(am.getType().getFaction())) {
							MythicUtil.setPrivateField("d", PathfinderGoalNearestAttackableTarget.class, this, el);
							return true;
						}
					} else {
						MythicUtil.setPrivateField("d", PathfinderGoalNearestAttackableTarget.class, this, el);
						return true;
					}
				}
				return false;
			}
			MythicUtil.setPrivateField("d", PathfinderGoalNearestAttackableTarget.class, this, (EntityLiving) list.get(0));
			return true;
		}
	}

	public class PathfinderGoalNearestAttackableSpecificFactionTarget extends PathfinderGoalNearestAttackableTarget {
		private final String faction;

		public PathfinderGoalNearestAttackableSpecificFactionTarget(EntityCreature entitycreature, Class oclass, String faction, int i, boolean flag) {
			this(entitycreature, oclass, faction, i, flag, false);
		}

		public PathfinderGoalNearestAttackableSpecificFactionTarget(EntityCreature entitycreature, Class oclass, String faction, int i, boolean flag,
				boolean flag1) {
			this(entitycreature, oclass, faction, i, flag, flag1, (Predicate) null);
		}

		public PathfinderGoalNearestAttackableSpecificFactionTarget(EntityCreature entitycreature, Class oclass, String faction, int i, boolean flag,
				boolean flag1, Predicate ientityselector) {
			super(oclass, i, flag, flag1, ientityselector);
			this.faction = faction;
		}

		public boolean a() {
			int pB = ((Integer) MythicUtil.getPrivateField("g", PathfinderGoalNearestAttackableTarget.class, this)).intValue();
			if ((pB > 0) && (MythicMobs.r.nextInt(pB) != 0)) {
				return false;
			}
			Class pA = (Class) MythicUtil.getPrivateField("a", PathfinderGoalNearestAttackableTarget.class, this);
			PathfinderGoalNearestAttackableTarget.DistanceComparator pE = (PathfinderGoalNearestAttackableTarget.DistanceComparator) MythicUtil
					.getPrivateField("b", PathfinderGoalNearestAttackableTarget.class, this);
			Predicate pF = (Predicate) MythicUtil.getPrivateField("c", PathfinderGoalNearestAttackableTarget.class, this);

			double d0 = f();
			List list = this.e.world.a(pA, this.e.getBoundingBox().grow(d0, 4.0D, d0), pF);
			list.remove(this.c);
			if (list.isEmpty()) {
				return false;
			}
			Collections.sort(list, pE);
			for (int i = 0; i < list.size(); i++) {
				EntityLiving el = (EntityLiving) list.get(i);
				if ((ActiveMobHandler.isRegisteredMob((LivingEntity) el.getBukkitEntity()))
						&& (ActiveMobHandler.getMythicMobInstance((LivingEntity) el.getBukkitEntity()).getType().hasFaction())
						&& (ActiveMobHandler.getMythicMobInstance((LivingEntity) el.getBukkitEntity()).getType().getFaction().equals(this.faction))) {
					MythicUtil.setPrivateField("d", PathfinderGoalNearestAttackableTarget.class, this, el);
					return true;
				}
			}
			return false;
		}
	}

	public class PathfinderGoalGoToLocation extends PathfinderGoal {
		private double speed;
		private EntityInsentient entity;
		private Location loc;
		private NavigationAbstract navigation;

		public PathfinderGoalGoToLocation(EntityInsentient entity, Location loc, double speed) {
			this.entity = entity;
			this.loc = loc;
			this.navigation = this.entity.getNavigation();
			this.speed = speed;
		}

		public boolean a() {
			return true;
		}

		public void c() {
			PathEntity pathEntity = this.navigation.a(this.loc.getX(), this.loc.getY(), this.loc.getZ());

			this.navigation.a(pathEntity, this.speed);
		}
	}
	
	*/

	public org.bukkit.inventory.ItemStack setItemUnbreakable(org.bukkit.inventory.ItemStack i) {
		if (!(i instanceof CraftItemStack)) {
			i = CraftItemStack.asCraftCopy(i);
		}
		NBTTagCompound tag = getTag(i);
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setByte("Unbreakable", (byte) 1);
		return setTag(i, tag);
	}

	public org.bukkit.inventory.ItemStack setItemHideFlags(org.bukkit.inventory.ItemStack i) {
		if (!(i instanceof CraftItemStack)) {
			i = CraftItemStack.asCraftCopy(i);
		}
		NBTTagCompound tag = getTag(i);
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setInt("HideFlags", 63);
		return setTag(i, tag);
	}

	public void sendTitleToPlayer(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;

		PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
		conn.sendPacket(packet);
		if (title != null) {
			packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(title));
			conn.sendPacket(packet);
		}
		if (subtitle != null) {
			packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(subtitle));
			conn.sendPacket(packet);
		}
	}

	public void sendActionBarMessageToPlayer(Player player, String message) {
		PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
		PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte) 2);
		conn.sendPacket(packet);
	}

	private static NBTTagCompound getTag(org.bukkit.inventory.ItemStack item) {
		if ((item instanceof CraftItemStack)) {
			try {
				Field field = CraftItemStack.class.getDeclaredField("handle");
				field.setAccessible(true);
				return ((net.minecraft.server.v1_8_R3.ItemStack) field.get(item)).getTag();
			} catch (Exception e) {
			}
		}
		return null;
	}

	private static org.bukkit.inventory.ItemStack setTag(org.bukkit.inventory.ItemStack item, NBTTagCompound tag) {
		CraftItemStack craftItem = null;
		if ((item instanceof CraftItemStack)) {
			craftItem = (CraftItemStack) item;
		} else {
			craftItem = CraftItemStack.asCraftCopy(item);
		}
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = null;
		try {
			Field field = CraftItemStack.class.getDeclaredField("handle");
			field.setAccessible(true);
			nmsItem = (net.minecraft.server.v1_8_R3.ItemStack) field.get(item);
		} catch (Exception e) {
		}
		if (nmsItem == null) {
			nmsItem = CraftItemStack.asNMSCopy(craftItem);
		}
		nmsItem.setTag(tag);
		try {
			Field field = CraftItemStack.class.getDeclaredField("handle");
			field.setAccessible(true);
			field.set(craftItem, nmsItem);
		} catch (Exception e) {
		}
		return craftItem;
	}

	@Override
	public void doParticleEffect(Location paramLocation, String paramString, float paramFloat1, float paramFloat2, int paramInt1, float paramFloat3,
			float paramFloat4, int paramInt2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aiGoalSelectorHandler(LivingEntity paramLivingEntity, List<String> paramList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aiTargetSelectorHandler(LivingEntity paramLivingEntity, List<String> paramList) {
		// TODO Auto-generated method stub
		
	}
}
