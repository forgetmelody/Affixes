package io.github.forgetmelody.affixes.registries;

import io.github.forgetmelody.affixes.api.Affix;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class BuiltInRegistries {
    public static final Registry<DataComponentType<?>> AFFIX_EFFECT_COMPONENT_TYPE = create(Registries.AFFIX_EFFECT_COMPONENT_TYPE);

    private BuiltInRegistries() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(NewRegistryEvent.class, event -> {
            event.register(AFFIX_EFFECT_COMPONENT_TYPE);
        });
        modEventBus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> {
            event.dataPackRegistry(Registries.AFFIX, Affix.DIRECT_CODEC, Affix.DIRECT_CODEC);
        });
    }

    private static <T> Registry<T> create(ResourceKey<Registry<T>> key) {
        return new RegistryBuilder<>(key).create();
    }

}
