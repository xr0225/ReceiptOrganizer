package com.comp3617.rorganizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

public class PagerFrag extends AppCompatActivity {
    private static final int EMAIL_CODE = 200;
    static ActionBar actionBar;
    private static String name;
    private static String substr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        name = MenuFragment.IMAGE_URLS[ImagePagerFragment.currentPage];
        substr= name.substring(name.indexOf("2016"), name.indexOf(".jpg"));
        actionBar.setTitle(substr);

        setContentView(R.layout.activity_pager_frag);
        Fragment fr;
        String tag;
        int titleRes;

        tag = ImagePagerFragment.class.getSimpleName();
        fr = getSupportFragmentManager().findFragmentByTag(tag);
        if (fr == null) {
            fr = new ImagePagerFragment();
            fr.setArguments(getIntent().getExtras());
        }
        titleRes = R.string.app_name;
        setTitle(titleRes);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.email_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
     public boolean onOptionsItemSelected(MenuItem item) {
        String action = "";
        switch (item.getItemId()) {
            case R.id.action_email:

                int index = ImagePagerFragment.currentPage;
                String name = MenuFragment.IMAGE_URL[index];
                if(name == null){
                    Toast.makeText(this, "No file!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                //Toast.makeText(PagerFrag.this, String.valueOf(index), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"thebelldandy@gmail.com"});
                i.putExtra(Intent.EXTRA_TEXT, "This receipt was issued on "+substr);
                i.putExtra(Intent.EXTRA_SUBJECT, "Receipt Notice");
                File file = new File(name);
                if (!file.exists() || !file.canRead()) {
                    Toast.makeText(this, "Attachment Error", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Uri uri = Uri.parse("file://" + name);
                i.putExtra(Intent.EXTRA_STREAM, uri);
                try {
                    startActivityForResult(Intent.createChooser(i, "Send mail..."), EMAIL_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(PagerFrag.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        //Toast.makeText(this, action, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EMAIL_CODE){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
