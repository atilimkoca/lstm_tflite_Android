package com.example.myapplication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.sql.DriverManager
import org.tensorflow.lite.Interpreter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tfliteModel = loadModelFile(this)


        var inputData: ByteBuffer = ByteBuffer.allocateDirect(76

        )
        //inputData.order(ByteOrder.nativeOrder())
        //inputData.putFloat(150f)

        val value = byteArrayOf(127,0,0,110,0,0,120,0,0,110,0,0,110,0,0,110,0,0,110,110,0,0,110,0,0,110,0,0,110,0,0,110,0,0,110,0,0,110,110,0,0,110,0,0,110,0,0,110,0,0,110,0,0,110,0,0,110,110,0,0,110,0,0,110,0,0,110,0,0,110,0,0,110,0,0,110)
        val value2= floatArrayOf(127F,0F,0F,110F,0F,0F,120F,0F,0F,110F,0F,0F,110F,0F,0F,110F,0F,0F,110F)
        inputData.position(0)
        //inputData.array(value2)
        val data = arrayOf(
            arrayOf(floatArrayOf(127F,0F,0F,110F,0F,0F,120F,0F,0F,110F,0F,0F,110F,0F,0F,110F,0F,0F,110F))

        )

        val labelProbArray: Array<FloatArray> = Array(1) { FloatArray(5) }
        val tflite = Interpreter(tfliteModel, Interpreter.Options())
        tflite.run(data, labelProbArray)

        var prediction = (labelProbArray[0][0])

        DriverManager.println("test" + prediction)
        //runOnUiThread { result.text = "Prediction $prediction" }
    }
    private fun loadModelFile(activity: Activity): MappedByteBuffer {
        val fileDescriptor = activity.assets.openFd("lstmmodel.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}