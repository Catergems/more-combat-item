package com.mst.item

import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld

class StarSwordItem(settings: Settings) : Item(settings) {

    companion object {
        const val XP_THRESHOLD = 15
        const val BASE_DAMAGE = 3f
        const val MAX_DAMAGE = 10f
        const val BONUS_DAMAGE = (MAX_DAMAGE - BASE_DAMAGE) * 1.5f  // 7 extra magic dmg when >= 15 levels
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        if (attacker is PlayerEntity) {
            val world = attacker.entityWorld
            if (world is ServerWorld) {
                val levelBefore = attacker.experienceLevel

                if (levelBefore > 0) {
                    attacker.addExperienceLevels(-1)
                }

                if (levelBefore >= XP_THRESHOLD) {
                    target.damage(world, attacker.damageSources.magic(), BONUS_DAMAGE)
                }
            }
        }

        stack.damage(1, attacker, EquipmentSlot.MAINHAND)
    }
}