package net.geekmc.athena.entity

import net.geekmc.athena.item.AbstractItem
import net.geekmc.athena.item.ItemManager
import net.geekmc.athena.item.UniqueItem
import net.geekmc.turingcore.framework.Logger
import net.minestom.server.item.ItemStack

fun entity(id: String, lamb: TemplateEntity.() -> Unit): TemplateEntity {
    val item = TemplateEntity(id)
    lamb.invoke(item)
    return item
}

class TemplateEntity(id: String) : AbstractItem(id) {

    lateinit var uniqueItem: UniqueItem

    fun build(): ItemStack {
        if (!cached) {
            Logger.info("构造模板物品 $id")
            uniqueItem = UniqueItem(this)
            cached = true
        }
        return uniqueItem.build()
    }

    fun toUnique(): UniqueItem {
        return UniqueItem(this)
    }

    init {

        ItemManager.set(id,this)

    }

}