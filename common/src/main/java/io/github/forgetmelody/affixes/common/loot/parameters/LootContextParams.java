package io.github.forgetmelody.affixes.common.loot.parameters;

import io.github.forgetmelody.affixes.common.AffixesMod;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public final class LootContextParams {
    public static final LootContextParam<Integer> AFFIX_LEVEL = create("affix_level");

    private LootContextParams() {
    }

    private static <T> LootContextParam<T> create(String name) {
        return new LootContextParam<>(AffixesMod.id(name));
    }
}
