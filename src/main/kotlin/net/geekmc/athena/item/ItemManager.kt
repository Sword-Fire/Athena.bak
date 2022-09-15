package net.geekmc.athena.item

object ItemManager {

    private val itemMap = LinkedHashMap<String, TemplateItem>()

    fun get(id: String): TemplateItem {
        return itemMap[id] ?: throw IllegalArgumentException("物品 $id 不存在!")
    }

    fun set(id: String, item: TemplateItem) {
        itemMap[id] = item
    }

    fun contains(id: String): Boolean {
        return itemMap.containsKey(id)
    }

    fun clear() {
        itemMap.clear()
    }

}