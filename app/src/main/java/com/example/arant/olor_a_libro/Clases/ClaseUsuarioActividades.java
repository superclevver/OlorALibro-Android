package com.example.arant.olor_a_libro.Clases;

import java.util.ArrayList;

public class ClaseUsuarioActividades {


    private String nombre;
    private int edad;
    private int codigopostal;
    private String correo;
    private String contraseña;
    private ArrayList actividades;


    @Override
    public String toString() {
        return "ClaseUsuario{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", codigopostal=" + codigopostal +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", actividades=" + actividades +
                '}';
    }


    public ClaseUsuarioActividades(String nombre, int edad, int codigopostal, String correo, String contraseña, ArrayList actividades )
    {
        this.nombre = nombre;
        this.edad = edad;
        this.codigopostal = codigopostal;
        this.correo = correo;
        this.contraseña = contraseña;
        this.actividades = actividades;
    }
    public ClaseUsuarioActividades(String nombre, int edad, int codigopostal, String correo, String contraseña)
    {
        this.nombre = nombre;
        this.edad = edad;
        this.codigopostal = codigopostal;
        this.correo = correo;
        this.contraseña = contraseña;
    }
    public  ClaseUsuarioActividades(){}
    public String getNombre()
    {
        return nombre;
    }

    public int getEdad()
    {
        return edad;
    }

    public int getCodigopostal()
    {
        return codigopostal;
    }

    public String getCorreo()
    {
        return correo;
    }

    public String getContraseña()
    {
        return contraseña;
    }


    public ArrayList getActividades() {
        return actividades;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setCodigopostal(int codigopostal) {
        this.codigopostal = codigopostal;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setActividades(ArrayList actividades) {
        this.actividades = actividades;
    }
}



