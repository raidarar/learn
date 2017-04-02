package org.raidarar.learn

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import kotlin.reflect.KClass

object StructCache {

    val map: MutableMap<Class<*>, Struct> = Object2ObjectArrayMap<Class<*>, Struct>()

    operator inline fun <reified T : Struct> get(type: Class<*>, vararg args: Any): T {
        var struct = map[type]
        if (struct == null) {
            struct = (if (args.isNotEmpty()) {
                val types = arrayOfNulls<Class<*>>(args.size)
                type.declaredFields.forEachIndexed { i, field -> types[i] = field.type }
                val constructor = type.getDeclaredConstructor(*types)
                constructor.newInstance(*args)
            } else type.newInstance()) as T
            map[type] = struct
        }
        return struct as T
    }

    operator inline fun <reified T : Struct> get(type: KClass<T>, vararg args: Any): T = get(type.java, *args)

}

inline operator fun <reified T : Struct> KClass<T>.get(vararg args: Any)
        = StructCache[this, args]