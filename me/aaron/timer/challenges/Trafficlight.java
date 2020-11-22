package me.aaron.timer.challenges;

import me.aaron.timer.Main;
import me.aaron.timer.timer.Timer;
import me.aaron.timer.utils.SettingsItems;
import me.aaron.timer.utils.SettingsModes;
import me.aaron.timer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Trafficlight {
    public static int TrafficLightScheduler;
    private Main plugin;
    private BossBar bossBar;
    private int neededtime;
    private int currenttime;

    public Trafficlight(Main plugin) {
        this.plugin = plugin;
    }

    public void start() {
        currentstate = TrafficLightState.GREEN;
        neededtime = Utils.getRandomInt(180, 300);
        currenttime = 0;
        bossBar = Bukkit.createBossBar("Der Timer ist pausiert.", BarColor.WHITE, BarStyle.SOLID);
        bossBar.setVisible(true);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            addPlayer(pl);
        }
        update();
    }

    public void update() {
            TrafficLightScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(Main.class), new Runnable() {
                @Override
                public void run() {
                    if (SettingsModes.challenge.get(SettingsItems.ItemType.TRAFFICLIGHT) == SettingsItems.ItemState.ENABLED) {
                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            addPlayer(pl);
                        }
                    if (Timer.state == Timer.TimerState.RUNNING || SettingsModes.settings.get(SettingsItems.ItemType.TIMER) == SettingsItems.ItemState.DISABLED) {
                            if (currentstate == TrafficLightState.GREEN) {
                                if (currenttime != neededtime) {
                                    bossBar.setTitle("§8[ §a█████ §8] §8[ §7█████ §8] §8[ §7█████ §8]");
                                    bossBar.setColor(BarColor.GREEN);
                                    currenttime++;
                                } else {
                                    neededtime = 3;
                                    currenttime = 0;
                                    currentstate = TrafficLightState.YELLOW;
                                    for (World wl : Bukkit.getWorlds()) {
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            wl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 100, 1);
                                        }
                                    }
                                }
                            }
                            if (currentstate == TrafficLightState.YELLOW) {
                                if (currenttime != neededtime) {
                                    bossBar.setTitle("§8[ §7█████ §8] §8[ §e█████ §8] §8[ §7█████ §8]");
                                    bossBar.setColor(BarColor.YELLOW);
                                    currenttime++;
                                } else {
                                    neededtime = Utils.getRandomInt(4, 7);
                                    currenttime = 0;
                                    currentstate = TrafficLightState.RED;
                                    for (World wl : Bukkit.getWorlds()) {
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            wl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 100, 0);
                                        }
                                    }
                                }
                            }
                            if (currentstate == TrafficLightState.RED) {
                                if (currenttime != neededtime) {
                                    bossBar.setTitle("§8[ §7█████ §8] §8[ §7█████ §8] §8[ §c█████ §8]");
                                    bossBar.setColor(BarColor.RED);
                                    currenttime++;
                                } else {
                                    neededtime = 2;
                                    currenttime = 0;
                                    currentstate = TrafficLightState.YELLOW_RED;
                                    for (World wl : Bukkit.getWorlds()) {
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            wl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 100, 1);
                                        }
                                    }
                                }
                            }
                            if (currentstate == TrafficLightState.YELLOW_RED) {
                                if (currenttime != neededtime) {
                                    bossBar.setTitle("§8[ §7█████ §8] §8[ §e█████ §8] §8[ §c█████ §8]");
                                    bossBar.setColor(BarColor.RED);
                                    currenttime++;
                                } else {
                                    neededtime = Utils.getRandomInt(180, 300);
                                    currenttime = 0;
                                    currentstate = TrafficLightState.GREEN;
                                    for (World wl : Bukkit.getWorlds()) {
                                        for (Player pl : Bukkit.getOnlinePlayers()) {
                                            wl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 100, 2);
                                        }
                                    }
                                }
                            }
                        } else {
                        bossBar.setColor(BarColor.WHITE);
                        bossBar.setTitle("Der Timer ist pausiert.");
                    }
                    }
                    else {
                        bossBar.setVisible(false);
                    }
                }
        }, 0, 20);
    }

    public void addPlayer(Player p) {
        bossBar.addPlayer(p);
    }

    public void removePlayer(Player p) {
        bossBar.removePlayer(p);
    }

    public BossBar getBossBar() {
        return bossBar;
    }


    public enum TrafficLightState {
        GREEN,
        YELLOW,
        RED,
        YELLOW_RED
    }

    public static TrafficLightState currentstate = TrafficLightState.GREEN;

}
