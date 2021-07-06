package com.carlolj.likestagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("DBX5KKHlCKaa6X2IYNUXW4r9PFhLnKRJ5snec5jJ")
                .clientKey("VUbNe2hAOigpPjRnW73V83UGVoejyy99zQik27av")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
