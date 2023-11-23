/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.low.level.api.fir.lazy.resolve

import org.jetbrains.kotlin.analysis.low.level.api.fir.api.targets.LLFirClassWithAllCallablesResolveTarget
import org.jetbrains.kotlin.analysis.low.level.api.fir.api.targets.LLFirResolveTarget
import org.jetbrains.kotlin.analysis.low.level.api.fir.api.targets.LLFirSingleResolveTarget
import org.jetbrains.kotlin.analysis.low.level.api.fir.api.targets.LLFirWholeElementResolveTarget
import org.jetbrains.kotlin.analysis.low.level.api.fir.api.targets.asResolveTarget
import org.jetbrains.kotlin.analysis.low.level.api.fir.api.throwUnexpectedFirElementError
import org.jetbrains.kotlin.analysis.low.level.api.fir.api.tryCollectDesignationWithFile
import org.jetbrains.kotlin.fir.FirElementWithResolveState
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor

internal object LLFirResolveMultiDesignationCollector {
    fun getDesignationsToResolve(target: FirElementWithResolveState): List<LLFirResolveTarget> = when (target) {
        is FirFile -> listOf(LLFirSingleResolveTarget(target))
        is FirSyntheticPropertyAccessor -> getDesignationsToResolve(target.delegate)
        is FirSyntheticProperty -> getDesignationsToResolve(target.getter) + target.setter?.let(::getDesignationsToResolve).orEmpty()
        else -> listOfNotNull(getMainDesignationToResolve(target))
    }

    fun getDesignationsToResolveWithCallableMembers(target: FirRegularClass): List<LLFirResolveTarget> {
        val designation = target.tryCollectDesignationWithFile() ?: return emptyList()
        val resolveTarget = LLFirClassWithAllCallablesResolveTarget(designation.firFile, designation.path, target)
        return listOf(resolveTarget)
    }

    fun getDesignationsToResolveRecursively(target: FirElementWithResolveState): List<LLFirResolveTarget> {
        if (target is FirFile) return listOf(LLFirWholeElementResolveTarget(target))

        if (!target.shouldBeResolved()) return emptyList()
        val designation = target.tryCollectDesignationWithFile() ?: return emptyList()
        val resolveTarget = LLFirWholeElementResolveTarget(designation.firFile, designation.path, target)
        return listOf(resolveTarget)
    }

    private fun getMainDesignationToResolve(target: FirElementWithResolveState): LLFirResolveTarget? {
        require(target !is FirFile)
        if (!target.shouldBeResolved()) return null
        return when (target) {
            is FirPropertyAccessor -> getMainDesignationToResolve(target.propertySymbol.fir)
            is FirBackingField -> getMainDesignationToResolve(target.propertySymbol.fir)
            is FirTypeParameter -> getMainDesignationToResolve(target.containingDeclarationSymbol.fir)
            is FirValueParameter -> getMainDesignationToResolve(target.containingFunctionSymbol.fir)
            else -> target.tryCollectDesignationWithFile()?.asResolveTarget()
        }
    }

    private fun FirElementWithResolveState.shouldBeResolved() = when (this) {
        is FirDeclaration -> shouldBeResolved()
        is FirFileAnnotationsContainer -> annotations.isNotEmpty()
        else -> throwUnexpectedFirElementError(this)
    }

    private fun FirDeclaration.shouldBeResolved(): Boolean = when (origin) {
        is FirDeclarationOrigin.Source,
        is FirDeclarationOrigin.ImportedFromObjectOrStatic,
        is FirDeclarationOrigin.Delegated,
        is FirDeclarationOrigin.Synthetic,
        is FirDeclarationOrigin.SubstitutionOverride,
        is FirDeclarationOrigin.SamConstructor,
        is FirDeclarationOrigin.WrappedIntegerOperator,
        is FirDeclarationOrigin.IntersectionOverride,
        is FirDeclarationOrigin.ScriptCustomization,
        -> {
            when (this) {
                is FirFile -> true
                is FirSyntheticProperty, is FirSyntheticPropertyAccessor -> false
                is FirSimpleFunction,
                is FirProperty,
                is FirPropertyAccessor,
                is FirField,
                is FirTypeAlias,
                is FirConstructor,
                -> true
                else -> true
            }
        }
        else -> {
            @OptIn(ResolveStateAccess::class)
            check(resolvePhase == FirResolvePhase.BODY_RESOLVE) {
                "Expected body resolve phase for origin $origin but found $resolveState"
            }

            false
        }
    }
}
