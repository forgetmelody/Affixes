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
    public static final ResourceKey<Affix> TEST = register("test");

    private Affixes() {
    }

    public static void boostrap(BootstrapContext<Affix> context) {
        context.register(
                TEST,
                Affix.builder()
                        .withEffect(AffixEffectComponents.IMMUNE_DAMAGE, DamageSourceCondition.hasDamageSource(new DamageSourcePredicate.Builder().tag(TagPredicate.is(DamageTypeTags.IS_PLAYER_ATTACK))))
                        .build(TEST.location())
        );
    }

    private static ResourceKey<Affix> register(String name) {
        return ResourceKey.create(Registries.AFFIX, AffixesMod.id(name));
    }
}
