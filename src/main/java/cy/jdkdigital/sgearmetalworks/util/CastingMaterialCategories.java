package cy.jdkdigital.sgearmetalworks.util;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.silentchaos512.gear.api.material.IMaterialCategory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum CastingMaterialCategories implements IMaterialCategory {
    CASTING;

    private static final Map<String, IMaterialCategory> CACHE = new HashMap<>();

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static IMaterialCategory get(String key) {
        return CACHE.computeIfAbsent(key, key1 -> {
            for (CastingMaterialCategories cat : values()) {
                if (cat.getName().equalsIgnoreCase(key1)) {
                    return cat;
                }
            }
            String key2 = key1.toLowerCase(Locale.ROOT);
            return () -> key2;
        });
    }

    public static final Codec<IMaterialCategory> CODEC = Codec.STRING
            .xmap(
                    CastingMaterialCategories::get,
                    IMaterialCategory::getName
            );

    public static final StreamCodec<FriendlyByteBuf, IMaterialCategory> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, IMaterialCategory::getName,
            CastingMaterialCategories::get
    );
}