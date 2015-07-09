package Trubby.co.th;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import net.elseland.xikage.MythicMobs.MythicMobs;
import net.elseland.xikage.MythicMobs.Items.MythicItem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.EnderChest;
import org.bukkit.plugin.java.JavaPlugin;

import Trubby.co.th.Map.Dungeon;
import Trubby.co.th.Map.DungeonManager;
import Trubby.co.th.Map.Room;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

@SuppressWarnings("deprecation")
public class DG extends JavaPlugin {

	ArrayList<CuboidClipboard> ccList = new ArrayList<>();
	ArrayList<CuboidClipboard> ccBridgeList = new ArrayList<>();
	CuboidClipboard ccFirst;
	public DungeonManager dm;

	MythicMobs my;

	public static DG plugin;

	@Override
	public void onEnable() {
		plugin = this;

		my = (MythicMobs) Bukkit.getServer().getPluginManager().getPlugin("MythicMobs");

		Bukkit.getPluginManager().registerEvents(new MobsListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

		dm = new DungeonManager();

		File dataDirectory = this.getDataFolder();
		File schematicsDirectory = new File(dataDirectory, "schematics");

		File[] schematics = schematicsDirectory.listFiles();

		for (File schematic : schematics) {
			if (schematic.getName().endsWith(".schematic")) {

				SchematicFormat sf = SchematicFormat.getFormat(schematic);
				CuboidClipboard cc = null;

				// IS FIRST
				if (schematic.getName().endsWith("start.schematic")) {
					// ccFirst = FIRST
					try {
						cc = sf.load(schematic);
						ccFirst = cc;

					} catch (IOException | DataException e) {
					}

				} else if (schematic.getName().startsWith("b")) {
					// ADD TO ccBridgeList
					try {
						cc = sf.load(schematic);
						ccBridgeList.add(cc);

					} catch (IOException | DataException e) {
					}
				} else if (schematic.getName().startsWith("r")) {
					// ADD TO ccList
					try {
						cc = sf.load(schematic);
						ccList.add(cc);

					} catch (IOException | DataException e) {
					}
				}
			}
		}

	}

	Random ran = new Random();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (label.equalsIgnoreCase("dgori")) {

			Location loc = create();
			Location oriloc = loc.clone();
			BlockFace bf = BlockFace.NORTH;
			boolean first = true;
			boolean bridge = true;

			Dungeon d = new Dungeon();
			dm.dungeons.add(d);

			// ROOM
			for (int i = 0; i < 1; i++) {

				CuboidClipboard cc;
				// GET FROM LIST
				if (first) {
					cc = ccFirst;
					first = false;
				} else if (bridge) {
					cc = ccBridgeList.get(ran.nextInt(ccBridgeList.size()));
					bridge = false;
				} else {
					cc = ccList.get(ran.nextInt(ccList.size()));
					bridge = true;
				}

				// ROTATE
				if (getDirection(bf) > 0) {
					cc.rotate2D(getDirection(bf));
				}

				/**
				 * OFFSET
				 */
				int offsetX = cc.getOffset().getBlockX();
				int offsetZ = cc.getOffset().getBlockZ();
				int offsetY = cc.getOffset().getBlockY();

				/**
				 * SCANNING
				 */
				for (int y = 0; y < cc.getSize().getBlockY(); y++) {
					for (int x = 0; x < cc.getSize().getBlockX(); x++) {
						for (int z = 0; z < cc.getSize().getBlockZ(); z++) {
							Vector currentPoint = new Vector(x, y, z);
							int currentBlock = cc.getPoint(currentPoint).getType();
							if (currentBlock != 0) {
								if (currentBlock == Material.ENDER_CHEST.getId()) {

									pasteSchematic(loc, cc);

									loc = loc.add(x + offsetX, y + offsetY, z + offsetZ);

									EnderChest ec = (EnderChest) loc.getBlock().getState().getData();
									bf = ec.getFacing();

									System.out.println("Generating structure : " + i + " : " + bf);

									Location locup = loc.clone();
									locup.add(0, 2, 0).getBlock().setType(Material.GOLD_BLOCK);

								}
							}
						}
					}
				}

				/**
				 * ================== MIN MAX ==================
				 */
				double yMax = oriloc.getY() + offsetY + cc.getSize().getBlockY() - 1;
				double xMax = oriloc.getX() + offsetX + cc.getSize().getBlockX() - 1;
				double zMax = oriloc.getZ() + offsetZ + cc.getSize().getBlockZ() - 1;

				double yMin = oriloc.getY() + offsetY;
				double xMin = oriloc.getX() + offsetX;
				double zMin = oriloc.getZ() + offsetZ;

				Location l1 = new Location(loc.getWorld(), xMax, yMax, zMax);
				l1.getBlock().setType(Material.GLASS);
				Location l2 = new Location(loc.getWorld(), xMin, yMin, zMin);
				l2.getBlock().setType(Material.DIAMOND_BLOCK);

				d.rooms.add(new Room(CuboidMin(l1, l2), CuboidMax(l1, l2)));

				oriloc = loc.clone();
			}

			Bukkit.broadcastMessage("DONE!");

		} else if (label.equalsIgnoreCase("rs")) {

			Player p = (Player) sender;

			/*
			 * p.sendMessage(""+MobCommon.getAllMythicMobs()); MythicMob mm =
			 * MobCommon.getMythicMob("testSkel"); if (mm == null) {
			 * p.sendMessage("null"); return false; } Entity e = (Entity)
			 * mm.spawn(p.getLocation(), 1);
			 * 
			 * LivingEntity le = (LivingEntity) e; le.setHealth(0);
			 * p.sendMessage("yay");
			 */

			if (MythicItem.getMythicItem("KingsCrown") != null) {
				MythicItem mi = MythicItem.getMythicItem("KingsCrown");
				p.getInventory().addItem(new ItemStack[] { mi.generateItemStack(1, p, p) });
				p.sendMessage("yay");
			} else {
				p.sendMessage("null");
			}

		} else if (label.equalsIgnoreCase("dg")) {

			Player p = (Player) sender;
			Location loc = p.getLocation();
			Location oriloc = loc.clone();
			BlockFace bf = BlockFace.NORTH;
			boolean first = true;
			boolean bridge = true;

			Dungeon d = new Dungeon();
			d.players.add(p); // TODO add player
			dm.dungeons.add(d);

			// ROOM
			for (int i = 0; i < 3; i++) {

				CuboidClipboard cc;
				// GET FROM LIST
				if (first) {
					cc = ccFirst;
					first = false;
				} else if (bridge) {
					cc = ccBridgeList.get(ran.nextInt(ccBridgeList.size()));
					bridge = false;
				} else {
					cc = ccList.get(ran.nextInt(ccList.size()));
					bridge = true;
				}

				// ROTATE
				if (getDirection(bf) > 0) {
					cc.rotate2D(getDirection(bf));
				}

				/**
				 * OFFSET
				 */
				int offsetX = cc.getOffset().getBlockX();
				int offsetZ = cc.getOffset().getBlockZ();
				int offsetY = cc.getOffset().getBlockY();

				ArrayList<Location> spawners = new ArrayList<Location>();

				/**
				 * SCANNING
				 */
				for (int y = 0; y < cc.getSize().getBlockY(); y++) {
					for (int x = 0; x < cc.getSize().getBlockX(); x++) {
						for (int z = 0; z < cc.getSize().getBlockZ(); z++) {
							Vector currentPoint = new Vector(x, y, z);
							int currentBlock = cc.getPoint(currentPoint).getType();
							if (currentBlock != 0) {
								if (currentBlock == Material.ENDER_CHEST.getId()) {

									pasteSchematic(loc, cc);

									loc = loc.add(x + offsetX, y + offsetY, z + offsetZ);

									EnderChest ec = (EnderChest) loc.getBlock().getState().getData();
									bf = ec.getFacing();

									System.out.println("Generating structure : " + i + " : " + bf);

									Location locup = loc.clone();
									locup.add(0, 2, 0).getBlock().setType(Material.GOLD_BLOCK);

								} else if (currentBlock == Material.MOB_SPAWNER.getId()) {
									Location spawnLoc = oriloc.clone();
									spawnLoc.add(x + offsetX, y + offsetY, z + offsetZ);
									/* spawners.add(spawnLoc); */
									spawners.add(spawnLoc);

									spawnLoc.getBlock().setType(Material.AIR);

									Bukkit.broadcastMessage("found spawner.");
								}
							}
						}
					}
				}

				// It's a room
				if (bridge && i >= 2) {
					/**
					 * ================== MIN MAX ==================
					 */
					double yMax = oriloc.getY() + offsetY + cc.getSize().getBlockY() - 1;
					double xMax = oriloc.getX() + offsetX + cc.getSize().getBlockX() - 1;
					double zMax = oriloc.getZ() + offsetZ + cc.getSize().getBlockZ() - 1;

					double yMin = oriloc.getY() + offsetY;
					double xMin = oriloc.getX() + offsetX;
					double zMin = oriloc.getZ() + offsetZ;

					Location l1 = new Location(p.getWorld(), xMax, yMax, zMax);
					l1.getBlock().setType(Material.GLASS);
					Location l2 = new Location(p.getWorld(), xMin, yMin, zMin);
					l2.getBlock().setType(Material.DIAMOND_BLOCK);

					Room room = new Room(CuboidMin(l1, l2), CuboidMax(l1, l2));
					d.rooms.add(room);

					Bukkit.broadcastMessage("add room");

					if (!spawners.isEmpty()) {
						room.spawners = spawners;
					}

				}

				// UPDATE oriloc
				oriloc = loc.clone();
			}

			Bukkit.broadcastMessage("DONE!");

		}
		return false;
	}

