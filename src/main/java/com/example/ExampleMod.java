package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ExampleMod implements ModInitializer {
	public static final RegistryKey<Registry<ExampleRecord>> RECORD_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("example", "record"));
	public static final RegistryKey<ExampleRecord> BOOGLY = RegistryKey.of(RECORD_REGISTRY_KEY, new Identifier("example", "boogly"));
	public static final DataComponentType<RegistryEntry<ExampleRecord>> RECORD_COMPONENT = DataComponentType.<RegistryEntry<ExampleRecord>>builder()
			.codec(ExampleRecord.CODEC)
			.packetCodec(ExampleRecord.PACKET_CODEC)
			.build();
	public static final Item ITEM = Registry.register(Registries.ITEM, new Identifier("example", "item"), new Item(new Item.Settings().maxCount(1)));

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			ItemStack stack = new ItemStack(ITEM);
			stack.set(RECORD_COMPONENT, entries.getContext().lookup().createRegistryLookup().getOrThrow(RECORD_REGISTRY_KEY).getOrThrow(BOOGLY));
			entries.add(stack);
		});
		Registry.register(Registries.DATA_COMPONENT_TYPE, new Identifier("example", "component"), RECORD_COMPONENT);
		DynamicRegistries.registerSynced(RECORD_REGISTRY_KEY, ExampleRecord.DIRECT_CODEC);
	}
}