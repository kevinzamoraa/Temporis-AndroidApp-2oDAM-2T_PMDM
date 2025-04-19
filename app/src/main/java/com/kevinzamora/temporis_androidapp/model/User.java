package com.kevinzamora.temporis_androidapp.model;

import java.io.Serializable;

public class User implements Serializable {

        private String username, displayName, email,password,profilePhotoUrl,QrCode,uid,description;
        private int rol;


        public User(String username, String displayName, String email, String password, String profilePhotoUrl, String uid,int rol) {
            this.username = username;
            this.displayName = displayName;
            this.email = email;
            this.password = password;
            this.profilePhotoUrl = profilePhotoUrl;
            this.uid = uid;
            this.rol=rol;
        }

        public User(String username, String displayName, String email, String password,String uid, int rol) {
            this.username = username;
            this.displayName = displayName;
            this.uid= uid;
            this.email = email;
            this.password = password;
            this.rol = rol;
        }

        public User(String username, String displayName, String profilePhotoUrl, String description, int rol, String uid) {
            this.username = username;
            this.displayName = displayName;
            this.profilePhotoUrl = profilePhotoUrl;
            this.description = description;
            this.rol= rol;
            this.uid=uid;
        }

    public User(String uid, String username, String email, String displayName, String description, String profilePhotoUrl) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.description = description;
        this.profilePhotoUrl = profilePhotoUrl;
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
            return username;
        }

        public void setName(String username) {
            this.username = username;
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
            return profilePhotoUrl;
        }

        public void setProfilePhoto(String profilePhotoUrl) {
            this.profilePhotoUrl = profilePhotoUrl;
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
                    "username='" + username + '\'' +
                    "displayName='" + displayName + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", profilePhotoUrl='" + profilePhotoUrl + '\'' +
                    ", QrCode='" + QrCode + '\'' +
                    ", uid=" + uid +
                    ", rol=" + rol +
                    '}';
        }
    }

