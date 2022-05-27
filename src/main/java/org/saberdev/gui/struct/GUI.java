package org.saberdev.gui.struct;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.saberdev.Pouches;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class GUI {
    // -------------------------------------------- //
    // LOCAL FIELDS
    // -------------------------------------------- //

    public Pouches main;

    private String title;
    private int size;

    private boolean paged;
    private Integer page;
    private Integer lastPage;
    private Integer maxItemsPerPage;

    private Inventory inventory;
    private InventoryClick inventoryClick;
    private InventoryClose inventoryClose;

    private Map<Integer, ItemStack> items;

    private final Map<Integer, InventoryClick> clicks;

    public InventoryClick getClick(int slot) {
        if (this.isPaged()) {
            if (slot >= maxItemsPerPage) {
                if (slot == maxItemsPerPage + 2 && this.page != 1) {
                    return previousPage();
                } else if (slot == maxItemsPerPage + 6 && !this.page.equals(this.lastPage)) {
                    return nextPage();
                } else {
                    return null;
                }
            } else if (this.page == 1) {
                return this.clicks.get(slot);
            } else {
                return this.clicks.get(slot + this.maxItemsPerPage * (this.page - 1));
            }
        } else return this.clicks.get(slot);
    }

    // -------------------------------------------- //
    // CONSTRUCT
    // -------------------------------------------- //

    public GUI(Pouches main) {
        this.main = main;
        this.title = " ";
        this.size = 54;
        this.paged = false;
        this.page = null;
        this.inventory = null;
        this.inventoryClick = this.mainInventoryClick();
        this.inventoryClose = this.inventoryClose();
        this.items = new HashMap<>();
        this.clicks = new HashMap<>();
    }

    // -------------------------------------------- //
    // INVENTORY ACTIONS
    // -------------------------------------------- //

    public void open(Player player, Object... objects) {
        this.buildInventory(player, objects);

        this.inventory = Bukkit.createInventory(null, this.size, this.title);

        if (this.isPaged()) {
            if (this.page == null) this.page = 1;
            this.maxItemsPerPage = this.size - 9;

            int count = 0;

            for (int i = (this.page - 1) * this.maxItemsPerPage; i < this.page * this.maxItemsPerPage; i++) {
                this.inventory.setItem(count++, this.items.get(i));
            }

            this.lastPage = Math.max(1, (int) Math.ceil(((double) this.items.size() / this.maxItemsPerPage)));

            int bottomRow = this.maxItemsPerPage;

            this.inventory.setItem(bottomRow++, main.getGuiManager().getFillerItem());
            this.inventory.setItem(bottomRow++, main.getGuiManager().getFillerItem());
            this.inventory.setItem(bottomRow++, main.getGuiManager().getFillerItem());
            this.inventory.setItem(bottomRow++, main.getGuiManager().getFillerItem());
            this.inventory.setItem(bottomRow++, main.getGuiManager().getFillerItem());
            this.inventory.setItem(bottomRow++, main.getGuiManager().getFillerItem());
            this.inventory.setItem(bottomRow++, this.lastPage == 1 ? main.getGuiManager().getFillerItem() : main.getGuiManager().getNextPageItem());
            this.inventory.setItem(bottomRow++, main.getGuiManager().getFillerItem());
            this.inventory.setItem(bottomRow, main.getGuiManager().getFillerItem());
        } else {
            this.items.forEach(this.inventory::setItem);
        }

        player.openInventory(this.inventory);

        main.getGuiManager().addPlayerGui(player.getUniqueId(), this);
    }

    public InventoryClick nextPage() {
        return event ->
        {
            int previousPage = this.page++;

            int count = 0;

            for (int i = previousPage * this.maxItemsPerPage; i < this.page * this.maxItemsPerPage; i++) {
                this.inventory.setItem(count++, this.items.get(i));
            }

            if (this.page.equals(this.lastPage))
                this.inventory.setItem(this.maxItemsPerPage + 6, main.getGuiManager().getFillerItem());

            this.inventory.setItem(this.maxItemsPerPage + 2, main.getGuiManager().getPreviousPageItem());
        };
    }

    public InventoryClick previousPage() {
        return event ->
        {
            int previousPage = this.page--;

            int count = 0;

            for (int i = (this.page - 1) * this.maxItemsPerPage; i < (previousPage - 1) * this.maxItemsPerPage; i++) {
                this.inventory.setItem(count++, this.items.get(i));
            }

            if (this.page == 1) this.inventory.setItem(this.maxItemsPerPage + 2, main.getGuiManager().getFillerItem());

            this.inventory.setItem(this.maxItemsPerPage + 6, main.getGuiManager().getNextPageItem());
        };
    }

    // -------------------------------------------- //
    // INVENTORY MANIPULATIONS
    // -------------------------------------------- //

    public void setItem(int slot, ItemStack item) {
        this.items.put(slot, item);
    }

    public void setItem(int slot, ItemStack item, InventoryClick inventoryClick) {
        setItem(slot, item);
        this.clicks.put(slot, inventoryClick);
    }

    public void removeItem(int slot) {
        this.items.remove(slot);

        if (this.page == null || this.page == 1) {
            this.inventory.setItem(slot, new ItemStack(Material.AIR));
        } else {
            this.inventory.setItem(slot - this.maxItemsPerPage * (this.page - 1), new ItemStack(Material.AIR));
        }
    }

    public void setInventoryClick(int slot, InventoryClick inventoryClick) {
        this.clicks.put(slot, inventoryClick);
    }

    public void removeInventoryClick(int slot) {
        this.clicks.remove(slot);
    }

    public void addItem(ItemStack item) {
        this.setItem(findNextEmptySlot(), item);
    }

    public void addItem(ItemStack item, InventoryClick inventoryClick) {
        this.setItem(findNextEmptySlot(), item, inventoryClick);
    }

    private int findNextEmptySlot() {
        int i = 0;

        while (this.items.containsKey(i)) i++;

        return i;
    }

    public void fillInventory() {
        int emptySlots = 0;
        for (int i = 0; i < this.size; i++) {
            if (!this.items.containsKey(i)) emptySlots++;
        }
        for (int y = 0; y < emptySlots; y++) this.addItem(main.getGuiManager().getFillerItem());
    }

    protected abstract void buildInventory(Player player, Object... objects);

    protected abstract InventoryClick mainInventoryClick();

    protected abstract InventoryClose inventoryClose();

}
