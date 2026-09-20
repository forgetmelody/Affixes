package io.github.forgetmelody.affixes.registries;

import io.github.forgetmelody.affixes.AffixesMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;

public final class Registries {
    public static final ResourceKey<Registry<DataComponentType<?>>> AFFIX_EFFECT_COMPONENT_TYPE = key("affix_effect_component_type");

    private Registries() {
    }

    private static ResourceKey<Registry<DataComponentType<?>>> key(String name) {
        return ResourceKey.createRegistryKey(AffixesMod.id(name));
    }
}
