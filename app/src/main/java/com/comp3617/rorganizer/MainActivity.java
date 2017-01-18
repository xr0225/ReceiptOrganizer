package com.comp3617.rorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private final int CAMERA_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fr;
        String tag;
        int titleRes;
        tag = ImageGridFragment.class.getSimpleName();
        fr = getSupportFragmentManager().findFragmentByTag(tag);
        if (fr == null) {
            fr = new ImageGridFragment();
            fr.setArguments(getIntent().getExtras());
        }

        titleRes = R.string.app_name;
        setTitle(titleRes);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
        MenuFragment.IMAGE_URLS = getimages();
        MenuFragment.IMAGE_URL = getimagePath();
        ImageLoader.getInstance()
                .init(ImageLoaderConfiguration.createDefault(MainActivity.this));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public static String[] getimages() {
        String[] images=new String[175];
        String ExternalStorageDirectoryPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)+ File.separator;

        String targetPath = ExternalStorageDirectoryPath + "MyCameraApp/";
        int counter=0;
        String name;
        File yourDir = new File(ExternalStorageDirectoryPath, "MyCameraApp/");
        for (File f : yourDir.listFiles()) {
            if (f.isFile()){
                name = "file:///"+targetPath + f.getName().toString() ;
                images[counter]=name;
                // make something with the name
                counter++;}
        }
        return images;
    }
    public static String[] getimagePath() {
        String[] images=new String[175];
        String ExternalStorageDirectoryPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)+ File.separator;

        String targetPath = ExternalStorageDirectoryPath + "MyCameraApp/";
        int counter=0;
        String name;
        File yourDir = new File(ExternalStorageDirectoryPath, "MyCameraApp/");
        for (File f : yourDir.listFiles()) {
            if(f!=null) {
                if (f.isFile()) {
                    name = targetPath + f.getName().toString();
                    images[counter] = name;
                    // make something with the name
                    counter++;
                }
            }
        }
        return images;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String action = "";
        switch (item.getItemId()) {
            case R.id.action_create:
                Intent intent = new Intent(this, CameraAction.class);
                this.startActivityForResult(intent, 100);
                break;
            case R.id.action_refresh:
                ImageLoader.getInstance()
                        .init(ImageLoaderConfiguration.createDefault(MainActivity.this));
                Intent re = getIntent();
                finish();
                startActivity(re);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        //Toast.makeText(this, action, Toast.LENGTH_LONG).show();
        return true;
    }


}
