package com.techno.minilauncher.screens;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.techno.minilauncher.R;
import com.techno.minilauncher.global.Constants;
import com.techno.minilauncher.global.Global;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout llHomeBottom, llHome;
    SimpleDraweeView sdvWallpaper;
    public static final int REQUEST_SET_WALLPAPER = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            bindHere();
            loadWallpaper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindHere() {
        try {
            sdvWallpaper = findViewById(R.id.sdvWallpaper);

            findViewById(R.id.btnContact).setOnClickListener(this);
            findViewById(R.id.btnDialer).setOnClickListener(this);
            findViewById(R.id.btnApps).setOnClickListener(this);
            findViewById(R.id.btnMessage).setOnClickListener(this);
            findViewById(R.id.btnCamera).setOnClickListener(this);

            findViewById(R.id.btnWallpaper).setOnClickListener(this);
            findViewById(R.id.btnAppChange).setOnClickListener(this);
            findViewById(R.id.btnSettings).setOnClickListener(this);

            llHomeBottom = findViewById(R.id.llHomeBottom);
            llHome = findViewById(R.id.llHome);
            llHome.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    llHomeBottom.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnContact:
                    Intent contactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(contactIntent, 1);

                    break;
                case R.id.btnDialer:
                    Intent dialerIntent = new Intent(Intent.ACTION_DIAL);
                    // dialerIntent .setData(Uri.parse("tel:0123456789"));//dialer open if pass number
                    startActivity(dialerIntent);
                    break;
                case R.id.btnApps:
                    Intent listIntent = new Intent(this, AppListActivity.class);
                    startActivity(listIntent);
                    break;
                case R.id.btnMessage:
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms:"));
                    startActivity(sendIntent);
                    break;
                case R.id.btnCamera:
                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(cameraIntent);
                    break;
                case R.id.btnWallpaper:
                    Intent wallpaperIntent = new Intent(this, GalleryActivity.class);
                    startActivityForResult(wallpaperIntent, REQUEST_SET_WALLPAPER);
                    break;
                case R.id.btnAppChange:

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        final Intent intent = new Intent(Settings.ACTION_HOME_SETTINGS);
                        startActivity(intent);
                    } else {
                        final Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                    llHomeBottom.setVisibility(View.GONE);
                    break;
                case R.id.btnSettings:
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    llHomeBottom.setVisibility(View.GONE);
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        try {
            if (llHomeBottom.getVisibility() == View.VISIBLE) {
                llHomeBottom.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_SET_WALLPAPER) {
                    try {
                        String imgUrl = data.getStringExtra(Constants.INTENT_WALLPAPER);
                        updateWallpaper(imgUrl);
                        llHomeBottom.setVisibility(View.GONE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void loadWallpaper() {
        String loadUrl = Global.getPreference(Constants.SELECTED_WALLPAPER, "");
        if (!loadUrl.isEmpty() && !loadUrl.equals("")) ;
        {
            updateWallpaper(loadUrl);
        }
    }

    public void updateWallpaper(String imgUrl) {
        try {
            Uri uri = Uri.parse(imgUrl);
            sdvWallpaper.setImageURI(uri);
            if (!imgUrl.isEmpty() && !imgUrl.equals("")) ;
            {
                Global.storePreference(Constants.SELECTED_WALLPAPER, imgUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
