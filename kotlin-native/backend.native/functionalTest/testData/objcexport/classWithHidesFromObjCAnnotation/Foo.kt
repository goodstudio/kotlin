
@ExperimentalObjCRefinement
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
@kotlin.native.HidesFromObjC
annotation class MyHiddenFromObjC

@MyHiddenFromObjC
class Hidden

class NotHidden