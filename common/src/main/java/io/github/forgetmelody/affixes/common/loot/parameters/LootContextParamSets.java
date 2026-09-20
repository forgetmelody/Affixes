package io.github.forgetmelody.affixes.common.loot.parameters;

import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public final class LootContextParamSets {
    public static final LootContextParamSet AFFIX_DAMAGE = LootContextParamSet.builder()
            .required(LootContextParams.THIS_ENTITY)
            .required(io.github.forgetmelody.affixes.common.loot.parameters.LootContextParams.AFFIX_LEVEL)
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.DAMAGE_SOURCE)
            .optional(LootContextParams.DIRECT_ATTACKING_ENTITY)
            .optional(LootContextParams.ATTACKING_ENTITY)
            .build();

    private LootContextParamSets() {
    }
}
