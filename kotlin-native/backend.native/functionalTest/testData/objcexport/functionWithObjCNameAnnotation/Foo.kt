
@kotlin.native.ObjCName("objcTopLevelFunction", "swiftTopLevelFunction")
fun someTopLevelFunction() = ""

class Foo {
    @kotlin.native.ObjCName("objcMemberFunction", "swiftMemberFunction")
    fun someMemberFunction() = Unit
}