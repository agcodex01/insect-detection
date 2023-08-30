package com.example.objectdetection.screens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color.BLACK
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.objectdetection.MainActivity
import com.example.objectdetection.R
import com.example.objectdetection.data.CameraState
import com.example.objectdetection.ml.Insects
import com.example.objectdetection.view_model.CameraViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.util.Arrays


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(paddingValues: PaddingValues, context: Context = LocalContext.current) {
    val cameraPermission: PermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA);

    MainContent(
        hasPermission = cameraPermission.status.isGranted,
        onRequestPermission = cameraPermission::launchPermissionRequest,
        context = context
    )
}

@Composable
fun MainContent(
    hasPermission: Boolean, onRequestPermission: () -> Unit, context: Context = LocalContext.current
) {
    if (hasPermission) {
        CameraScreen(context = context);
    } else {
        NoPermissionScreen(onRequestPermission = onRequestPermission);
    }
}

@Composable
fun CameraScreen(
    viewModel: CameraViewModel = viewModel(), context: Context = LocalContext.current
) {
    val cameraState: CameraState by viewModel.state.collectAsStateWithLifecycle()

    CameraContent(
        onPhotoCaptured = viewModel::onPhotoCaptured
    )

    cameraState.capturedImage?.let { capturedImage: Bitmap ->
        CapturedImageBitmapDialog(
            capturedImage = capturedImage,
            onDismissRequest = viewModel::onCapturedPhotoConsumed,
            context = context
        )
    }
}


@Composable
private fun CapturedImageBitmapDialog(
    capturedImage: Bitmap,
    onDismissRequest: () -> Unit,
    context: Context = LocalContext.current
) {

//    val imageProcessor = ImageProcessor.Builder().add(
//        ResizeOp(
//            640, 640, ResizeOp.ResizeMethod.BILINEAR
//        )
//    ).build()
//
//    var tensorImage = TensorImage(DataType.FLOAT32);
//    tensorImage.load(capturedImage)
//    tensorImage = imageProcessor.process(tensorImage)
//
//    val model = Insects.newInstance(context)

    // Creates inputs for reference.
//    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 3, 640, 640), DataType.FLOAT32)
//    inputFeature0.loadBuffer(tensorImage.buffer)

    // Runs model inference and gets result.
//    val outputs = model.process(inputFeature0)
//    val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

//    model.run {
//        tensorImage.buffer
//        outputs
//    }
//    Log.i("RESULT", Arrays.toString(outputs.outputFeature0AsTensorBuffer.intArray));

//    val labels = FileUtil.loadLabels(context, "labels.txt")

//    var maxIndex = 0
//
//    outputFeature0.forEachIndexed { index, fl ->
//        if (outputFeature0[maxIndex] < fl) {
//            maxIndex = index;
//        }
//    }
//
//    model.run {  }


//    val capturedImageBitmap: ImageBitmap = remember { capturedImage.asImageBitmap() }

    Dialog(onDismissRequest = {
        onDismissRequest()
    }) {
        Image(
            bitmap = capturedImage.asImageBitmap(), contentDescription = "Captured photo"
        )
        Text(text = "Sample Detection")
    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun CameraContent(
    onPhotoCaptured: (Bitmap) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }


    Scaffold(floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {
        ExtendedFloatingActionButton(text = { Text(text = "Take photo") },
            icon = { Icon(Icons.Filled.BrowseGallery, "Extended floating action button.") },
            onClick = {
                val mainExecutor = ContextCompat.getMainExecutor(context)

                cameraController.takePicture(mainExecutor,
                    object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {

                            // Note: There is a known issue with the CameraX library, that JPEG images are currently not supported
                            // for Bitmap conversion via `.toBitmap()`. A Fix is on the way: https://android-review.googlesource.com/c/platform/frameworks/support/+/2594225
                            // Probably will released with the next 1.3.0-alpha/beta version of CameraX https://developer.android.com/jetpack/androidx/releases/camera#version_13_2
                            //
                            // tl;dr: The following therefore only will work in the next version of CameraX
                            // In the meantime you can use the ImageCapture.OnImageSavedCallback overload of the takePicture method
                            val correctedBitmap: Bitmap = image.toBitmap()

                            onPhotoCaptured(correctedBitmap)

                            image.close()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Log.e("CameraContent", "Error capturing image", exception)
                        }
                    })
            })
    }) { paddingValues: PaddingValues ->
        AndroidView(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .heightIn(30.dp),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    setBackgroundColor(BLACK)
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            })
    }
}

@Preview
@Composable
private fun Preview_CameraContent() {
    CameraContent(onPhotoCaptured = {})
}

@Composable
fun NoPermissionScreen(
    onRequestPermission: () -> Unit
) {
    NoPermissionContent(
        onRequestPermission = onRequestPermission
    )
}

@Composable
fun NoPermissionContent(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Please grant the permission to use the camera to use the core functionality of this app.")
        Button(onClick = onRequestPermission) {
            Icon(imageVector = Icons.Default.Camera, contentDescription = "Camera")
            Text(text = "Grant permission")
        }
    }
}

@Preview
@Composable
private fun Preview_NoPermissionContent() {
    NoPermissionContent(onRequestPermission = {})
}