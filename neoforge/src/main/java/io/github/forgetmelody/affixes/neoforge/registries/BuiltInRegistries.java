package io.github.forgetmelody.affixes.neoforge.registries;

import io.github.forgetmelody.affixes.common.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class BuiltInRegistries {
    public static final Registry<DataComponentType<?>> AFFIX_EFFECT_COMPONENT_TYPE = create(Registries.AFFIX_EFFECT_COMPONENT_TYPE);

    private BuiltInRegistries() {
    }

    private static <T> Registry<T> create(ResourceKey<Registry<T>> key) {
        return new RegistryBuilder<>(key).create();
    }

}
