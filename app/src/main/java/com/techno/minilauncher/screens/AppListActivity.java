package com.techno.minilauncher.screens;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.techno.minilauncher.adapter.CustomAppListAdapter;
import com.techno.minilauncher.model.AppListMain;
import com.techno.minilauncher.R;
import com.techno.minilauncher.listener.RecyclerItemClickListener;
import com.techno.minilauncher.ui.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppListActivity extends AppCompatActivity {
    private RecyclerView rvAppList;
    private PackageManager packageManager;
    private CustomAppListAdapter customAppListAdapter;
    private ArrayList<AppListMain> appListMainArrayList;
    private AppListMain appListMain;
    public static final int REQUEST_UNINSTALL = 222;
    int selectedPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        loadApps();
        loadListView();

    }

    public void loadApps() {
        packageManager = getPackageManager();
        appListMainArrayList = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList) {
            AppListMain appListMain = new AppListMain();
            appListMain.setAppIcon(resolveInfo.activityInfo.loadIcon(packageManager));
            appListMain.setAppName(resolveInfo.loadLabel(packageManager));
            appListMain.setAppPackage(resolveInfo.activityInfo.packageName);
            appListMainArrayList.add(appListMain);
        }
    }

    public void loadListView() {
        int mColumnCount = 4;
        rvAppList = findViewById(R.id.rvAppList);
        rvAppList.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        rvAppList.addItemDecoration(new GridSpacingItemDecoration(10)); // 16px. In practice, you'll want to use getDimensionPixelSize

        Collections.sort(appListMainArrayList, new Comparator<AppListMain>() {
            @Override
            public int compare(AppListMain lhs, AppListMain rhs) {
                return lhs.getAppName().toString().compareTo(rhs.getAppName().toString());
            }

        });
        customAppListAdapter = new CustomAppListAdapter(this, appListMainArrayList);
        rvAppList.setAdapter(customAppListAdapter);

        rvAppList.addOnItemTouchListener(new RecyclerItemClickListener(this, rvAppList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                appListMain = appListMainArrayList.get(position);
                if (appListMain != null) {
                    Intent intent = packageManager.getLaunchIntentForPackage(appListMainArrayList.get(position).getAppPackage().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                appListMain = appListMainArrayList.get(position);
                if (appListMain != null) {
                   /* Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:"+appListMain.getAppPackage()));
                    startActivity(intent);
                    customAppListAdapter.notifyDataSetChanged();*/
                    selectedPos = position;
                    Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
                    intent.setData(Uri.parse("package:" + appListMain.getAppPackage()));
                    intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                    startActivityForResult(intent, REQUEST_UNINSTALL);

                }
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UNINSTALL) {
            if (resultCode == RESULT_OK) {
                Log.d("TAG", "onActivityResult: user accepted the (un)install");
//                customAppListAdapter.updateList(appListMainArrayList,selectedPos);
                customAppListAdapter.notifyItemRemoved(selectedPos);
                Toast.makeText(this, "Uninstall successfully!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("TAG", "onActivityResult: user canceled the (un)install");
               // Toast.makeText(this, "System can't uninstall!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_FIRST_USER) {
                Log.d("TAG", "onActivityResult: failed to (un)install");
            }
        }
    }

}
