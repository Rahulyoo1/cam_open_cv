package com.example.camopencv

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.Camera
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.SurfaceTexture
import android.view.TextureView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer

class MainActivity : Activity(), TextureView.SurfaceTextureListener {
    private lateinit var textureView: TextureView
    private var camera: Camera? = null
    private var saved = false
    private val TAG="CamOpenCV"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textureView = TextureView(this)
        setContentView(textureView)
        textureView.surfaceTextureListener = this

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 11)
        }
    }

    override fun onSurfaceTextureAvailable(st: SurfaceTexture, w: Int, h: Int) {
        camera = Camera.open()
        camera?.apply {
            val p = parameters
            setPreviewTexture(st)

            setPreviewCallback { data, cam ->
                val ps = cam.parameters.previewSize
                val yuv = YuvImage(data, ImageFormat.NV21, ps.width, ps.height, null)
                val bos = ByteArrayOutputStream()
                yuv.compressToJpeg(Rect(0,0,ps.width,ps.height), 90, bos)
                val jpeg = bos.toByteArray()
                val bmp = BitmapFactory.decodeByteArray(jpeg,0,jpeg.size)
                val rgba = ByteArray(bmp.byteCount)
                ByteBuffer.wrap(rgba).apply { bmp.copyPixelsToBuffer(this) }

                val processed = NativeBridge.processFrame(rgba, bmp.width, bmp.height)
                val outBmp = Bitmap.createBitmap(bmp.width,bmp.height,Bitmap.Config.ARGB_8888)
                outBmp.copyPixelsFromBuffer(ByteBuffer.wrap(processed))

                val canvas = textureView.lockCanvas()
                canvas.drawBitmap(outBmp, null, Rect(0,0,textureView.width,textureView.height), null)
                textureView.unlockCanvasAndPost(canvas)

                if (!saved) {
                    saved = true
                    val file = File(getExternalFilesDir(null),"processed_frame_base64.txt")
                    val bout = ByteArrayOutputStream()
                    outBmp.compress(Bitmap.CompressFormat.PNG,100,bout)
                    file.writeText(Base64.encodeToString(bout.toByteArray(),Base64.NO_WRAP))
                    Log.i(TAG,"Saved base64 to "+file.absolutePath)
                }
            }
            startPreview()
        }
    }

    override fun onSurfaceTextureSizeChanged(s: SurfaceTexture, w: Int, h: Int) {}
    override fun onSurfaceTextureDestroyed(s: SurfaceTexture)=true.also{
        camera?.stopPreview(); camera?.release()
    }
    override fun onSurfaceTextureUpdated(s: SurfaceTexture) {}
}
