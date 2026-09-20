package io.github.forgetmelody.affixes.neoforge;

import com.mojang.serialization.Codec;
import io.github.forgetmelody.affixes.common.AffixesMod;
import io.github.forgetmelody.affixes.common.api.Affix;
import io.github.forgetmelody.affixes.common.api.AffixEffectComponents;
import io.github.forgetmelody.affixes.common.api.AffixHolder;
import io.github.forgetmelody.affixes.common.registries.Registries;
import io.github.forgetmelody.affixes.neoforge.registries.BuiltInRegistries;
import io.github.forgetmelody.affixes.neoforge.world.entity.EntityAffixHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.commons.lang3.mutable.MutableBoolean;

@Mod(AffixesMod.MOD_ID)
public class AffixesNeoForgeMod extends AffixesMod {

    public AffixesNeoForgeMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerRegistries);
        modEventBus.addListener(this::registerDataPackRegistries);
        modEventBus.addListener(this::registerContext);
        NeoForge.EVENT_BUS.addListener(this::immuneDamage);
    }

    @Override
    public AffixHolder get(Entity entity) {
        return new EntityAffixHolder(entity);
    }

    @Override
    public Codec<DataComponentType<?>> getAffixEffectComponentCodec() {
        return BuiltInRegistries.AFFIX_EFFECT_COMPONENT_TYPE.byNameCodec();
    }

    private void registerRegistries(NewRegistryEvent event) {
        event.register(BuiltInRegistries.AFFIX_EFFECT_COMPONENT_TYPE);
    }

    private void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Registries.AFFIX, Affix.DIRECT_CODEC, Affix.DIRECT_CODEC);
    }

    private void registerContext(RegisterEvent event) {
        event.register(Registries.AFFIX_EFFECT_COMPONENT_TYPE, helper -> {
            helper.register(id("immune_damage"), AffixEffectComponents.IMMUNE_DAMAGE);
        });
    }

    private void immuneDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().level() instanceof ServerLevel world) {
            float amount = event.getAmount();
            DamageContainer damage = event.getContainer();
            LivingEntity victim = event.getEntity();
            DamageSource source = event.getSource();

            MutableBoolean immune = new MutableBoolean();

            AffixHolder.get(victim).runIteration((affix, level) -> immune.setValue(
                    immune.isTrue() || affix.value().isImmuneDamage(world, level, victim, source)
            ));

            if (immune.booleanValue()) {
                event.setCanceled(true);
            }
        }

    }


}
