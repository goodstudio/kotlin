// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
//KT-2334 An error 'local function without body' is not reported

fun foo() {
    <!NON_MEMBER_FUNCTION_NO_BODY!>fun bar()<!>
}

/* GENERATED_FIR_TAGS: functionDeclaration, localFunction */
