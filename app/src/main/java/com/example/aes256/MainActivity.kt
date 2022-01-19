package com.example.aes256

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var keygenerator : KeyGenerator
        var secretKey : SecretKey

        val textinput = findViewById<EditText>(R.id.TextToEncode).text
        val encodebtn = findViewById<Button>(R.id.encrypt)
        val decodebtn = findViewById<Button>(R.id.decrypt)
        val decodeoutput = findViewById<TextView>(R.id.decodedview)
        val encodedoutput = findViewById<TextView>(R.id.encodedview)


        var encryptedtext : ByteArray? = null
        var encryptText : String? = null

        keygenerator = KeyGenerator.getInstance("AES")
        keygenerator.init(256)
        secretKey = keygenerator.generateKey()

        encodebtn.setOnClickListener {
            val bytes: ByteArray = textinput.toString().toByteArray(Charset.defaultCharset())
            encryptedtext = encrypt(bytes, secretKey)
            encryptText = String(encryptedtext!!, Charset.defaultCharset())
            encodedoutput.setText(encryptText)

        }

        decodebtn.setOnClickListener {
            try {
                var decrypt : ByteArray = decrypt(encryptedtext!!, secretKey)
                decodeoutput.setText(String(decrypt, Charset.defaultCharset()))
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun decrypt(encryptedtext: ByteArray, key: SecretKey): ByteArray {

        val cipher = Cipher.getInstance("AES")
        val SecretKeySpec = SecretKeySpec(key.getEncoded(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec)
        var decryptedText : ByteArray = cipher.doFinal(encryptedtext)
        return decryptedText

    }




    private fun encrypt(text: ByteArray, Key: SecretKey): ByteArray {

        val cipher = Cipher.getInstance("AES")
        val keySpec = SecretKeySpec(Key.getEncoded(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        return cipher.doFinal(text)

    }
}