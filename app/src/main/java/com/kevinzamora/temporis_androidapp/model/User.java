package com.kevinzamora.temporis_androidapp.model;

import java.io.Serializable;

public class User implements Serializable {

        private String nickName,email,password,fotoPerfil,codigoQr,idUser,descripcion;
        private int rol;


        public User(String nickName, String email, String password, String fotoPerfil, String idUser,int rol) {
            this.nickName = nickName;
            this.email = email;
            this.password = password;
            this.fotoPerfil = fotoPerfil;
            this.idUser = idUser;
            this.rol=rol;
        }

        public User(String nickName, String email, String password,String idUser, int rol) {
            this.nickName = nickName;
            this.idUser= idUser;
            this.email = email;
            this.password = password;
            this.rol = rol;
        }

        public User(String nickName, String fotoPerfil, String descripcion, int rol, String idUser) {
            this.nickName = nickName;
            this.fotoPerfil = fotoPerfil;
            this.descripcion = descripcion;
            this.rol= rol;
            this.idUser=idUser;
        }


        public User(){

        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFotoPerfil() {
            return fotoPerfil;
        }

        public void setFotoPerfil(String fotoPerfil) {
            this.fotoPerfil = fotoPerfil;
        }

        public String getCodigoQr() {
            return codigoQr;
        }

        public void setCodigoQr(String codigoQr) {
            this.codigoQr = codigoQr;
        }

        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public int getRol() {
            return rol;
        }

        public void setRol(int rol) {
            this.rol = rol;
        }

        @Override
        public String toString() {
            return "User{" +
                    "nickName='" + nickName + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", fotoPerfil='" + fotoPerfil + '\'' +
                    ", codigoQr='" + codigoQr + '\'' +
                    ", idUser=" + idUser +
                    ", rol=" + rol +
                    '}';
        }
    }

