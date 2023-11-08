class PricedBridgeNative(length: Int, builder: String) : Bridge(length, builder) {
    fun price() = length * builder.length * 300
}
