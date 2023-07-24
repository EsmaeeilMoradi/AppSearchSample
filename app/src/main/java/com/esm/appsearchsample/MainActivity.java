package com.esm.appsearchsample;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.GenericDocument;
import android.app.appsearch.SearchResult;
import android.app.appsearch.SearchSpec;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Executor mExecutor;
    private Context context;
    private SearchSpec searchSpec;
    private AppSearchManager appSearchManager;
    private GenericDocument searchResultsDoc;
    private List<SearchResult> listAppSearchResultIterable;
    private String appName = "";

    private EditText editTextGlobalSearch;
    private RecyclerView recyclerViewGlobalSearch;
    private ArrayList<GlobalSearchData> globalSearchDataList;
    private GlobalSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get searchResults by createGlobalSearchSession from AppSearchManager
        searchSpec = new SearchSpec.Builder().build();
        context = this;
        mExecutor = context.getMainExecutor();
        appSearchManager = context.getSystemService(AppSearchManager.class);

        editTextGlobalSearch = findViewById(R.id.edt_search);
        recyclerViewGlobalSearch = findViewById(R.id.rv_search_result);
        globalSearchDataList = new ArrayList<>();

        adapter = new GlobalSearchAdapter(globalSearchDataList);
        recyclerViewGlobalSearch.setHasFixedSize(true);
        recyclerViewGlobalSearch.setLayoutManager(new LinearLayoutManager(getApplication()));
        recyclerViewGlobalSearch.setAdapter(adapter);

        editTextGlobalSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                globalSearchDataList.clear();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (count == 0) {
                    globalSearchDataList.clear();
                    adapter.notifyDataSetChanged();
                }
                if (count != 0) {
                    try {
                        appSearchManager.createGlobalSearchSession(mExecutor, globalSearchSessionAppSearchResult -> {
                            Log.e(TAG, "onCreate: check globalSearchSession " + globalSearchSessionAppSearchResult.isSuccess());
                            globalSearchSessionAppSearchResult.getResultValue().search(text.toString(), searchSpec).getNextPage(mExecutor, listAppSearchResult -> {

                                listAppSearchResultIterable = listAppSearchResult.getResultValue();
                                for (int searchItem = 0; searchItem < listAppSearchResultIterable.size(); searchItem++) {

                                    searchResultsDoc = listAppSearchResult.getResultValue().get(searchItem).getGenericDocument();
                                    if (searchResultsDoc.getSchemaType().equals("Shortcut")) {
                                        try {
                                            if (searchResultsDoc.getPropertyString("shortLabel") != null
                                                    & searchResultsDoc.getPropertyString("intents") != null) {

                                                Resources resSearchItem =
                                                        getApplication().getPackageManager().getResourcesForApplication(searchResultsDoc.getNamespace());

                                                int iconResId = (int) searchResultsDoc.getPropertyLong("iconResId");
                                                Drawable icon;
                                                if (iconResId != 0) {
                                                    icon = resSearchItem.getDrawable(iconResId);
                                                } else {
                                                    icon = getDrawable(R.drawable.border);
                                                }

                                                appName = AppUtils.getAppNameFromPkgName(context, searchResultsDoc.getNamespace());
                                                if (searchItem >= 1) {
                                                    if (appName ==
                                                            AppUtils.getAppNameFromPkgName(context, listAppSearchResultIterable.get(searchItem - 1).getGenericDocument().getNamespace())) {
                                                        appName = "";
                                                    }
                                                }
                                                globalSearchDataList.add(new
                                                        GlobalSearchData(
                                                        searchResultsDoc.getPropertyString("shortLabel"),
                                                        icon,
                                                        searchResultsDoc.getPropertyStringArray("intents"),
                                                        appName));
                                            }

                                        } catch (PackageManager.NameNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                        adapter.notifyDataSetChanged();


                                    } else if (searchResultsDoc.getSchemaType().equals("builtin:Person")) {
                                        Log.e(TAG, "SchemaType(builtin:Person) : " + searchResultsDoc.getSchemaType());

                                        globalSearchDataList.add(new GlobalSearchData(
                                                searchResultsDoc.getPropertyString("givenName"),
                                                ContextCompat.getDrawable(getApplication(), R.drawable.ic_launcher_background),
                                                listAppSearchResultIterable.get(searchItem).getGenericDocument().getPropertyStringArray("externalUri"), "Contact Person"));
                                        adapter.notifyDataSetChanged();

                                    } else if (searchResultsDoc.getSchemaType().equals("builtin:ContactPoint")) {
                                        Log.e(TAG, "SchemaType(builtin:ContactPoint) : " + listAppSearchResultIterable.get(searchItem).getGenericDocument().getSchemaType());

                                    } else {
                                        Log.e(TAG, "SchemaType(Unknown) :  Not yet added to the structure of the program|==> " + listAppSearchResultIterable.get(searchItem).getGenericDocument().getSchemaType());
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
            public void afterTextChanged(Editable text) {
                adapter.notifyDataSetChanged();
                globalSearchDataList.clear();
            }
        });

    }
}