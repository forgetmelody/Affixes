package io.github.forgetmelody.affixes.api;

import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

public record Affix(
        Component description,
        HolderSet<Affix> exclusiveSet,
        HolderSet<EntityType<?>> supportedEntities,
        DataComponentMap effects
) {

}
