package net.geekmc.athena.command

import net.geekmc.athena.item.ItemManager
import net.geekmc.turingcore.color.send
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.entity.Player
import world.cepi.kstom.command.kommand.Kommand

object GiveCommand : Kommand({
    val idArg = ArgumentWord("id")

    syntax(idArg) {
        if (sender !is Player) {
            sender.send("&r只有玩家能使用这个命令!")
            return@syntax
        }
        val id = !idArg
        if (!ItemManager.contains(id)) {
            sender.send("&r物品 $id 不存在.")
            return@syntax
        }

        player.inventory.addItemStack(ItemManager.get(id).build())

    }

}, "give")