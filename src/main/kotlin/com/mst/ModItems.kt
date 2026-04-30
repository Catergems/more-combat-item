package com.mst

import com.mst.item.StarSwordItem
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.ToolMaterial
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

object ModItems {

    /**
     * Star Sword — uses Item.Settings.sword() (confirmed in 1.21.11 mappings on Item$Properties).
     *
     * .sword(material, attackDamage, attackSpeed):
     *   attackDamage = bonus on top of material's own bonus.
     *   NETHERITE has attackDamageBonus = 4, so 4 + (-1) + 1(base) = 4... adjust as needed.
     *
     * To hit exactly 3 base damage:
     *   Use ToolMaterial.WOOD (attackDamageBonus = 0): 0 + 2 + 1(base) = 3 ✓
     *   But durability comes from the material; we call .maxDamage() to override.
     *
     * The XP bonus (7 extra magic dmg) bumps it to 10 when >= 15 levels (see StarSwordItem).
     */
    val STAR_SWORD: Item = register("star_sword") { key ->
        StarSwordItem(
            Item.Settings()
                .sword(ToolMaterial.WOOD, 2f, -2.4f)  // 0 (wood bonus) + 2 + 1(base) = 3 attack
                .maxDamage(500)
                .registryKey(key)
        )
    }

    private fun register(name: String, factory: (RegistryKey<Item>) -> Item): Item {
        val key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MoreSwordType.MOD_ID, name))
        return Registry.register(Registries.ITEM, key, factory(key))
    }

    fun initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register { group ->
            group.add(STAR_SWORD)
        }
    }
}
