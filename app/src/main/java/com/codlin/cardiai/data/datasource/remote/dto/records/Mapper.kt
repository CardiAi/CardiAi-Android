package com.codlin.cardiai.data.datasource.remote.dto.records

import com.codlin.cardiai.domain.model.record.RecordType

fun stringToChestPain(string: String): RecordType.ChestPain =
    RecordType.ChestPain.valueOf(string.replaceSpaceWithUnderscore())

fun stringToEcg(string: String): RecordType.ECG =
    RecordType.ECG.valueOf(string.replaceSpaceWithUnderscore())

fun stringToSlope(string: String): RecordType.Slope =
    RecordType.Slope.valueOf(string.replaceSpaceWithUnderscore())

fun stringToThal(string: String): RecordType.Thal =
    RecordType.Thal.valueOf(string.replaceSpaceWithUnderscore())

fun String.replaceSpaceWithUnderscore(): String = replace(" ", "_")

fun String.replaceUnderscoreWithSpace(): String = replace("_", " ")