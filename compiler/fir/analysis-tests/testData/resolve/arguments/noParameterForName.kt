// RUN_PIPELINE_TILL: FRONTEND
fun foo(x: Int) {}

fun bar() {
    foo(<!NO_VALUE_FOR_PARAMETER!><!NAMED_PARAMETER_NOT_FOUND!>y<!> = 1)<!>
}

/* GENERATED_FIR_TAGS: functionDeclaration, integerLiteral */
