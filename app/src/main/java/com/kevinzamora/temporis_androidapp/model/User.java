package com.kevinzamora.temporis_androidapp.model;

import java.io.Serializable;

public class User implements Serializable {

        private String name, displayName, email,password,profilePhoto,QrCode,uid,description;
        private int rol;


        public User(String name, String displayName, String email, String password, String profilePhoto, String uid,int rol) {
            this.name = name;
            this.displayName = displayName;
            this.email = email;
            this.password = password;
            this.profilePhoto = profilePhoto;
            this.uid = uid;
            this.rol=rol;
        }

        public User(String name, String displayName, String email, String password,String uid, int rol) {
            this.name = name;
            this.displayName = displayName;
            this.uid= uid;
            this.email = email;
            this.password = password;
            this.rol = rol;
        }

        public User(String name, String displayName, String profilePhoto, String description, int rol, String uid) {
            this.name = name;
            this.displayName = displayName;
            this.profilePhoto = profilePhoto;
            this.description = description;
            this.rol= rol;
            this.uid=uid;
        }


        public User(){

        }

        public String getDescripcion() {
            return description;
        }

        public void setDescripcion(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getProfilePhoto() {
            return profilePhoto;
        }

        public void setProfilePhoto(String profilePhoto) {
            this.profilePhoto = profilePhoto;
        }

        public String getQrCode() {
            return QrCode;
        }

        public void setQrCode(String QrCode) {
            this.QrCode = QrCode;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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
                    "name='" + name + '\'' +
                    "displayName='" + displayName + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", profilePhoto='" + profilePhoto + '\'' +
                    ", QrCode='" + QrCode + '\'' +
                    ", uid=" + uid +
                    ", rol=" + rol +
                    '}';
        }
    }

