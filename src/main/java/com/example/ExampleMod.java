package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ExampleMod implements ModInitializer {
	public static final RegistryKey<Registry<ExampleRecord>> RECORD_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("example", "record"));
	public static final RegistryKey<ExampleRecord> BOOGLY = RegistryKey.of(RECORD_REGISTRY_KEY, new Identifier("example", "boogly"));
	public static final DataComponentType<ExampleComponent> RECORD_COMPONENT = DataComponentType.<ExampleComponent>builder()
			.codec(ExampleComponent.CODEC)
			.packetCodec(ExampleComponent.PACKET_CODEC)
			.build();
	public static final Item ITEM = Registry.register(Registries.ITEM, new Identifier("example", "item"), new Item(new Item.Settings().maxCount(1)));

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			ItemStack stack = new ItemStack(ITEM);
			stack.set(RECORD_COMPONENT, new ExampleComponent(Map.of(entries.getContext().lookup().createRegistryLookup().getOrThrow(RECORD_REGISTRY_KEY).getOrThrow(BOOGLY), Enchantments.AQUA_AFFINITY.getRegistryEntry())));
			entries.add(stack);
		});
		DynamicRegistries.registerSynced(RECORD_REGISTRY_KEY, ExampleRecord.CODEC);
	}
}