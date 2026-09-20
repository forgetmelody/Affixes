package io.github.forgetmelody.affixes.common.registries;

import io.github.forgetmelody.affixes.common.AffixesMod;
import io.github.forgetmelody.affixes.common.api.Affix;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;

public final class Registries {
    public static final ResourceKey<Registry<DataComponentType<?>>> AFFIX_EFFECT_COMPONENT_TYPE = key("affix_effect_component_type");
    public static final ResourceKey<Registry<Affix>> AFFIX = key("affix");

    private Registries() {
    }

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(AffixesMod.id(name));
    }
}
