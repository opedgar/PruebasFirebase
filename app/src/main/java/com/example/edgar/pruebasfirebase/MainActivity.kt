package com.example.edgar.pruebasfirebase

import android.drm.DrmStore
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.scalified.fab.ActionButton

class MainActivity : AppCompatActivity() {

    //Variables de inicialización de la base de datos firebase
    lateinit var database : FirebaseDatabase
    lateinit var userRef : DatabaseReference
    lateinit var habitRef : DatabaseReference

    //Variables de autentificación a la base de datos
    lateinit var mAuth : FirebaseAuth

    //Variables correspondientes a elementos del diseño
    lateinit var actionButton : ActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicialiciar base de datos y referencias
        database = FirebaseDatabase.getInstance()
        userRef = database.getReference("users")
        habitRef = database.getReference("habits")

        //Inicializar variables de autentificación a la base de datos
        mAuth = FirebaseAuth.getInstance()

        //Insertar usuarios y hábitos de prueba
        añadirUsuario("okenobi", "obi wan", "kenobi" )
        añadirUsuario("harambe", "harambe", "harambe")
        añadirHabito("harambe", "matar", "matar niños")

        //Configurar el floatingActionButton
        actionButton = findViewById<ActionButton>(R.id.actionButton)
        configurarFloatingActionButton()
    }

    //Al iniciar la actividad, comprobar que el usuario ya ha iniciado sesión
    override fun onStart() {
        super.onStart()
        //Comprobar que el usuario haya iniciado sesión
        //(!=null) y actualizar el interfaz en consecuiencia
        var currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    //Añadir o actualizar usuario de la base de datos
    fun añadirUsuario(id:String, name:String, surname:String){
        val newUserOrUpdate = userRef.child(id)
        val userData = HashMap<String,String>()
        userData.put("name", name)
        userData.put("surname", surname)
        newUserOrUpdate.setValue(userData)
    }

    //Añadir o actualizar habito
    fun añadirHabito(userId:String, id: String, name: String){
        val userHabitRef = database.getReference("users/"+userId+"/habits/"+id)
        userHabitRef.setValue(" ")
        val newHabitOrUpdate = habitRef.child(id)
        val habitData = HashMap<String,String>()
        habitData.put("name", name)
        habitData.put("owner", userId)
        newHabitOrUpdate.setValue(habitData)
    }

    //Dar forma y color al floatingActionButton
    fun configurarFloatingActionButton(){
        actionButton.setButtonColor(getResources().getColor(R.color.fab_material_indigo_500))
        actionButton.strokeColor = getResources().getColor(R.color.fab_material_white)
        actionButton.setButtonColorPressed(resources.getColor(R.color.fab_material_indigo_900))
        actionButton.setStrokeWidth(1.5f)
        actionButton.setImageResource(R.drawable.fab_plus_icon)
    }

    //Actualizar el interfaz dependiendo de si el usuario está conectado o no
    fun updateUI (currentUser : FirebaseUser?) {
        //TODO
    }
}
