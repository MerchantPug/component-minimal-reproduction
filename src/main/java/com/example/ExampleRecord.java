package com.example;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;

public record ExampleRecord(int value) {
    public static final Codec<ExampleRecord> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("value").forGetter(ExampleRecord::value)
    ).apply(inst, ExampleRecord::new));
    public static final Codec<RegistryEntry<ExampleRecord>> CODEC = RegistryFixedCodec.of(ExampleMod.RECORD_REGISTRY_KEY);

    public static final PacketCodec<RegistryByteBuf, RegistryEntry<ExampleRecord>> PACKET_CODEC = PacketCodecs.registryEntry(ExampleMod.RECORD_REGISTRY_KEY);
}
