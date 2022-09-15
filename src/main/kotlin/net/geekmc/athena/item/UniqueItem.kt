package net.geekmc.athena.item

import net.geekmc.turingcore.color.toComponent
import net.geekmc.turingcore.framework.Logger
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.item.metadata.LeatherArmorMeta
import net.minestom.server.item.metadata.WrittenBookMeta

class UniqueItem(template: TemplateItem) : AbstractItem(template.id) {

    private lateinit var itemStack: ItemStack

    init {
        template.copyTo(this)
    }

    fun toTemplate(): TemplateItem {
        return ItemManager.get(id)
    }

    fun build(): ItemStack {

        if (!cached) {

            Logger.info("构造唯一物品: $id")
            cached = true
            val material = Material.fromNamespaceId(material) ?: run {
                Logger.error("物品 $id 的材质 $material 不存在")
                Material.STICK
            }
            val builder = ItemStack.builder(material)
                .amount(amount)
                .displayName(("<!italic>$name").toComponent())
                .lore(lore.lores.map { ("<!italic>$it").toComponent() })
                .meta {
                    it.damage(damage)
                    it.unbreakable(unbreakable)
                    it.enchantments(enchant.enchantmentMap)
                    it.hideFlag(*(hide.hideFlagsSet.toTypedArray()))
                }

            when (material) {
                Material.WRITTEN_BOOK -> {
                    builder.meta(WrittenBookMeta::class.java) { it ->
                        it.resolved(writtenBook.resolved)
                        it.generation(writtenBook.generation)
                        it.author(writtenBook.author)
                        it.title(writtenBook.title)
                        it.pages(writtenBook.pages.map { it.toComponent() })
                    }
                }

                Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS -> {
                    builder.meta(LeatherArmorMeta::class.java) {
                        it.color(colorBacking)
                    }
                }

                else -> {}
            }

            itemStack = builder.build()

        }
        return itemStack
    }

    companion object {
//        fun from(item: ItemStack): UniqueItem? {
//            val template = TemplateItem.fromItemStack(item) ?: return null
//            return UniqueItem(template)
//        }
    }

}