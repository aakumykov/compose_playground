/*
 * Copyright (C) 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.aakumykov.compose_playground.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object FilterList : NavKey

@Serializable
data class FilterEdit(
    val filterId: String?,
    val packageName: String?
): NavKey {
    companion object {
        fun byFilterId(filterId: String) = FilterEdit(filterId = filterId, packageName = null)
        fun byPackageName(packageName: String) = FilterEdit(filterId = null, packageName = packageName)
    }
}

@ConsistentCopyVisibility
@Serializable
data class RuleEdit private constructor(
    val filterId: String,
    val ruleId: String?
): NavKey {
    companion object {
        fun forCreate(filterId: String): RuleEdit = RuleEdit(filterId = filterId, ruleId = null)
        fun forEdit(ruleId: String, filterId: String): RuleEdit = RuleEdit(filterId = filterId, ruleId = ruleId)
    }
}