package com.example.doggallery.model

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

/*Archivo de configuración del inicio de la Base de Datos de Realm*/

/*Versión del Esquema de BD*/
private val realmVersion: Long = 1L


/*Se crea la configuracion inicial de RealmDB*/
fun createRealmConfig(context: Context): RealmConfiguration {
    Realm.init(context)// Inicializa el motor de RealmDB
    return RealmConfiguration.Builder().schemaVersion(realmVersion).build()
}
