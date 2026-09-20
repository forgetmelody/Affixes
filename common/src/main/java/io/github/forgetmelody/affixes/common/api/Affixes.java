package io.github.forgetmelody.affixes.common.api;

import io.github.forgetmelody.affixes.common.AffixesMod;
import io.github.forgetmelody.affixes.common.registries.Registries;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.TagPredicate;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;

public final class Affixes {
    public static final ResourceKey<Affix> IMMUNE_PLAYER_DAMAGE = register("immune_player_damage");

    private Affixes() {
    }

    public static void boostrap(BootstrapContext<Affix> context) {
        context.register(
                IMMUNE_PLAYER_DAMAGE,
                Affix.builder()
                        .withEffect(AffixEffectComponents.IMMUNE_DAMAGE, DamageSourceCondition.hasDamageSource(new DamageSourcePredicate.Builder().tag(TagPredicate.is(DamageTypeTags.IS_PLAYER_ATTACK))))
                        .build(IMMUNE_PLAYER_DAMAGE.location())
        );
    }

    private static ResourceKey<Affix> register(String name) {
        return ResourceKey.create(Registries.AFFIX, AffixesMod.id(name));
    }
}
