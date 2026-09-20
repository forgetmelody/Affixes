package io.github.forgetmelody.affixes.neoforge.attachments;

import io.github.forgetmelody.affixes.common.AffixesMod;
import io.github.forgetmelody.affixes.common.api.EntityAffixes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class Attachments {
    private static final DeferredRegister<AttachmentType<?>> DEFERRED_REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AffixesMod.MOD_ID);
    public static final Supplier<AttachmentType<EntityAffixes>> AFFIXES = register("affixes", AttachmentType.builder(() -> EntityAffixes.EMPTY).serialize(EntityAffixes.CODEC).sync(EntityAffixes.STREAM_CODEC));

    private Attachments() {
    }

    public static void register(IEventBus modEventBus) {
        DEFERRED_REGISTER.register(modEventBus);
    }

    private static <T> Supplier<AttachmentType<T>> register(String name, AttachmentType.Builder<T> builder) {
        return DEFERRED_REGISTER.register(name, builder::build);
    }
}
