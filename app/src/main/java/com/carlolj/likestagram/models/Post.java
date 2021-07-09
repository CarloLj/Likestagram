package com.carlolj.likestagram.models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.io.File;
import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {

    public Post(){ }

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "username";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_LIKES = "likes";

    public String getDescription(){
        //The class getString is a method defined in ParseObject which will look inside and pull out the attribute of description
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION,description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }

    public int getLikes(){
        return getInt(KEY_LIKES);
    }
}

