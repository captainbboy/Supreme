package com.github.relativobr.supreme.machine;

import com.github.relativobr.supreme.generic.machine.SimpleItemWithLargeContainerMachine;
import com.github.relativobr.supreme.machine.recipe.VirtualGardenMachineRecipe;
import com.github.relativobr.supreme.resource.SupremeComponents;
import com.github.relativobr.supreme.resource.magical.SupremeAttribute;
import com.github.relativobr.supreme.resource.magical.SupremeCetrus;
import com.github.relativobr.supreme.util.SupremeItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.springframework.scheduling.annotation.Async;

@Async
public class VirtualGarden extends SimpleItemWithLargeContainerMachine {

  public static final SlimefunItemStack VIRTUAL_GARDEN_MACHINE = new SupremeItemStack("SUPREME_VIRTUAL_GARDEN_I",
      Material.STRIPPED_WARPED_HYPHAE, "&bVirtual Garden", "", "&fThis machine allows you to",
      "&fcultivate some resources.", "", LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
      LoreBuilder.speed(1), LoreBuilder.powerBuffer(1000), LoreBuilder.powerPerSecond(20), "", "&3Supreme Machine");
  public static final ItemStack[] RECIPE_VIRTUAL_GARDEN_MACHINE = new ItemStack[]{SupremeComponents.SYNTHETIC_RUBY.item(),
      new ItemStack(Material.STRIPPED_WARPED_HYPHAE), SupremeComponents.SYNTHETIC_RUBY.item(),
      SupremeComponents.RUSTLESS_MACHINE.item(), SupremeComponents.PETRIFIER_MACHINE.item(), SupremeComponents.RUSTLESS_MACHINE.item(),
      SupremeComponents.ADAMANTIUM_PLATE.item(), SlimefunItems.PROGRAMMABLE_ANDROID_2_FARMER.item(),
      SupremeComponents.ADAMANTIUM_PLATE.item()};

  public static final SlimefunItemStack VIRTUAL_GARDEN_MACHINE_II = new SupremeItemStack("SUPREME_VIRTUAL_GARDEN_II",
      Material.STRIPPED_WARPED_HYPHAE, "&bVirtual Garden II", "", "&fThis machine allows you to",
      "&fcultivate some resources.", "", LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
      LoreBuilder.speed(5), LoreBuilder.powerBuffer(5000), LoreBuilder.powerPerSecond(100), "", "&3Supreme Machine");
  public static final ItemStack[] RECIPE_VIRTUAL_GARDEN_MACHINE_II = new ItemStack[]{
      SupremeComponents.CONVEYANCE_MACHINE.item(), SupremeCetrus.CETRUS_LUMIUM.item(), SupremeComponents.CONVEYANCE_MACHINE.item(),
      SupremeComponents.INDUCTOR_MACHINE.item(), VirtualGarden.VIRTUAL_GARDEN_MACHINE.item(), SupremeComponents.INDUCTOR_MACHINE.item(),
      SupremeComponents.THORNERITE.item(), SupremeCetrus.CETRUS_AQUA.item(), SupremeComponents.THORNERITE.item()};

  public static final SlimefunItemStack VIRTUAL_GARDEN_MACHINE_III = new SupremeItemStack("SUPREME_VIRTUAL_GARDEN_III",
      Material.STRIPPED_WARPED_HYPHAE, "&bVirtual Garden III", "", "&fThis machine allows you to",
      "&fcultivate some resources.", "", LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
      LoreBuilder.speed(15), LoreBuilder.powerBuffer(15000), LoreBuilder.powerPerSecond(300), "", "&3Supreme Machine");
  public static final ItemStack[] RECIPE_VIRTUAL_GARDEN_MACHINE_III = new ItemStack[]{SupremeComponents.THORNERITE.item(),
      SupremeAttribute.getFortune().item(), SupremeComponents.THORNERITE.item(), SupremeComponents.SUPREME.item(),
      VirtualGarden.VIRTUAL_GARDEN_MACHINE_II.item(), SupremeComponents.SUPREME.item(), SupremeComponents.CRYSTALLIZER_MACHINE.item(),
      SupremeCetrus.CETRUS_LUMIUM.item(), SupremeComponents.CRYSTALLIZER_MACHINE.item()};

