package io.github.forgetmelody.affixes.api;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMaps;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.function.Predicate;

public record EntityAffixes(Reference2IntMap<Holder<Affix>> affixes) {
    public static final EntityAffixes EMPTY = new EntityAffixes(Reference2IntMaps.emptyMap());
    public static final Codec<EntityAffixes> CODEC = Codec.unboundedMap(Affix.REFERENCE_CODEC, Codec.INT).xmap(EntityAffixes::new, EntityAffixes::affixes);
    public static final StreamCodec<RegistryFriendlyByteBuf, EntityAffixes> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(Reference2IntOpenHashMap::new, Affix.STREAM_CODEC, ByteBufCodecs.VAR_INT),
            EntityAffixes::affixes,
            EntityAffixes::new
    );

    private EntityAffixes(Map<Holder<Affix>, Integer> affixes) {
        this(new Reference2IntOpenHashMap<>(affixes));
    }

    public boolean isEmpty() {
        return this.affixes.isEmpty();
    }

    public EntityAffixes.Mutable mutable() {
        return new Mutable(this);
    }

    public static class Mutable implements AffixHolder.Mutable {
        private final Reference2IntMap<Holder<Affix>> affixes;

        public Mutable() {
            this.affixes = new Reference2IntOpenHashMap<>();
        }

        private Mutable(EntityAffixes entityAffixes) {
            this.affixes = new Reference2IntOpenHashMap<>(entityAffixes.affixes);
        }

        @Override
        public void upgrade(Holder<Affix> affix, int level) {
            if (level > 0) {
                this.affixes.merge(affix, Math.min(level, 255), Integer::max);
            }
        }

        @Override
        public void set(Holder<Affix> affix, int level) {
            if (level <= 0) {
                this.affixes.removeInt(affix);
            } else {
                this.affixes.put(affix, Math.min(level, 255));
            }
        }

        @Override
        public void remove(Holder<Affix> affix) {
            this.affixes.removeInt(affix);
        }

        @Override
        public void removeIf(Predicate<Holder<Affix>> predicate) {
            this.affixes.keySet().removeIf(predicate);
        }

        public EntityAffixes toImmutable() {
            return new EntityAffixes(Reference2IntMaps.unmodifiable(new Reference2IntOpenHashMap<>(this.affixes)));
        }
    }
}
