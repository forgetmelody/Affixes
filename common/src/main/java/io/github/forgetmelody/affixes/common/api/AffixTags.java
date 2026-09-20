package io.github.forgetmelody.affixes.common.api;

import io.github.forgetmelody.affixes.common.AffixesMod;
import io.github.forgetmelody.affixes.common.registries.Registries;
import net.minecraft.tags.TagKey;

public final class AffixTags {
    private AffixTags() {
    }

    private static TagKey<Affix> create(String name) {
        return TagKey.create(Registries.AFFIX, AffixesMod.id(name));
    }
}

