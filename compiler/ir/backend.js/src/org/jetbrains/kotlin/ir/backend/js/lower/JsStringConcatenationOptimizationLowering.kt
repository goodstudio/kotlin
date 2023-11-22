/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.backend.js.lower

import org.jetbrains.kotlin.backend.common.BodyLoweringPass
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins
import org.jetbrains.kotlin.ir.backend.js.utils.jsFunctionSignature
import org.jetbrains.kotlin.ir.backend.js.utils.valueArguments
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.types.classOrNull
import org.jetbrains.kotlin.ir.util.isFinalClass
import org.jetbrains.kotlin.ir.util.overrides
import org.jetbrains.kotlin.ir.visitors.*

class JsStringConcatenationOptimizationLowering(private val context: JsIrBackendContext) : BodyLoweringPass {
    override fun lower(irBody: IrBody, container: IrDeclaration) {
        irBody.transformChildrenVoid(object : IrElementTransformerVoid() {
            override fun visitCall(expression: IrCall): IrExpression {
                expression.transformChildrenVoid()
                return tryUnwrapToStringCall(expression) ?: expression
            }
        })
    }

    private fun tryUnwrapToStringCall(expression: IrCall): IrExpression? {
        if (expression.origin != JsStatementOrigins.EXPLICIT_TO_STRING_CALL) {
            return null
        }
        val toStringReceiver = expression.dispatchReceiver
        val receiverFinalClass = toStringReceiver?.type?.classOrNull?.owner?.takeIf { it.isFinalClass } ?: return null
        if (receiverFinalClass.declarations.none { it is IrFunction && jsFunctionSignature(it, context) == "valueOf" }) {
            return toStringReceiver
        }
        return null
    }
}
