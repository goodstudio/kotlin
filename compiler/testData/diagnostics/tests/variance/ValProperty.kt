// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
import kotlin.reflect.KProperty

interface In<in T>
interface Out<out T>
interface Inv<T>

class Delegate<T> {
    operator fun getValue(t: Any, p: KProperty<*>): T = null!!
    operator fun setValue(t: Any, p: KProperty<*>, value: T) {}
}

fun <T> getT(): T = null!!

abstract class Test<in I, out O, P> {
    abstract val type1: <!TYPE_VARIANCE_CONFLICT_ERROR!>I<!>
    abstract val type2: O
    abstract val type3: P
    abstract val type4: In<I>
    abstract val type5: In<<!TYPE_VARIANCE_CONFLICT_ERROR!>O<!>>

    <!TYPE_VARIANCE_CONFLICT_ERROR!>val implicitType1<!> = getT<I>()
    val implicitType2 = getT<O>()
    val implicitType3 = getT<P>()
    val implicitType4 = getT<In<I>>()
    <!TYPE_VARIANCE_CONFLICT_ERROR!>val implicitType5<!> = getT<In<O>>()

    <!TYPE_VARIANCE_CONFLICT_ERROR!>val delegateType1<!> by Delegate<I>()
    val delegateType2 by Delegate<O>()
    val delegateType3 by Delegate<P>()
    val delegateType4 by Delegate<In<I>>()
    <!TYPE_VARIANCE_CONFLICT_ERROR!>val delegateType5<!> by Delegate<In<O>>()

    abstract val I.receiver1: Int
    abstract val <!TYPE_VARIANCE_CONFLICT_ERROR!>O<!>.receiver2: Int
    abstract val P.receiver3: Int
    abstract val In<<!TYPE_VARIANCE_CONFLICT_ERROR!>I<!>>.receiver4: Int
    abstract val In<O>.receiver5: Int

    val <X : I> X.typeParameter1: Int get() = 0
    val <X : <!TYPE_VARIANCE_CONFLICT_ERROR!>O<!>> X.typeParameter2: Int get() = 0
    val <X : P> X.typeParameter3: Int get() = 0
    val <X : In<<!TYPE_VARIANCE_CONFLICT_ERROR!>I<!>>> X.typeParameter4: Int get() = 0
    val <X : In<O>> X.typeParameter5: Int get() = 0

    val <X> X.typeParameter6: Int where X : I get() = 0
    val <X> X.typeParameter7: Int where X : <!TYPE_VARIANCE_CONFLICT_ERROR!>O<!> get() = 0
    val <X> X.typeParameter8: Int where X : P get() = 0
    val <X> X.typeParameter9: Int where X : In<<!TYPE_VARIANCE_CONFLICT_ERROR!>I<!>> get() = 0
    val <X> X.typeParameter0: Int where X : In<O> get() = 0
}

/* GENERATED_FIR_TAGS: checkNotNullCall, classDeclaration, functionDeclaration, getter, in, integerLiteral,
interfaceDeclaration, nullableType, operator, out, propertyDeclaration, propertyDelegate, propertyWithExtensionReceiver,
starProjection, typeConstraint, typeParameter */
