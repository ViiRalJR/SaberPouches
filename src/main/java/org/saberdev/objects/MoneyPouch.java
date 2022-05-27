package org.saberdev.objects;

import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.saberdev.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MoneyPouch {

    private String name;
    private Material material;
    private List<String> lore;
    private double max;
    private double min;

    public MoneyPouch(String name, Material material, List<String> lore, int max, int min) {
        this.name = name;
        this.material = material;
        this.lore = lore;
        this.max = max;
        this.min = min;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(this.material);
        List<String> formattedLore = new ArrayList<>();
        for (String s : this.lore) {
            formattedLore.add(StringUtils.color(s).replace("{MINIMUM}", Double.toString(this.min)).replace("{MAXIMUM}", Double.toString(this.max)));
        }
        String formattedName = StringUtils.color(this.name);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(formattedName);
        meta.setLore(formattedLore);
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setDouble("SaberMinAmount", this.min);
        nbt.setDouble("SaberMaxAmount", this.max);
        nbt.setString("SaberPouchType", "money");
        return nbt.getItem();
    }

}
