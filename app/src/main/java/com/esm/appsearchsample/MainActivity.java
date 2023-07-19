package com.esm.appsearchsample;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.SearchResult;
import android.app.appsearch.SearchSpec;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esm.appsearchsample.adapter.BetterAdapter_Visitor;
import com.esm.appsearchsample.adapter.TypeFactory;
import com.esm.appsearchsample.adapter.TypeFactoryForList;
import com.esm.appsearchsample.adapter.Visitable;
import com.esm.appsearchsample.entities.AppSearchShortcut;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements GlobalSearchListAdapter.OnSearchListener {
    private Executor mExecutor;
    private Context context;
    private String appName = "";
    private Drawable appIcon;
    private String appName2 = "";
//    private ArrayList<GlobalSearchListData> myListData = new ArrayList<>();
    private ArrayList<Visitable>  elementList = new ArrayList<>();

    TypeFactory typeFactory = new TypeFactoryForList();
//    private GlobalSearchListAdapter adapter = new GlobalSearchListAdapter(myListData, this);
    private BetterAdapter_Visitor adapter2 = new BetterAdapter_Visitor(
            elementList,
            typeFactory
    );


    private final Handler mUiHandler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplication();

        EditText edt_search = findViewById(R.id.edt_search);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
//        recyclerView.setAdapter(adapter);

        recyclerView.setAdapter(adapter2);


        SearchSpec searchSpec = new SearchSpec.Builder().build();

        mExecutor = Executors.newSingleThreadExecutor();

        AppSearchManager appSearchManager = this.getSystemService(AppSearchManager.class);


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                myListData.clear();
//                adapter.notifyDataSetChanged();
                elementList.clear();
                adapter2.notifyDataSetChanged();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
//                    myListData.clear();
//                    adapter.notifyDataSetChanged();
                    elementList.clear();
                    adapter2.notifyDataSetChanged();

                }

                if (count != 0) {
                    try {
                        appSearchManager.createGlobalSearchSession(mExecutor, globalSearchSessionAppSearchResult -> {
                            globalSearchSessionAppSearchResult.getResultValue().search(s.toString(), searchSpec).getNextPage(mExecutor,
                                    listAppSearchResult -> {

                                        List<SearchResult> listAppSearchResult2 = listAppSearchResult.getResultValue();
                                        Log.e("ESM", "SIZE :   " + listAppSearchResult.getResultValue().size());

                                        mUiHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                for (int x = 0; x < listAppSearchResult.getResultValue().size(); x++) {

                                                    Log.e("ESM", "GenericDocument(" + x + ") :   " + listAppSearchResult.getResultValue().get(x).getGenericDocument());


                                                    if (listAppSearchResult2.get(x).getGenericDocument().getSchemaType().equals("Shortcut")) {
                                                        try {
                                                            if (
                                                                    listAppSearchResult2.get(x).getGenericDocument().getPropertyString("shortLabel") != null &
                                                                            listAppSearchResult2.get(x).getGenericDocument().getPropertyString("intents") != null
                                                            ) {

                                                                Drawable d = AppUtils.getShortcutIconFromIconResId(context, listAppSearchResult2, x);
                                                                appName = AppUtils.getAppNameFromPkgName(context, listAppSearchResult2.get(x).getGenericDocument().getNamespace());
                                                                appIcon = AppUtils.getAppIconFromPkgName(context, listAppSearchResult2.get(x).getGenericDocument().getNamespace());

                                                                if (x >= 1) {
                                                                    appName2 = AppUtils.getAppNameFromPkgName(context, listAppSearchResult2.get(x - 1).getGenericDocument().getNamespace());
                                                                    if (appName == appName2) {
                                                                        appName = "";
                                                                        appIcon = null;

                                                                    }
                                                                }
//                                                                myListData.add(new GlobalSearchListData(
//                                                                        appIcon,
//                                                                        listAppSearchResult2.get(x).getGenericDocument().getPropertyString("shortLabel"),
//                                                                        d,
//                                                                        listAppSearchResult2.get(x).getGenericDocument().getPropertyStringArray("intents"),
//                                                                        appName
//                                                                ));
                                                                elementList.add(new AppSearchShortcut(
                                                                        appIcon,
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
//                                                        adapter.notifyDataSetChanged();
                                                        adapter2.notifyDataSetChanged();


                                                    } else if (listAppSearchResult2.get(x).getGenericDocument().getSchemaType().equals("builtin:Person") && false) {
//                                                        myListData.add(new GlobalSearchListData(
//                                                                appIcon,
//                                                                listAppSearchResult2.get(x).getGenericDocument().getPropertyString("givenName"),
//                                                                ContextCompat.getDrawable(getApplication(), R.drawable.ic_launcher_background),
//                                                                listAppSearchResult2.get(x).getGenericDocument().getPropertyStringArray("externalUri"),
//                                                                "Contact Person"));

//                                                        adapter.notifyDataSetChanged();


                                                    } else if (listAppSearchResult2.get(x).getGenericDocument().getSchemaType().equals("builtin:ContactPoint") && false) {
                                                        Log.e("Test", "SchemaType(builtin:ContactPoint) : " + listAppSearchResult2.get(x).getGenericDocument().getSchemaType());

                                                    } else {
                                                        Log.e("Test", "SchemaType(Unknown) :  Not yet added to the structure of the program|==> "
                                                                + listAppSearchResult2.get(x).getGenericDocument().getSchemaType());
                                                    }

                                                }

                                            }
                                        });

                                    });
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                adapter.notifyDataSetChanged();
//                myListData.clear();
                adapter2.notifyDataSetChanged();
                elementList.clear();
            }
        });


        findViewById(R.id.img_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSearchClick(int position) {
        Intent intent;
//        try {
//            intent = Intent.parseUri(myListData.get(position).getIntent()[0], 0);
//            startActivity(intent);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        Toast.makeText(this, "click on item: " + myListData.get(position).getDescription(), Toast.LENGTH_LONG).show();
    }
}