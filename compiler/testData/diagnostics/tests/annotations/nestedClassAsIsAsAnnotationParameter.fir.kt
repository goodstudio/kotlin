// RUN_PIPELINE_TILL: FRONTEND
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.TYPE)
annotation class Special(val why: KClass<*>)

interface Interface

class Outer {
    @Special(<!ARGUMENT_TYPE_MISMATCH, NO_COMPANION_OBJECT!>Nested<!>)
    class Nested<@Special(<!ARGUMENT_TYPE_MISMATCH, NO_COMPANION_OBJECT!>Nested<!>) T> : @Special(<!ARGUMENT_TYPE_MISMATCH, NO_COMPANION_OBJECT!>Nested<!>) Interface
}

/* GENERATED_FIR_TAGS: annotationDeclaration, classDeclaration, interfaceDeclaration, nestedClass, nullableType,
primaryConstructor, propertyDeclaration, starProjection, typeParameter */