  public static Map<Block, MachineRecipe> processing = new HashMap<>();
  public static Map<Block, Integer> progress = new HashMap<>();
  private final Set<VirtualGardenMachineRecipe> virtualGardenMachineRecipes = new HashSet();

  @ParametersAreNonnullByDefault
  public VirtualGarden(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
    super(category, item, recipeType, recipe);
  }


  @Override
  protected void registerDefaultRecipes() {
    this.recipes.clear();
    VirtualGardenMachineRecipe.getAllRecipe()
        .stream().filter(Objects::nonNull)
        .forEach(recipe -> {
      this.addProduce(new VirtualGardenMachineRecipe(recipe));
    });
  }


  public void addProduce(@Nonnull VirtualGardenMachineRecipe produce) {
    Validate.notNull(produce, "A produce cannot be null");
    this.virtualGardenMachineRecipes.add(produce);
  }


  @Nonnull
  @Override
  public List<ItemStack> getDisplayRecipes() {
    List<ItemStack> displayRecipes = new ArrayList();
    VirtualGardenMachineRecipe.getAllRecipe()
        .stream().filter(Objects::nonNull)
        .forEach(recipe -> {
      displayRecipes.add(CustomItemStack.create(recipe.getFirstMaterialInput(), null, "&fRequires &bto cultivate"));
      displayRecipes.add(new ItemStack(recipe.getFirstMaterialOutput()));
    });
    return displayRecipes;
  }


  @Override
  public void preRegister() {
    this.addItemHandler(new BlockTicker() {
      public void tick(Block b, SlimefunItem sf, Config data) {
        VirtualGarden.this.tick(b);
      }

      public boolean isSynchronized() {
        return true;
      }
    });
  }


  @Nonnull
  @Override
  public String getRecipeSectionLabel(@Nonnull Player p) {
    return "&7Cultivate:";
  }

  @Override
  protected MachineRecipe findNextRecipe(@Nonnull BlockMenu inv) {
    int[] inputSlots = this.getInputSlots();
    for (int slot : inputSlots) {
      for (VirtualGardenMachineRecipe produce : this.virtualGardenMachineRecipes) {
        ItemStack itemInSlot = inv.getItemInSlot(slot);
        final ItemStack itemInInput = produce.getInput()[0];
        if (itemInSlot != null && itemInInput != null && itemInSlot.getType() == itemInInput.getType() && InvUtils.fits(
            inv.toInventory(), produce.getOutput()[0], this.getOutputSlots())) {
          return produce;
        }
      }
    }
    return null;
  }

  @Override
  protected void tick(Block b) {
    BlockMenu inv = BlockStorage.getInventory(b);
    if (inv == null) {
      return;
    }

    if (isProcessing(b)) {

      var recipeOutput = processing.get(b).getOutput();
      if (notHasSpaceOutput(inv, recipeOutput)) {
        updateStatusOutputFull(inv);
        return;
      }

      if (getCharge(b.getLocation()) < getEnergyConsumption()) {
        updateStatusConnectEnergy(inv, recipeOutput[0]);
        return;
      }

      if (takeCharge(b.getLocation())) {
        int timeleft = progress.get(b);
        if (timeleft > 0) {
          ChestMenuUtils.updateProgressbar(inv, getStatusSlot(), timeleft, processing.get(b).getTicks(), getProgressBar());
          int time = timeleft - getSpeed();
          if (time < 0) {
            time = 0;
          }
          progress.put(b, time);
        } else {
          for (ItemStack output : recipeOutput) {
            if(output != null){
              ItemStack clone = output.clone();
              clone.setAmount(1);
              inv.pushItem(clone, getOutputSlots());
            }
          }
          progress.remove(b);
          processing.remove(b);
          updateStatusReset(inv);
        }
      }
    } else {
      MachineRecipe next = findNextRecipe(inv);
      if (next != null) {
        processing.put(b, next);
        progress.put(b, next.getTicks());
      } else {
        updateStatusReset(inv);
      }
    }
  }

  @Nonnull
  @Override
  public String getMachineIdentifier() {
    return "VIRTUAL_GARDEN";
  }

  @Override
  public ItemStack getProgressBar() {
    return new ItemStack(Material.IRON_HOE);
  }

  public MachineRecipe getProcessing(Block b) {
    return processing.get(b);
  }

  public boolean isProcessing(Block b) {
    return getProcessing(b) != null;
  }

}
