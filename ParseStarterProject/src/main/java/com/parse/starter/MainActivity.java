/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Parse Documentation
    // http://docs.parseplatform.org/android/guide/#the-parseobject

    // Create new Class. Can think of this as a table
    ParseObject score = new ParseObject("Score");

    // Create new columns, "score" and "username"
    score.put("score", 7);
    score.put("username", "John");

    // Save to Parse Server
    score.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if(e == null) {
                Log.i(TAG, "done: score succesfully added");
            } else {
                e.printStackTrace();
            }
        }
    });

      // Retrieve data from Parse server
      // Can think of this statement as rawQuery(SELECT * FROM Score)
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

      // Note: the objectId (first param) is taken from Parse Server
      query.getInBackground("98rpbljzaZ", new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {

              if(e == null && object != null) {

                  // Update username
                  object.put("username", "Alex");
                  object.saveInBackground();

                  object.getString("username");
                  Log.i(TAG, "done: username: " + object.getString("username"));
                  Log.i(TAG, "done: score: " + object.getInt("score"));

              } else {
                // Error
              }
          }
      });

    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}