// IGNORE_BACKEND_K1: JS, JS_IR, JS_IR_ES6, WASM
// IGNORE_NATIVE_K1: mode=ONE_STAGE_MULTI_MODULE
// !LANGUAGE: +MultiPlatformProjects

// MODULE: common
// TARGET_PLATFORM: Common
// FILE: commonMain.kt

expect class R

expect fun ret(): R

fun foo() = ::ret

// MODULE: platform()()(common)
// FILE: platform.kt

actual fun ret(): R = "OK"

actual typealias R = String

fun box() = foo().invoke()