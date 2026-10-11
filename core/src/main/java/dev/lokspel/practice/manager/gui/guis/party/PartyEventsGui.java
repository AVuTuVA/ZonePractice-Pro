package dev.lokspel.practice.manager.gui.guis.party;

import dev.lokspel.practice.config.PracticeConfig;
import dev.lokspel.practice.manager.backend.BackendUtil;
import dev.lokspel.practice.manager.fight.match.enums.MatchType;
import dev.lokspel.practice.manager.gui.Gui;
import dev.lokspel.practice.manager.gui.GuiType;
import dev.lokspel.practice.manager.gui.guis.selectors.LadderSelectorGui;
import dev.lokspel.practice.manager.party.Party;
import dev.lokspel.practice.manager.party.PartyManager;
import dev.lokspel.practice.manager.profile.Profile;
import dev.lokspel.practice.manager.profile.ProfileManager;
import dev.lokspel.practice.util.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PartyEventsGui extends Gui {

  private static final ItemStack FILLER_ITEM =
      BackendUtil.getGuiItem(
              PracticeConfig.guis().getGuis().getParty().getPartyGames().getIcons().getFillerItem())
          .get();

  public PartyEventsGui() {
    super(GuiType.Party_Events);
    this.gui.put(
        1,
        InventoryUtil.createInventory(
            PracticeConfig.guis().getGuis().getParty().getPartyGames().getTitle(), 3));

    build();
  }

  @Override
  public void build() {
    update();
  }

  @Override
  public void update() {
    Inventory inventory = gui.get(1);

    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, FILLER_ITEM);
    }

    inventory.setItem(
        11,
        BackendUtil.getGuiItem(
                PracticeConfig.guis()
                    .getGuis()
                    .getParty()
                    .getPartyGames()
                    .getIcons()
                    .getPartySplit())
            .get());
    inventory.setItem(
        15,
        BackendUtil.getGuiItem(
                PracticeConfig.guis().getGuis().getParty().getPartyGames().getIcons().getPartyFfa())
            .get());

    updatePlayers();
  }

  @Override
  public void handleClickEvent(InventoryClickEvent e) {
    Player player = (Player) e.getWhoClicked();
    Profile profile = ProfileManager.getInstance().getProfile(player);
    Party party = PartyManager.getInstance().getParty(player);

    if (party == null) {
      return;
    }

    int slot = e.getRawSlot();
    e.setCancelled(true);

    switch (slot) {
      case 11:
        new LadderSelectorGui(profile, MatchType.PARTY_SPLIT).open(player);
        break;
      case 15:
        new LadderSelectorGui(profile, MatchType.PARTY_FFA).open(player);
        break;

      default:
        break;
    }
  }
}
