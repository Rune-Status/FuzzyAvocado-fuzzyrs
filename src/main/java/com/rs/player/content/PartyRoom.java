package com.rs.player.content;

import com.rs.content.dialogues.types.SimpleMessage;
import com.rs.core.utils.file.EconomyPrices;
import com.rs.core.utils.item.ItemSetsKeyGenerator;
import com.rs.player.Player;
import com.rs.world.item.Item;
import com.rs.world.item.ItemsContainer;

public class PartyRoom {

    private static final int CHEST_INTERFACE_ITEMS_KEY = ItemSetsKeyGenerator
            .generateKey();
    public static int PARTY_CHEST_INTERFACE = 647;
    public static int INVENTORY_INTERFACE = 336;
    private static ItemsContainer<Item> items = new ItemsContainer<Item>(100,
            false);

    public static void openPartyChest(final Player player) {
        player.getTemporaryAttributtes().put("PartyRoomInventory", Boolean.TRUE);
        player.getInterfaceManager().sendInterface(PARTY_CHEST_INTERFACE);
        player.getInterfaceManager().sendInventoryInterface(INVENTORY_INTERFACE);
        sendOptions(player);
        player.setCloseInterfacesEvent(() -> player.getTemporaryAttributtes().remove("PartyRoomInventory"));
    }

    private static void sendOptions(final Player player) {
        player.getPackets().sendInterSetItemsOptionsScript(INVENTORY_INTERFACE,
                0, 93, 4, 7, "Deposit", "Deposit-5", "Deposit-10",
                "Deposit-All", "Deposit-X");
        player.getPackets().sendIComponentSettings(INVENTORY_INTERFACE, 0, 0, 27, 1278);
        player.getPackets().sendInterSetItemsOptionsScript(INVENTORY_INTERFACE,
                30, CHEST_INTERFACE_ITEMS_KEY, 4, 7, "Value");
        player.getPackets().sendIComponentSettings(PARTY_CHEST_INTERFACE, 30,
                0, 27, 1150);
        player.getPackets().sendInterSetItemsOptionsScript(
                PARTY_CHEST_INTERFACE, 33, CHEST_INTERFACE_ITEMS_KEY, true, 4,
                7, "Examine");
        player.getPackets().sendIComponentSettings(PARTY_CHEST_INTERFACE, 33,
                0, 27, 1026);

    }

    public static int getTotalCoins() {
        int price = 0;
        for (final Item item : items.getItems()) {
            if (item == null) {
                continue;
            }
            price += EconomyPrices.getPrice(item.getId());
        }
        return price;
    }

    public static void purchase(final Player player, final boolean balloons) {
        if (balloons) {
            if (player.getInventory().containsItem(995, 1000)) {
                // startParty(player);
            } else {
                player.getDialogueManager().startDialogue(SimpleMessage.class,
                        "Balloon Bonanza costs 1000 coins.");
            }
        } else {
            if (player.getInventory().containsItem(995, 500)) {
                startDancingKnights();
            } else {
                player.getDialogueManager().startDialogue(SimpleMessage.class,
                        "Nightly Dance costs 500 coins.");
            }
        }
    }

    public static void startDancingKnights() {
        // TODO
    }
}