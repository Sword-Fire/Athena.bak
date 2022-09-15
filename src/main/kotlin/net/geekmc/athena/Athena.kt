package net.geekmc.athena

import net.geekmc.athena.command.GiveCommand
import net.geekmc.athena.command.MTestCommand
import net.geekmc.athena.item.item
import net.geekmc.turingcore.color.toComponent
import net.geekmc.turingcore.framework.TuringFramework
import net.minestom.server.extensions.Extension

class Athena : Extension() {

    override fun initialize() {
        registerFramework()
        registerCommands()
        registerItem()
        logger.info("Minotaur initialized.")
    }

    override fun terminate() {
        logger.info("Minotaur terminated.")
    }

    private fun registerItem() {

        item("测试") {
            material = "leather_helmet"
            name = "&g测试物品"

            lore {
                -"&y这是一个测试物品"
                -"&y啦啦啦"
            }

//            enchant {
//                quickCharge = 3
//            }

//            hide {
//                -enchants
//            }

            color(1,2,3)

        }

    }

    private fun registerFramework() {
        val registry = TuringFramework.registerExtension("net.geekmc.minotaur", this)
        registry.consolePrefix = "[Minotaur] "
        registry.playerPrefix = "&f[&gMinotaur&f] ".toComponent()
    }

    private fun registerCommands() {
        GiveCommand.register()
        MTestCommand.register()
    }

}