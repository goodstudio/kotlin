// RUN_PIPELINE_TILL: BACKEND
// ISSUE: KT-68618

val Int.fooA: Int get() = this@fooA

val Int.fooB get() = this<!UNRESOLVED_REFERENCE!>@fooB<!>

fun Int.fooC(): Int = this@fooC

fun Int.fooD() = this@fooD

/* GENERATED_FIR_TAGS: funWithExtensionReceiver, functionDeclaration, getter, propertyDeclaration,
propertyWithExtensionReceiver, thisExpression */
