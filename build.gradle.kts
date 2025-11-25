// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // CORRECCIÓN: Usamos 'id' con la sintaxis de versión explícita.
    // Esto resuelve los errores 'Unresolved reference to version catalog' y 'google'.
    id("com.google.gms.google-services") version "4.4.4" apply false
}