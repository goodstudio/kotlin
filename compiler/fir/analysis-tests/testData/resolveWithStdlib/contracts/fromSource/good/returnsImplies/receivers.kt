// RUN_PIPELINE_TILL: FRONTEND
import kotlin.contracts.*

interface A

fun A.foo() {}

@OptIn(ExperimentalContracts::class)
fun Any?.myRequireNotNull() {
    contract {
        returns() implies (this@myRequireNotNull != null)
    }
    if (this == null) throw IllegalStateException()
}

fun test_1(x: A?) {
    x.myRequireNotNull()
    x.foo()
}

fun test_2(x: A?) {
    x.myRequireNotNull()
    with(x) {
        foo()
    }
}

fun test_3(x: A?) {
    with(x) {
        myRequireNotNull()
    }
    x<!UNSAFE_CALL!>.<!>foo()
}

fun test_4(x: A?) {
    with(x) {
        myRequireNotNull()
        foo()
    }
}

fun A?.test_5() {
    myRequireNotNull()
    foo()
}

/* GENERATED_FIR_TAGS: classReference, contractConditionalEffect, contracts, equalityExpression,
funWithExtensionReceiver, functionDeclaration, ifExpression, interfaceDeclaration, lambdaLiteral, nullableType,
smartcast, thisExpression */
