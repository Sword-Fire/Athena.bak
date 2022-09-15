package net.geekmc.athena.item

import net.minestom.server.color.Color
import net.minestom.server.item.Enchantment
import net.minestom.server.item.ItemHideFlag
import net.minestom.server.item.metadata.WrittenBookMeta.WrittenBookGeneration
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * 抽象的物品类。
 */
abstract class AbstractItem(val id: String) {

    var cached = false

    var material: String by Delegates.observable("paper") { _, _, _ -> cached = false }
    var amount: Int by Delegates.observable(1) { _, _, _ -> cached = false }
    var name: String by Delegates.observable("no-name") { _, _, _ -> cached = false }
    var damage = 0
    var unbreakable = false

    val lore = LoreContext()
    var hide = HideFlagsContext()
    var enchant = EnchantmentsContext()
    val writtenBook = WrittenBookMetaContext()
    var color = ColorWrapper()
    var colorBacking = Color(255, 255, 255)

    //TODO 需要拆开多部分。需要可渲染。
    // 例如，普通物品可能有描述；传说物品可能有描述和诗文。
    // 自动换行：把所有东西变成一行，然后按照长度分割。
    inner class LoreContext {

        val lores = ArrayList<String>()

        operator fun plusAssign(value: String) {
            cached = false
            lores.add(value)
        }

        operator fun String.unaryMinus() {
            cached = false
            lores.add(this)
        }

        operator fun get(index: Int): String {
            if (index >= lores.size) {
                return "<$index out of index of lore>"
            }
            return lores[index]
        }

        operator fun set(index: Int, value: String) {
            cached = false
            while (index >= lores.size) {
                lores.add("")
            }
            lores[index] = value
        }

        operator fun invoke(lamb: LoreContext.() -> Unit) {
            lamb.invoke(lore)
        }

        fun copyTo(to: LoreContext) {
            to.lores.clear()
            to.lores.addAll(lores)
        }

    }

    inner class HideFlagsContext {

        val hideFlagsSet = HashSet<ItemHideFlag>()

        val enchants = ItemHideFlag.HIDE_ENCHANTS
        val attributes = ItemHideFlag.HIDE_ATTRIBUTES
        val unbreakable = ItemHideFlag.HIDE_UNBREAKABLE
        val canDestroy = ItemHideFlag.HIDE_DESTROYS
        val canPlaceOn = ItemHideFlag.HIDE_PLACED_ON
        val potion = ItemHideFlag.HIDE_POTION_EFFECTS
        val dye = ItemHideFlag.HIDE_DYE

        operator fun ItemHideFlag.unaryMinus() {
            cached = false
            hideFlagsSet.add(this)
        }

        operator fun ItemHideFlag.unaryPlus() {
            cached = false
            hideFlagsSet.remove(this)
        }

        operator fun invoke(lamb: HideFlagsContext.() -> Unit) {
            lamb.invoke(hide)
        }

        fun copyTo(to: HideFlagsContext) {
            to.hideFlagsSet.clear()
            to.hideFlagsSet.addAll(hideFlagsSet)
        }

    }

    inner class EnchantmentsContext {

        // 只允许仍然可能对客户端有效果的附魔
        val enchantmentMap = HashMap<Enchantment, Short>()
        var respiration by EnchantmentDelegate(Enchantment.RESPIRATION) //水下呼吸
        var aquaAffinity by EnchantmentDelegate(Enchantment.AQUA_AFFINITY) //水下速掘
        var depthStrider by EnchantmentDelegate(Enchantment.DEPTH_STRIDER) //深海探索者
        var frostWalker by EnchantmentDelegate(Enchantment.FROST_WALKER) //霜冻行者
        var sweeping by EnchantmentDelegate(Enchantment.SWEEPING) //横扫之刃
        var efficiency by EnchantmentDelegate(Enchantment.EFFICIENCY) //效率
        var infinity by EnchantmentDelegate(Enchantment.INFINITY) //无限
        var riptide by EnchantmentDelegate(Enchantment.RIPTIDE) //激流
        var quickCharge by EnchantmentDelegate(Enchantment.QUICK_CHARGE) //快速装填

        operator fun invoke(lamb: EnchantmentsContext.() -> Unit) {
            lamb.invoke(enchant)
        }

        inner class EnchantmentDelegate(private val enchantment: Enchantment) {

            operator fun getValue(thisRef: Any?, property: KProperty<*>): Short {
                return enchantmentMap[enchantment] ?: 0
            }

            operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Short) {
                cached = false
                enchantmentMap[enchantment] = value
            }
        }

        fun copyTo(to: EnchantmentsContext) {
            to.enchantmentMap.clear()
            to.enchantmentMap.putAll(enchantmentMap)
        }
    }

    inner class WrittenBookMetaContext {

        var resolved = false
        var generation = WrittenBookGeneration.ORIGINAL
        val original = WrittenBookGeneration.ORIGINAL
        val copy = WrittenBookGeneration.COPY_OF_ORIGINAL
        val copyOfCopy = WrittenBookGeneration.COPY_OF_COPY
        val tattered = WrittenBookGeneration.TATTERED

        var author = "匿名"
        var title = "未命名"
        val pages = ArrayList<String>()

        operator fun invoke(lamb: WrittenBookMetaContext.() -> Unit) {
            lamb.invoke(writtenBook)
        }

        fun copyTo(to: WrittenBookMetaContext) {
            to.resolved = resolved
            to.author = author
            to.title = title
            to.pages.clear()
            to.pages.addAll(pages)
        }

    }

    inner class ColorWrapper {
        operator fun invoke(r: Int, g: Int, b: Int) {
            colorBacking = Color(r, g, b)
        }
    }

    fun copyTo(to: AbstractItem) {

        to.cached = false

        to.material = material
        to.amount = amount
        to.name = name
        to.damage = damage
        to.unbreakable = unbreakable
        to.colorBacking = colorBacking

        lore.copyTo(to.lore)
        hide.copyTo(to.hide)
        enchant.copyTo(to.enchant)
        writtenBook.copyTo(to.writtenBook)

    }

}