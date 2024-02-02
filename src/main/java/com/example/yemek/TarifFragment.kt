package com.example.yemek
import android.Manifest
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import java.io.ByteArrayOutputStream

class TarifFragment : Fragment() {
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private lateinit var editTextText:EditText
    private lateinit var editTextText2:EditText
    var secilengorsel: Uri?=null
    var secilenBitmap:Bitmap?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tarif, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.button) // Button'u tanımla
        imageView = view.findViewById(R.id.imageView) // ImageView'u tanımla
        editTextText = view.findViewById<EditText>(R.id.editTextText)
        editTextText2=view.findViewById<EditText>(R.id.editTextText2)

        button.setOnClickListener{
            kaydet(it)
        }
        imageView.setOnClickListener{
            gorselSec(it)
        }

    }

    fun kaydet(view: View){
        //Sqlite kaydetme
        if (secilenBitmap == null) {
            // Kullanıcıya uyarı mesajı göster
            Toast.makeText(context, "Lütfen bir resim seçin.", Toast.LENGTH_SHORT).show()
            return
        }

        val isim = editTextText.text.toString().trim()
        val tarif = editTextText2.text.toString().trim()

        if (isim.isEmpty()) {
            // Kullanıcıya uyarı mesajı göster
            Toast.makeText(context, "Lütfen yemek ismini doldurun.", Toast.LENGTH_SHORT).show()
            return
        }

        if (tarif.isEmpty()) {
            // Kullanıcıya uyarı mesajı göster
            Toast.makeText(context, "Lütfen yemek tarifini doldurun.", Toast.LENGTH_SHORT).show()
            return
        }

        // Veritabanına kaydetme işlemleri
        val dbHelper = DBHelper(requireContext())
        val yemekIsmi = isim
        val yemekTarifi = tarif
        val yemekResim = bitmapToByteArray(shrinkBitmap(secilenBitmap!!))

        dbHelper.addYemek(yemekIsmi, yemekTarifi, yemekResim)

        // Başarı mesajı göster
        Toast.makeText(context, "Yemek başarıyla kaydedildi.", Toast.LENGTH_SHORT).show()

    }

    fun gorselSec(view: View) {
        activity?.let {
            if (checkSelfPermission(
                    it.applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // İzin verilmedi, izin istememiz gerekiyor
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                // İzin verildi, galeriyi aç
                val galeriIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent, 2)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // İzni aldık
                val galeriIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            secilengorsel = data.data
            try {
                context?.let {
                    if (secilengorsel != null) {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(it.contentResolver, secilengorsel!!)
                            secilenBitmap = ImageDecoder.decodeBitmap(source)
                            imageView.setImageBitmap(secilenBitmap)
                        } else {
                            secilenBitmap =
                                MediaStore.Images.Media.getBitmap(it.contentResolver, secilengorsel)
                            imageView.setImageBitmap(secilenBitmap)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun shrinkBitmap(bitmap: Bitmap): Bitmap {
        val targetWidth = bitmap.width / 2
        val targetHeight = bitmap.height / 2
        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
    }
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

}