package com.example;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ExampleRecord(int value) {
    public static final Codec<ExampleRecord> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("value").forGetter(ExampleRecord::value)
    ).apply(inst, ExampleRecord::new));
}
