// RUN_PIPELINE_TILL: BACKEND
class A(i: Int)

typealias AA = A

class B<T>(t: T)

typealias BB<U> = B<U>

fun main() {
    val x = AA(1)
    val y = BB<String>("bb")
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, integerLiteral, localProperty, nullableType,
primaryConstructor, propertyDeclaration, stringLiteral, typeAliasDeclaration, typeAliasDeclarationWithTypeParameter,
typeParameter */
