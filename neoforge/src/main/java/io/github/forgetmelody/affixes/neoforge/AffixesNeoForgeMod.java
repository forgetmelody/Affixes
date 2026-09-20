package io.github.forgetmelody.affixes.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.serialization.Codec;
import io.github.forgetmelody.affixes.common.AffixesMod;
import io.github.forgetmelody.affixes.common.api.Affix;
import io.github.forgetmelody.affixes.common.api.AffixEffectComponents;
import io.github.forgetmelody.affixes.common.api.AffixHolder;
import io.github.forgetmelody.affixes.common.api.Affixes;
import io.github.forgetmelody.affixes.common.commands.AffixCommands;
import io.github.forgetmelody.affixes.common.registries.Registries;
import io.github.forgetmelody.affixes.neoforge.attachments.Attachments;
import io.github.forgetmelody.affixes.neoforge.registries.BuiltInRegistries;
import io.github.forgetmelody.affixes.neoforge.world.entity.EntityAffixHolder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.concurrent.CompletableFuture;

@Mod(AffixesMod.MOD_ID)
public class AffixesNeoForgeMod extends AffixesMod {

    public AffixesNeoForgeMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerRegistries);
        modEventBus.addListener(this::registerDataPackRegistries);
        modEventBus.addListener(this::registerContext);
        modEventBus.addListener(this::generateData);
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
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
        event.register(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, context -> {
            context.register(id("affixes"), Attachments.AFFIXES);
        });
        event.register(Registries.AFFIX_EFFECT_COMPONENT_TYPE, context -> {
            context.register(id("immune_damage"), AffixEffectComponents.IMMUNE_DAMAGE);
        });
    }

    private void generateData(GatherDataEvent event) {
        if (event.includeServer()) {
            ExistingFileHelper helper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            event.createDatapackRegistryObjects(new RegistrySetBuilder().add(Registries.AFFIX, Affixes::boostrap));
        }
    }

    private void registerCommands(RegisterCommandsEvent event) {
        CommandBuildContext buildContext = event.getBuildContext();
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal(AffixesMod.MOD_ID)
                        .then(AffixCommands.register(buildContext))
        );
    }

    private void immuneDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().level() instanceof ServerLevel world) {
            float amount = event.getAmount();
            DamageContainer damage = event.getContainer();
            LivingEntity victim = event.getEntity();
            DamageSource source = event.getSource();
            AffixHolder affixHolder = AffixHolder.get(victim);

            if (affixHolder.isImmuneDamage(world, victim, source)) {
                event.setCanceled(true);
            }
        }

    }

}
