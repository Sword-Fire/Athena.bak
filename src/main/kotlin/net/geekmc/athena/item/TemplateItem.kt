package net.geekmc.athena.item

import net.geekmc.turingcore.framework.Logger
import net.minestom.server.item.ItemStack

fun item(id: String, lamb: TemplateItem.() -> Unit): TemplateItem {
    val item = TemplateItem(id)
    lamb.invoke(item)
    return item
}

class TemplateItem(id: String) : AbstractItem(id) {

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