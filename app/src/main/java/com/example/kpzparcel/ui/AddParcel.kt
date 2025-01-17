package com.example.kpzparcel.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.kpzparcel.R
import com.example.kpzparcel.viewmodel.ParcelViewModel
import com.example.kpzparcel.data.Parcel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kpzparcel.ui.theme.KPZParcelTheme
import java.io.ByteArrayOutputStream
import java.util.Date
import com.journeyapps.barcodescanner.ScanOptions
import com.journeyapps.barcodescanner.ScanContract
import java.io.File

@Composable
fun AddParcelForm(viewModel: ParcelViewModel = viewModel(), onAddComplete: () -> Unit) {
    var customerName by remember { mutableStateOf("") }
    var trackingNumber by remember { mutableStateOf("") }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val showImageOptionsDialog = remember { mutableStateOf(false) }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(context.contentResolver, uri)
                )
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
            selectedImageBitmap = bitmap
        }
    }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri.value?.let { uri ->
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(context.contentResolver, uri)
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                }
                selectedImageBitmap = bitmap
            }
        }
    }

    // QR scanner launcher
    val qrScannerLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->
        if (result.contents != null) {
            trackingNumber = result.contents
        }
    }

    // Temporary URI for the captured image
    val tempImageUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            File(context.cacheDir, "temp_image.jpg")
        )
    }

    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = "Add Parcel",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 50.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 50.sp,
                ),
                modifier = Modifier.padding(10.dp)
            )

            // Image section
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .padding(16.dp)
            ) {
                selectedImageBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Selected Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp)
                    )
                } ?: run {
                    Image(
                        painter = painterResource(R.drawable.box),
                        contentDescription = "Default Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            // Dialog to choose whether to open gallery or take a picture
            if (showImageOptionsDialog.value) {
                AlertDialog(
                    onDismissRequest = { showImageOptionsDialog.value = false },
                    title = { Text("Select Image Option") },
                    text = {
                        Column {
                            TextButton(onClick = {
                                imagePickerLauncher.launch("image/*")
                                showImageOptionsDialog.value = false
                            }) {
                                Text("Choose from Gallery")
                            }
                            TextButton(onClick = {
                                cameraLauncher.launch(tempImageUri)
                                showImageOptionsDialog.value = false
                            }) {
                                Text("Take a Picture")
                            }
                        }
                    },
                    confirmButton = {},
                    dismissButton = {},
                )
            }

            // Single button to show image options
            Button(
                onClick = { showImageOptionsDialog.value = true },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Add Parcel Photo")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Customer Name field
            OutlinedTextField(
                value = customerName,
                onValueChange = { customerName = it },
                label = { Text("Customer Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Tracking Number field
            OutlinedTextField(
                value = trackingNumber,
                onValueChange = { trackingNumber = it },
                label = { Text("Tracking Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

            // QR Code scanner
            Button(
                onClick = {
                    val options = ScanOptions()
                    options.setPrompt("Scan a QR Code")
                    options.setBeepEnabled(true)
                    options.setBarcodeImageEnabled(true)
                    options.setOrientationLocked(true)
                    qrScannerLauncher.launch(options)
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text("Scan Barcode/QR Code")
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Submit button
            Button(
                onClick = {
                    if (customerName.isBlank() || trackingNumber.isBlank() || selectedImageBitmap == null) {
                        errorMessage = "Please fill in all fields."
                    } else {
                        errorMessage = ""
                        val currentDate = Date()
                        val imageByteArray = selectedImageBitmap?.let { bitmap ->
                            val outputStream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                            outputStream.toByteArray()
                        } ?: ByteArray(0)

                        val parcel = Parcel(
                            trackingNumber = trackingNumber,
                            customerName = customerName,
                            date = currentDate,
                            imageByteArray = imageByteArray
                        )
                        viewModel.addParcel(parcel)
                        onAddComplete() // Callback after adding the parcel
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun EditParcelForm(
    parcel: Parcel,
    viewModel: ParcelViewModel = viewModel(),
    onEditComplete: () -> Unit
) {
    var customerName by remember { mutableStateOf(parcel.customerName) }
    var trackingNumber by remember { mutableStateOf(parcel.trackingNumber) }
    var selectedImageBitmap by remember {
        mutableStateOf(parcel.imageByteArray?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        })
    }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Launcher for picking image from gallery
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(context.contentResolver, uri)
                )
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
            selectedImageBitmap = bitmap
        }
    }

    // Launcher for capturing image with the camera
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val bitmap = selectedImageBitmap // bitmap is already updated after capture
            selectedImageBitmap = bitmap
        }
    }

    // Temporary URI for the captured image
    val tempImageUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            File(context.cacheDir, "temp_image.jpg")
        )
    }

    // A single button that allows the user to choose between gallery or camera
    val showImageOptionsDialog = remember { mutableStateOf(false) }

    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = "Edit Parcel",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 50.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 50.sp,
                ),
                modifier = Modifier.padding(10.dp)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .padding(16.dp)
            ) {
                selectedImageBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Selected Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp)
                    )
                } ?: run {
                    Image(
                        painter = painterResource(R.drawable.box),
                        contentDescription = "Default Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            // Dialog to choose whether to open gallery or take a picture
            if (showImageOptionsDialog.value) {
                AlertDialog(
                    onDismissRequest = { showImageOptionsDialog.value = false },
                    title = { Text("Select Image Option") },
                    text = {
                        Column {
                            TextButton(onClick = {
                                imagePickerLauncher.launch("image/*")
                                showImageOptionsDialog.value = false
                            }) {
                                Text("Choose from Gallery")
                            }
                            TextButton(onClick = {
                                takePictureLauncher.launch(tempImageUri)
                                showImageOptionsDialog.value = false
                            }) {
                                Text("Take a Picture")
                            }
                        }
                    },
                    confirmButton = {},
                    dismissButton = {},
                )
            }

            // Single button to show image options
            Button(
                onClick = { showImageOptionsDialog.value = true },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Change Parcel Photo")
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = customerName,
                onValueChange = { customerName = it },
                label = { Text("Customer Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = trackingNumber,
                onValueChange = { trackingNumber = it },
                label = { Text("Tracking Number") },
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (customerName.isBlank() || trackingNumber.isBlank() || selectedImageBitmap == null) {
                        errorMessage = "Please fill in all fields."
                    } else {
                        errorMessage = ""
                        val imageByteArray = selectedImageBitmap?.let { bitmap ->
                            val outputStream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                            outputStream.toByteArray()
                        } ?: ByteArray(0)

                        val updatedParcel = parcel.copy(
                            customerName = customerName,
                            trackingNumber = trackingNumber,
                            imageByteArray = imageByteArray
                        )

                        viewModel.updateParcel(updatedParcel)
                        onEditComplete() // Notify that editing is complete
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Save Changes")
            }
        }
    }
}
