package io.github.forgetmelody.affixes.common.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;
import java.util.Optional;

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

    public static LootContext damageContext(ServerLevel world, int level, Entity entity, DamageSource source) {
        LootParams lootparams = new LootParams.Builder(world)
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(io.github.forgetmelody.affixes.common.loot.parameters.LootContextParams.AFFIX_LEVEL, level)
                .withParameter(LootContextParams.ORIGIN, entity.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, source)
                .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, source.getEntity())
                .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, source.getDirectEntity())
                .create(LootContextParamSets.ENCHANTED_DAMAGE);
        return new LootContext.Builder(lootparams).create(Optional.empty());
    }

    public <T> List<T> getEffects(DataComponentType<List<T>> effectComponent) {
        return this.effects.getOrDefault(effectComponent, List.of());
    }

    /**
     * 将直接使用LootContext以支持更多上下文参数
     * @param world
     * @param level
     * @param victim
     * @param source
     * @return
     */
    public boolean isImmuneDamage(ServerLevel world, int level, LivingEntity victim, DamageSource source) {
        LootContext context = damageContext(world, level, victim, source);
        for (ConditionalEffect<Unit> effect : getEffects(AffixEffectComponents.IMMUNE_DAMAGE)) {
            if (effect.matches(context)) {
                return true;
            }
        }

        return false;
    }


}
