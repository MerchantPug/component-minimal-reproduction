package com.example;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;

import java.util.Map;

public record ExampleComponent(Map<RegistryEntry<ExampleRecord>, RegistryEntry<Enchantment>> values) {
    public static final Codec<ExampleComponent> CODEC = new UnboundedMapCodec<>(RegistryFixedCodec.of(ExampleMod.RECORD_REGISTRY_KEY), RegistryFixedCodec.of(RegistryKeys.ENCHANTMENT)).xmap(ExampleComponent::new, ExampleComponent::values);
    public static final PacketCodec<RegistryByteBuf, ExampleComponent> PACKET_CODEC = PacketCodecs.map(Object2ObjectOpenHashMap::new, PacketCodecs.registryEntry(ExampleMod.RECORD_REGISTRY_KEY), PacketCodecs.registryEntry(RegistryKeys.ENCHANTMENT)).xmap(ExampleComponent::new, component -> new Object2ObjectOpenHashMap<>(component.values));
}
