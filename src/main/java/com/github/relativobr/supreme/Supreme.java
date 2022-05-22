package com.github.relativobr.supreme;

import com.github.relativobr.generic.MobTechGeneric;
import com.github.relativobr.generic.MobTechGeneric.MobTechType;
import com.github.relativobr.supreme.setup.MainSetup;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.GitHubBuildsUpdater;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Supreme extends JavaPlugin implements SlimefunAddon {

  private static Supreme instance;
  private static Boolean limitProductionQuarry = null;
  private static Boolean limitProductionGenerators = null;
  private static Boolean enableMobtech = null;
  private static Boolean customBc = null;

  public static Supreme inst() {
    return instance;
  }

  public static boolean checkLimitProductionquarry() {
    if(limitProductionQuarry == null) {
      limitProductionQuarry = inst().getConfig().getBoolean("options.limit-production-quarry");
    }
    return limitProductionQuarry;
  }

  private static boolean checkLimitProductionGenerators() {
    if(limitProductionGenerators == null) {
      limitProductionGenerators = inst().getConfig().getBoolean("options.limit-production-generators");
    }
    return limitProductionGenerators;
  }

  public static boolean checkEnableMobtech() {
    if(enableMobtech == null) {
      enableMobtech = inst().getConfig().getBoolean("options.enable-mobtech");
    }
    return enableMobtech;
  }

  public static boolean checkCustomBc() {
    if(customBc == null) {
      customBc = inst().getConfig().getBoolean("options.custom-bc");
    }
    return customBc;
  }

  @Override
  public void onEnable() {

    instance = this;

    Supreme.inst().log(Level.INFO, "########################################");
    Supreme.inst().log(Level.INFO, "                 Supreme                ");
    Supreme.inst().log(Level.INFO, "########################################");

    Config cfg = new Config(this);
    if (cfg.getBoolean("options.auto-update") && getDescription().getVersion()
        .startsWith("DEV - ")) {
      new GitHubBuildsUpdater(this, getFile(), "RelativoBR/Supreme/main").start();
    }

    MainSetup.setup(this);

  }

  @Override
  public void onDisable() {
    instance = null;
  }

  @Override
  public String getBugTrackerURL() {
    return "";
  }

  @Override
  public JavaPlugin getJavaPlugin() {
    return this;
  }

  @Nonnull
  private static Map<Enchantment, Integer> getEnchants(@Nonnull ConfigurationSection section) {
    Map<Enchantment, Integer> enchants = new HashMap<>();
    for (String path : section.getKeys(false)) {
      Enchantment e = new EnchantmentWrapper(path);
      if (e != null) {
        int level = section.getInt(path);
        if (level > 0 && level <= 100) {
          enchants.put(e, level);
        } else if (level != 0) {
          enchants.put(e, 1);
        }
      }
    }
    return enchants;
  }

  public void addSupremeEnchantsAndSoulbound(@Nonnull SlimefunItemStack... items) {

    ConfigurationSection typeSection = this.getConfig().getConfigurationSection("supreme-enchant");

    if (typeSection == null) {
      log(Level.SEVERE,
          "Config section \"supreme-enchant\" missing, Check your config and report this!");
      return;
    }

    for (SlimefunItemStack item : items) {

      ItemMeta meta = item.getItemMeta();

      // lore
      List<String> lore;
      if (meta.hasLore()) {
        lore = meta.getLore();
      } else {
        lore = new ArrayList<>();
        lore.add("");
      }

      lore.add(ChatColor.AQUA + "Soulbound");

      // find path
      String itemPath = item.getItemId().toLowerCase();

      String amplifier = "I";
      if (itemPath.contains("legendary") || itemPath.contains("supreme")) {
        amplifier = "III";
      } else if (itemPath.contains("epic") || itemPath.contains("rare")) {
        amplifier = "II";
      }

      if (itemPath.contains("helmet")) {
        lore.add(ChatColor.DARK_PURPLE + "Night Vision " + amplifier);
        lore.add(ChatColor.DARK_PURPLE + "Conduit Power " + amplifier);
        lore.add(ChatColor.DARK_PURPLE + "Water Breathing " + amplifier);
      } else if (itemPath.contains("chestplate")) {
        lore.add(ChatColor.DARK_PURPLE + "Damage Resistance " + amplifier);
        lore.add(ChatColor.DARK_PURPLE + "Increase Damage " + amplifier);
        lore.add(ChatColor.DARK_PURPLE + "Saturation " + amplifier);
      } else if (itemPath.contains("leggings")) {
        lore.add(ChatColor.DARK_PURPLE + "Speed " + amplifier);
        lore.add(ChatColor.DARK_PURPLE + "Fast Digging " + amplifier);
        lore.add(ChatColor.DARK_PURPLE + "Regeneration " + amplifier);
      } else if (itemPath.contains("boots")) {
        lore.add(ChatColor.DARK_PURPLE + "Dolphins Grace " + amplifier);
        lore.add(ChatColor.DARK_PURPLE + "Fire Resistance " + amplifier);
      }

      meta.setLore(lore);

      ConfigurationSection itemSection = typeSection.getConfigurationSection(itemPath);
      if (itemSection != null) {
        // unbreakable and enchants
        meta.setUnbreakable(itemSection.getBoolean("unbreakable"));
        for (Map.Entry<Enchantment, Integer> entry : getEnchants(itemSection).entrySet()) {
          meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }
      } else {
        log(Level.SEVERE,
            "Config section for " + itemPath + " missing, Check your config and report this!");
      }

      // add meta
      item.setItemMeta(meta);

    }
  }

  public void addGearEnchants(@Nonnull SlimefunItemStack... items) {
    ConfigurationSection typeSection = this.getConfig().getConfigurationSection("supreme-enchant");

    if (typeSection == null) {
      log(Level.SEVERE,
          "Config section \"supreme-enchant\" missing, Check your config and report this!");
      return;
    }

    for (SlimefunItemStack item : items) {

      ItemMeta meta = item.getItemMeta();

      // lore
      List<String> lore;
      if (meta.hasLore()) {
        lore = meta.getLore();
      } else {
        lore = new ArrayList<>();
        lore.add("");
      }

      lore.add(ChatColor.AQUA + "Soulbound");

      // find path
      String itemPath = item.getItemId().toLowerCase();

      meta.setLore(lore);

      ConfigurationSection itemSection = typeSection.getConfigurationSection(itemPath);
      if (itemSection != null) {
        // unbreakable and enchants
        meta.setUnbreakable(itemSection.getBoolean("unbreakable"));
        for (Map.Entry<Enchantment, Integer> entry : getEnchants(itemSection).entrySet()) {
          meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }
      } else {
        log(Level.SEVERE,
            "Config section for " + itemPath + " missing, Check your config and report this!");
      }

      // add meta
      item.setItemMeta(meta);

    }
  }

  public static int getValueGeneratorsWithLimit(int value) {
    return checkLimitProductionGenerators() ? (value / 5) : value;
  }

  public static String buildNameTier(String newName, Integer tier) {
    switch (tier) {
      case 1:
        return ChatColor.GRAY + newName + " I";
      case 2:
        return ChatColor.DARK_GREEN + newName + " II";
      case 3:
        return ChatColor.GREEN + newName + " III";
      case 4:
        return ChatColor.DARK_BLUE + newName + " IV";
      case 5:
        return ChatColor.BLUE + newName + " V";
      case 6:
        return ChatColor.GOLD + newName + " VI";
      case 7:
        return ChatColor.YELLOW + newName + " VII";
      case 8:
        return ChatColor.DARK_RED + newName + " VIII";
      case 9:
        return ChatColor.DARK_PURPLE + newName + " IX";
      default:
        return ChatColor.DARK_GRAY + newName;
    }
  }

  public static String buildIdTier(String newName, Integer tier) {
    switch (tier) {
      case 1:
        return newName + "_I";
      case 2:
        return newName + "_II";
      case 3:
        return newName + "_III";
      case 4:
        return newName + "_IV";
      case 5:
        return newName + "_V";
      case 6:
        return newName + "_VI";
      case 7:
        return newName + "_VII";
      case 8:
        return newName + "_VIII";
      case 9:
        return newName + "_IX";
      default:
        return newName + "_0";
    }
  }

  public static String buildLoreRadioactivityType(MobTechType mobTechType) {
    Radioactivity radioactivity;
    switch (mobTechType) {
      case MUTATION_INTELLIGENCE:
      case MUTATION_BERSERK:
      case MUTATION_LUCK:
        radioactivity = Radioactivity.VERY_DEADLY;
        break;
      case ROBOTIC_CLONING:
      case ROBOTIC_ACCELERATION:
      case ROBOTIC_EFFICIENCY:
        radioactivity = Radioactivity.HIGH;
        break;
      case SIMPLE:
      default:
        radioactivity = Radioactivity.LOW;
    }
    return radioactivity.getLore();
  }

  public static String buildLoreType(MobTechType mobTechType, Integer tier) {
    switch (mobTechType) {
      case MUTATION_BERSERK:
      case ROBOTIC_ACCELERATION:
        return ChatColor.YELLOW + String.valueOf(tier + 1) + "x "
            + ChatColor.GRAY + "increase speed and increase energy";
      case MUTATION_LUCK:
      case ROBOTIC_CLONING:
        return buildLoreTypeLuckAndCloning(tier);
      case MUTATION_INTELLIGENCE:
      case ROBOTIC_EFFICIENCY:
        return ChatColor.YELLOW + String.valueOf(tier + 1) + "x "
            + ChatColor.GRAY + " decrease energy";
      case SIMPLE:
      default:
        return ChatColor.GRAY + "Aumenta Velocidade de processamento";
    }
  }

  private static String buildLoreTypeLuckAndCloning(Integer tier){

    if (tier >= 1) {
      return ChatColor.YELLOW + "2x "
          + ChatColor.GRAY + "Stack clone";
    } else if (tier >= 4) {
      return ChatColor.YELLOW + "3x "
          + ChatColor.GRAY + "Stack clone";
    } else if(tier >= 6){
      return ChatColor.YELLOW + "4x "
          + ChatColor.GRAY + "Stack clone";
    } else if(tier >= 8){
      return ChatColor.YELLOW + "5x "
          + ChatColor.GRAY + "Stack clone";
    }
    return "";
  }

  private static String buildLoreTypeAmount(MobTechType mobTechType, Integer tier){
    switch (mobTechType) {
      case MUTATION_BERSERK:
      case ROBOTIC_ACCELERATION:
        return ChatColor.YELLOW + "(" + String.valueOf(tier + 1) + "x amount stack / 32) speed "
            + ChatColor.GRAY + " value process";
      case MUTATION_INTELLIGENCE:
      case ROBOTIC_EFFICIENCY:
        return ChatColor.YELLOW + "(" + String.valueOf(tier + 1) + " amount stack) J/s "
            + ChatColor.GRAY + " value process";
      case MUTATION_LUCK:
      case ROBOTIC_CLONING:
        return ChatColor.YELLOW + "(" + String.valueOf(tier + 1) + "x amount stack)"
            + ChatColor.GRAY + " value process (limit 64x)";
      case SIMPLE:
      default:
        return ChatColor.YELLOW + "1x amount stack"
            + ChatColor.GRAY + " value process";
    }
  }

  public static SlimefunItemStack buildItemFromMobTechDTO(MobTechGeneric MobTechGeneric, Integer tier) {
    return new SlimefunItemStack(buildIdTier(MobTechGeneric.getId(), tier),
        MobTechGeneric.getTexture(),
        buildNameTier(MobTechGeneric.getName(), tier),
        "",
        buildLoreRadioactivityType(MobTechGeneric.getMobTechType()),
        buildLoreType(MobTechGeneric.getMobTechType(), tier),
        buildLoreTypeAmount(MobTechGeneric.getMobTechType(), tier),
        "", "&3Supreme Component");
  }

  public final void log(Level level, String messages) {
    this.getLogger().log(level, messages);
  }

}
