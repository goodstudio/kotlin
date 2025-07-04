// RUN_PIPELINE_TILL: BACKEND
class My(var x: String) {

    var y: String
        get() = if (x != "") x else z
        set(arg) {
            if (arg != "") x = arg
        }

    val z: String

    var d: String = ""
        get
        set

    val z1: String

    init {
        d = "d"
        if (d != "") z1 = this.d else z1 = d

        // Dangerous: setter!
        y = "x"
        // Dangerous: getter!
        if (y != "") z = this.y else z = y
    }
}

/* GENERATED_FIR_TAGS: assignment, classDeclaration, equalityExpression, getter, ifExpression, init, primaryConstructor,
propertyDeclaration, setter, stringLiteral, thisExpression */