	public org.bukkit.util.Vector CuboidMin(Location l1, Location l2) {
		if (!l1.getWorld().equals(l2.getWorld())) {
			throw new IllegalArgumentException("locations must be on the same world");
		}
		double x1 = Math.min(l1.getBlockX(), l2.getBlockX());
		double y1 = Math.min(l1.getBlockY(), l2.getBlockY());
		double z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());

		return new org.bukkit.util.Vector(x1, y1, z1);
	}

	public org.bukkit.util.Vector CuboidMax(Location l1, Location l2) {
		if (!l1.getWorld().equals(l2.getWorld())) {
			throw new IllegalArgumentException("locations must be on the same world");
		}

		double x2 = Math.max(l1.getBlockX(), l2.getBlockX());
		double y2 = Math.max(l1.getBlockY(), l2.getBlockY());
		double z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());

		return new org.bukkit.util.Vector(x2, y2, z2);
	}

	public BlockFace getBlockFace(Block b) {

		switch (b.getData()) {
		case (byte) 2:
			return BlockFace.NORTH;
		case (byte) 3:
			return BlockFace.SOUTH;
		case (byte) 1:
			return BlockFace.WEST;
		case (byte) 0:
			return BlockFace.EAST;
		default:
			return BlockFace.NORTH;
		}
	}

	public int getDirection(BlockFace face) {
		if (face == BlockFace.NORTH) {
			return 0;
		} else if (face == BlockFace.EAST) {
			return 90;
		} else if (face == BlockFace.WEST) {
			return 270;
		} else if (face == BlockFace.SOUTH) {
			return 180;
		} else {
			return 0;
		}
	}

	public Location create() {
		World world = Bukkit.getServer().getWorld("test");
		if (world == null) {
			this.getLogger().info("Loading world '" + "test" + "'.");
			WorldCreator arenaWorldCreator = new WorldCreator("test");
			arenaWorldCreator.generateStructures(false);
			arenaWorldCreator.generator(new VoidGenerator());
			arenaWorldCreator.type(WorldType.FLAT);
			arenaWorldCreator.environment(Environment.NETHER);
			arenaWorldCreator.seed(0);
			world = arenaWorldCreator.createWorld();
			this.getLogger().info("Done loading world '" + "test" + "'.");
		} else {
			this.getLogger().info("The world '" + "test" + "' was already loaded.");
		}
		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("doMobLoot", "false");
		world.setGameRuleValue("keepInventory", "true");
		world.setGameRuleValue("mobGriefing", "false");
		world.setAutoSave(false);
		world.getBlockAt(-5000, 45, -5000).setType(Material.STONE);
		world.setSpawnLocation(-5000, 50, -5000);

		return new Location(world, -5000, 45, -5000);
	}

	public static void pasteSchematic(Location origin, CuboidClipboard schematic) {
		EditSession editSession = new EditSession(new BukkitWorld(origin.getWorld()), 2147483647);
		editSession.setFastMode(true);

		try {
			schematic.paste(editSession, new Vector(origin.getBlockX(), origin.getBlockY(), origin.getBlockZ()), true);
		} catch (MaxChangedBlocksException ignored) {
		}

	}

}
