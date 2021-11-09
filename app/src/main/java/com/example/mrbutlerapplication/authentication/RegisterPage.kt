package com.example.mrbutlerapplication.authentication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mrbutlerapplication.R
import com.example.mrbutlerapplication.model.CurrentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class RegisterPage : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var uploadPhotoBtn: Button
    private lateinit var selectPhotoImageView: CircleImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        username = findViewById(R.id.username_edittext_register)
        email = findViewById(R.id.email_edittext_register)
        password = findViewById(R.id.password_edittext_register)
        uploadPhotoBtn = findViewById(R.id.upload_photo_button)
        selectPhotoImageView = findViewById(R.id.select_photo_image_view)


    }

    fun uploadPhoto(view: View?){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    var selectedPhotoUri: Uri? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            //proceed and check what the selected image was
            Log.d("RegisterPage", "Photo was selected")

            selectedPhotoUri = data.data

            var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedPhotoUri)
            selectPhotoImageView.setImageBitmap(bitmap)
            uploadPhotoBtn.alpha = 0f

        }
    }


    fun register(view:View?){
        Log.d("MainActivity", "Email is: " + email.text.toString())
        Log.d("MainActivity","Password is: " + password.text.toString())

        if (email.text.toString() == "" || password.text.toString() == ""){
            Toast.makeText(this@RegisterPage, "Please enter text in email and password!",Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener{
               if(!it.isSuccessful) return@addOnCompleteListener

                uploadImageToFirebaseStorage()

                Toast.makeText(this@RegisterPage, "User successfully registered!.",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@RegisterPage, "Failed to create user: ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {

        if(selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("Register", "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", "File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                // do some logging here
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = CurrentUser(username.text.toString(),profileImageUrl,email.text.toString(),uid)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Finally we saved the user to Firebase Database")
            }
    }


    fun showLogin(view:View?){
        val intent = Intent(this@RegisterPage, LoginPage::class.java)
        startActivity(intent)
    }
}