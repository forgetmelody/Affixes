package io.github.forgetmelody.affixes.api;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public record ConditionalEffect<T>(
        T effect,
        Optional<LootItemCondition> requirement
) {

}
