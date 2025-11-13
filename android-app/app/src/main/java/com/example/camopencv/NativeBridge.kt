package com.example.camopencv

object NativeBridge {
    init { System.loadLibrary("native-lib") }
    external fun processFrame(data: ByteArray, w: Int, h: Int): ByteArray
}
