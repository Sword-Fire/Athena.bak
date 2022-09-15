package net.geekmc.athena.command

import net.geekmc.athena.item.ItemManager
import net.geekmc.turingcore.color.send
import net.minestom.server.command.builder.arguments.ArgumentWord
import net.minestom.server.entity.Player
import world.cepi.kstom.command.kommand.Kommand

object SpawnCommand : Kommand({
    val idArg = ArgumentWord("id")

    syntax(idArg) {


    }

}, "spawn")