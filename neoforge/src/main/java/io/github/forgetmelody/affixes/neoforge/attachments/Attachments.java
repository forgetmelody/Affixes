package io.github.forgetmelody.affixes.neoforge.attachments;

import io.github.forgetmelody.affixes.common.api.EntityAffixes;
import net.neoforged.neoforge.attachment.AttachmentType;

public final class Attachments {
    public static final AttachmentType<EntityAffixes> AFFIXES = AttachmentType.builder(() -> EntityAffixes.EMPTY).serialize(EntityAffixes.CODEC).sync(EntityAffixes.STREAM_CODEC).build();

    private Attachments() {
    }

}
