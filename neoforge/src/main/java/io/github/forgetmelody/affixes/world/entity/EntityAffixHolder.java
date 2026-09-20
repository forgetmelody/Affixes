package io.github.forgetmelody.affixes.world.entity;

import io.github.forgetmelody.affixes.api.AffixHolder;
import io.github.forgetmelody.affixes.api.EntityAffixes;
import io.github.forgetmelody.affixes.attachments.Attachments;
import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;

public class EntityAffixHolder implements AffixHolder {
    private final Entity entity;

    public EntityAffixHolder(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void runIteration(AffixVisitor visitor) {
        getOrDefault().affixes().forEach(visitor::accept);
    }

    @Override
    public void update(Consumer<Mutable> updater) {
        if (!this.entity.level().isClientSide()) {
            EntityAffixes.Mutable mutableEntityAffixes = getOrDefault().mutable();
            updater.accept(mutableEntityAffixes);
            EntityAffixes immutableEntityAffixes = mutableEntityAffixes.toImmutable();

            if (immutableEntityAffixes.isEmpty()) {
                this.entity.removeData(Attachments.AFFIXES);
            } else {
                this.entity.setData(Attachments.AFFIXES, immutableEntityAffixes);
            }
        }
    }

    private EntityAffixes getOrDefault() {
        EntityAffixes entityAffixes = this.entity.getExistingDataOrNull(Attachments.AFFIXES);
        return entityAffixes != null ? entityAffixes : EntityAffixes.EMPTY;
    }
}
