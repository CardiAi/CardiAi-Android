package com.codlin.cardiai.data.datasource.remote.dto.records

import com.codlin.cardiai.domain.model.record.ChestPain
import com.codlin.cardiai.domain.model.record.ECG
import com.codlin.cardiai.domain.model.record.Slope
import com.codlin.cardiai.domain.model.record.Thal

fun stringToChestPain(string: String): ChestPain =
    ChestPain.valueOf(string.replaceSpaceWithUnderscore())

fun stringToEcg(string: String): ECG =
    ECG.valueOf(string.replaceSpaceWithUnderscore())

fun stringToSlope(string: String): Slope =
    Slope.valueOf(string.replaceSpaceWithUnderscore())

fun stringToThal(string: String): Thal =
    Thal.valueOf(string.replaceSpaceWithUnderscore())

fun String.replaceSpaceWithUnderscore(): String = replace(" ", "_")

fun String.replaceUnderscoreWithSpace(): String = replace("_", " ")