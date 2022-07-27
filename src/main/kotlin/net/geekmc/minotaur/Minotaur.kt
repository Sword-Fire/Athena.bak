package net.geekmc.minotaur

import net.minestom.server.extensions.Extension


class Minotaur : Extension() {


    override fun initialize() {

        logger.info("Minotaur initialized.")
    }

    override fun terminate() {
        logger.info("Minotaur terminated.")
    }

}