package net.geekmc.athena.command

import net.geekmc.athena.item.ItemManager
import net.minestom.server.command.builder.arguments.ArgumentLiteral
import net.minestom.server.item.ItemStack
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.kstom.serializer.ItemStackSerializer
import world.cepi.kstom.serializer.MinestomJSON

object MTestCommand : Kommand({

    val arg1 = ArgumentLiteral("1")
    val arg2 = ArgumentLiteral("2")
    val arg3 = ArgumentLiteral("3")
    val arg4 = ArgumentLiteral("4")
    val arg5 = ArgumentLiteral("5")

    syntax(arg1) {
        val item = ItemManager.get("测试").build()

        val format=MinestomJSON
        val str = format.encodeToString(ItemStackSerializer,item)
        println(str)

        val item2 = format.decodeFromString<ItemStack>(ItemStackSerializer,str)

        player.inventory.addItemStack(item2)

    }

}, "mt")