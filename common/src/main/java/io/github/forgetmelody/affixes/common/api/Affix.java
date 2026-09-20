package io.github.forgetmelody.affixes.common.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.EntityType;

public record Affix(
        Component description,
        HolderSet<EntityType<?>> supportedEntities,
        HolderSet<Affix> exclusiveSet,
        DataComponentMap effects
) {
    public static final Codec<Affix> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.fieldOf("description").forGetter(Affix::description),
            RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("supported_entities").forGetter(Affix::supportedEntities),
            RegistryCodecs.homogeneousList(io.github.forgetmelody.affixes.common.registries.Registries.AFFIX).fieldOf("exclusive_set").forGetter(Affix::exclusiveSet),
            AffixEffectComponents.CODEC.fieldOf("effects").forGetter(Affix::effects)
    ).apply(instance, Affix::new));
    public static final Codec<Holder<Affix>> REFERENCE_CODEC = RegistryFixedCodec.create(io.github.forgetmelody.affixes.common.registries.Registries.AFFIX);
    public static final Codec<HolderSet<Affix>> LIST_CODEC = RegistryCodecs.homogeneousList(io.github.forgetmelody.affixes.common.registries.Registries.AFFIX);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Affix>> STREAM_CODEC = ByteBufCodecs.holderRegistry(io.github.forgetmelody.affixes.common.registries.Registries.AFFIX);
}
