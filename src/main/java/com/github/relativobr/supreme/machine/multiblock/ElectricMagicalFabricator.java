package com.github.relativobr.supreme.machine.multiblock;

import com.github.relativobr.supreme.generic.machine.GenericMachine;
import com.github.relativobr.supreme.generic.recipe.AbstractItemRecipe;
import com.github.relativobr.supreme.resource.SupremeComponents;
import com.github.relativobr.supreme.resource.core.SupremeCoreAlloy;
import com.github.relativobr.supreme.resource.magical.SupremeAttribute;
import com.github.relativobr.supreme.resource.magical.SupremeCetrus;
import com.github.relativobr.supreme.resource.magical.SupremeCore;
import com.github.relativobr.supreme.util.SupremeItemStack;
import com.github.relativobr.supreme.util.UtilEnergy;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ElectricMagicalFabricator extends GenericMachine {

  public static final SlimefunItemStack ELECTRIC_MAGICAL_MACHINE = new SupremeItemStack("SUPREME_ELECTRIC_MAGICAL_I",
      Material.CRYING_OBSIDIAN, "&bElectric Magical Machine", "", "&fCraft resource of magical", "",
      LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE), LoreBuilder.speed(1),
      UtilEnergy.energyPowerPerSecond(20), "", "&3Supreme Machine");

  public static final ItemStack[] RECIPE_ELECTRIC_MAGICAL_MACHINE = new ItemStack[]{
      SupremeCoreAlloy.RESOURCE_CORE_NETHERITE.item(), new ItemStack(Material.CRYING_OBSIDIAN),
      SupremeCoreAlloy.RESOURCE_CORE_NETHERITE.item(), SupremeComponents.INDUCTOR_MACHINE.item(), SupremeComponents.SYNTHETIC_RUBY.item(),
      SupremeComponents.INDUCTOR_MACHINE.item(), SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.CARBONADO_EDGED_CAPACITOR.item(),
      SlimefunItems.ELECTRIC_MOTOR.item()};

  public static final SlimefunItemStack ELECTRIC_MAGICAL_MACHINE_II = new SupremeItemStack(
      "SUPREME_ELECTRIC_MAGICAL_II", Material.CRYING_OBSIDIAN, "&bElectric Magical Machine II", "",
      "&fAdvanced craft resource of magical", "", LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
      LoreBuilder.speed(5), UtilEnergy.energyPowerPerSecond(100), "", "&3Supreme Machine");

  public static final ItemStack[] RECIPE_ELECTRIC_MAGICAL_MACHINE_II = new ItemStack[]{
      SupremeComponents.CONVEYANCE_MACHINE.item(), SupremeCetrus.CETRUS_LUX.item(), SupremeComponents.CONVEYANCE_MACHINE.item(),
      SupremeComponents.INDUCTOR_MACHINE.item(), ElectricMagicalFabricator.ELECTRIC_MAGICAL_MACHINE.item(),
      SupremeComponents.INDUCTOR_MACHINE.item(), SupremeComponents.BLEND_MACHINE.item(), SupremeCetrus.CETRUS_IGNIS.item(),
      SupremeComponents.BLEND_MACHINE.item()};

  public static final SlimefunItemStack ELECTRIC_MAGICAL_MACHINE_III = new SupremeItemStack(
      "SUPREME_ELECTRIC_MAGICAL_III", Material.CRYING_OBSIDIAN, "&bElectric Magical Machine III", "",
      "&fAdvanced craft resource of magical", "", LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
      LoreBuilder.speed(15), UtilEnergy.energyPowerPerSecond(300), "", "&3Supreme Machine");

  public static final ItemStack[] RECIPE_ELECTRIC_MAGICAL_MACHINE_III = new ItemStack[]{SupremeComponents.THORNERITE.item(),
      SupremeCetrus.CETRUS_LUX.item(), SupremeComponents.THORNERITE.item(), SupremeComponents.SUPREME.item(),
      ElectricMagicalFabricator.ELECTRIC_MAGICAL_MACHINE_II.item(), SupremeComponents.SUPREME.item(),
      SupremeComponents.CRYSTALLIZER_MACHINE.item(), SupremeCetrus.CETRUS_LUMIUM.item(), SupremeComponents.CRYSTALLIZER_MACHINE.item()};

  @ParametersAreNonnullByDefault
  public ElectricMagicalFabricator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType,
      ItemStack[] recipe) {
    super(category, item, recipeType, recipe);
  }

  public static List<AbstractItemRecipe> getAllRecipe() {
    List<AbstractItemRecipe> list = new ArrayList<>();
    list.add(new AbstractItemRecipe(SupremeComponents.RECIPE_BLEND_MACHINE, SupremeComponents.BLEND_MACHINE.item()));
    list.add(new AbstractItemRecipe(SupremeCore.RECIPE_CORE_OF_BLOCK, SupremeCore.CORE_OF_BLOCK.item()));
    list.add(new AbstractItemRecipe(SupremeCore.RECIPE_CORE_OF_DEATH, SupremeCore.CORE_OF_DEATH.item()));
    list.add(new AbstractItemRecipe(SupremeCore.RECIPE_CORE_OF_LIFE, SupremeCore.CORE_OF_LIFE.item()));
    list.add(new AbstractItemRecipe(SupremeCore.RECIPE_CORE_OF_COLOR, SupremeCore.CORE_OF_COLOR.item()));
    list.add(new AbstractItemRecipe(SupremeCore.RECIPE_CORE_OF_NATURE, SupremeCore.CORE_OF_NATURE.item()));
    list.add(new AbstractItemRecipe(SupremeCore.RECIPE_CORE_OF_ALLOY, SupremeCore.CORE_OF_ALLOY.item()));
    list.add(new AbstractItemRecipe(SupremeCetrus.RECIPE_CETRUS_IGNIS, SupremeCetrus.CETRUS_IGNIS.item()));
    list.add(new AbstractItemRecipe(SupremeCetrus.RECIPE_CETRUS_LUX, SupremeCetrus.CETRUS_LUX.item()));
    list.add(new AbstractItemRecipe(SupremeCetrus.RECIPE_CETRUS_VENTUS, SupremeCetrus.CETRUS_VENTUS.item()));
    list.add(new AbstractItemRecipe(SupremeCetrus.RECIPE_CETRUS_AQUA, SupremeCetrus.CETRUS_AQUA.item()));
    list.add(new AbstractItemRecipe(SupremeCetrus.RECIPE_CETRUS_LUMIUM, SupremeCetrus.CETRUS_LUMIUM.item()));
    list.add(new AbstractItemRecipe(SupremeComponents.RECIPE_SUPREME, SupremeComponents.SUPREME.item()));
    list.add(new AbstractItemRecipe(SupremeAttribute.RECIPE_ATTRIBUTE_MAGIC, SupremeAttribute.getMagic().item()));
    list.add(new AbstractItemRecipe(SupremeAttribute.RECIPE_ATTRIBUTE_BOMB, SupremeAttribute.getBomb().item()));
    list.add(new AbstractItemRecipe(SupremeAttribute.RECIPE_ATTRIBUTE_FORTUNE, SupremeAttribute.getFortune().item()));
    list.add(new AbstractItemRecipe(SupremeAttribute.RECIPE_ATTRIBUTE_IMPETUS, SupremeAttribute.getImpetus().item()));
    return list;
  }

}
