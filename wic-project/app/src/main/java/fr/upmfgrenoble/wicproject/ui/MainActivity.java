package fr.upmfgrenoble.wicproject.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import fr.upmfgrenoble.wicproject.model.GPX;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            InputStream is = getAssets().open("simple.gpx");
            String s = GPX.parse(is).toString();
            Log.v("toto", s);
        } catch (IOException e){
            ;
        }


    }

}
