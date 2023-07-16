package com.esm.appsearchsample;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.SearchResult;
import android.app.appsearch.SearchSpec;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements GlobalSearchListAdapter.OnSearchListener {
    private Executor mExecutor;
    private Context context;
    private String appName = "";
    private String appName2 = "";
    private ArrayList<GlobalSearchListData> myListData = new ArrayList<>();
    private GlobalSearchListAdapter adapter = new GlobalSearchListAdapter(myListData, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplication();

        EditText edt_search = findViewById(R.id.edt_search);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        recyclerView.setAdapter(adapter);


        SearchSpec searchSpec = new SearchSpec.Builder().build();

        mExecutor = this.getMainExecutor();

        AppSearchManager appSearchManager = this.getSystemService(AppSearchManager.class);


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                myListData.clear();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    myListData.clear();
                    adapter.notifyDataSetChanged();

                }

                if (count != 0) {
                    try {
                        appSearchManager.createGlobalSearchSession(mExecutor, globalSearchSessionAppSearchResult -> {
                            globalSearchSessionAppSearchResult.getResultValue().search(s.toString(), searchSpec).getNextPage(mExecutor,
                                    listAppSearchResult -> {

                                        List<SearchResult> listAppSearchResult2 = listAppSearchResult.getResultValue();

                                        for (int x = 0; x < listAppSearchResult.getResultValue().size(); x++) {
                                            if (listAppSearchResult2.get(x).getGenericDocument().getSchemaType().equals("Shortcut")) {
                                                try {
                                                    if (
                                                            listAppSearchResult2.get(x).getGenericDocument().getPropertyString("shortLabel") != null &
                                                                    listAppSearchResult2.get(x).getGenericDocument().getPropertyString("intents") != null
                                                    ) {
                                                        Resources res = getApplication().getPackageManager().getResourcesForApplication(listAppSearchResult2.get(x).getGenericDocument().getNamespace());
                                                        int img1 = (int) listAppSearchResult2.get(x).getGenericDocument().getPropertyLong("iconResId");
                                                        int img2 = R.drawable.border;
                                                        int y;
                                                        Drawable d;
                                                        if (img1 != 0) {
                                                            y = img1;
                                                            d = res.getDrawable(y);
                                                        } else {
                                                            y = img2;
                                                            d = getDrawable(y);
                                                        }
                                                        appName = AppUtils.getAppNameFromPkgName(context, listAppSearchResult2.get(x).getGenericDocument().getNamespace());
                                                        if (x >= 1) {
                                                            appName2 = AppUtils.getAppNameFromPkgName(context, listAppSearchResult2.get(x - 1).getGenericDocument().getNamespace());
                                                            if (appName == appName2) {
                                                                appName = "";
                                                            }
                                                        }
                                                        myListData.add(new GlobalSearchListData(
                                                                listAppSearchResult2.get(x).getGenericDocument().getPropertyString("shortLabel"),
                                                                d,
                                                                listAppSearchResult2.get(x).getGenericDocument().getPropertyStringArray("intents"),
                                                                appName
                                                        ));
                                                    }

                                                } catch (
                                                        PackageManager.NameNotFoundException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                adapter.notifyDataSetChanged();


                                            } else if (listAppSearchResult2.get(x).getGenericDocument().getSchemaType().equals("builtin:Person")) {
                                                myListData.add(new GlobalSearchListData(
                                                        listAppSearchResult2.get(x).getGenericDocument().getPropertyString("givenName"),
                                                        ContextCompat.getDrawable(getApplication(), R.drawable.ic_launcher_background),
                                                        listAppSearchResult2.get(x).getGenericDocument().getPropertyStringArray("externalUri"),
                                                        "Contact Person"));

                                                adapter.notifyDataSetChanged();


                                            } else if (listAppSearchResult2.get(x).getGenericDocument().getSchemaType().equals("builtin:ContactPoint")) {
                                                Log.e("Test", "SchemaType(builtin:ContactPoint) : " + listAppSearchResult2.get(x).getGenericDocument().getSchemaType());

                                            } else {
                                                Log.e("Test", "SchemaType(Unknown) :  Not yet added to the structure of the program|==> "
                                                        + listAppSearchResult2.get(x).getGenericDocument().getSchemaType());
                                            }
                                        }
                                    });
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.notifyDataSetChanged();
                myListData.clear();
            }
        });

        findViewById(R.id.img_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSearchClick(int position) {
        Intent intent;
        try {
            intent = Intent.parseUri(myListData.get(position).getIntent()[0], 0);
            startActivity(intent);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(this, "click on item: " + myListData.get(position).getDescription(), Toast.LENGTH_LONG).show();
    }
}