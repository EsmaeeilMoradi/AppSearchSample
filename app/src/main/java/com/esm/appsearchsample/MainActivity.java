package com.esm.appsearchsample;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.GenericDocument;
import android.app.appsearch.SearchResult;
import android.app.appsearch.SearchSpec;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esm.appsearchsample.adapter.Adapter_Visitor;
import com.esm.appsearchsample.adapter.TypeFactory;
import com.esm.appsearchsample.adapter.TypeFactoryForList;
import com.esm.appsearchsample.adapter.Visitable;
import com.esm.appsearchsample.entities.AppSearchPerson;
import com.esm.appsearchsample.entities.AppSearchShortcut;

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
    private Drawable appIcon;
    private Drawable shortcutIcon;

    private ArrayList<Visitable> elementsearchList = new ArrayList<>();
    private TypeFactory typeFactory = new TypeFactoryForList();
    private Adapter_Visitor adapter = new Adapter_Visitor(elementsearchList, typeFactory);

    private EditText editTextGlobalSearch;
    private RecyclerView recyclerViewGlobalSearch;


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

        recyclerViewGlobalSearch.setHasFixedSize(true);
        recyclerViewGlobalSearch.setLayoutManager(new LinearLayoutManager(getApplication()));
        recyclerViewGlobalSearch.setAdapter(adapter);

        editTextGlobalSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                elementsearchList.clear();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                elementsearchList.clear();
                adapter.notifyDataSetChanged();
               
                if (text.length() >= 1) try {
                    appSearchManager.createGlobalSearchSession(mExecutor, globalSearchSessionAppSearchResult -> {
                        Log.e(TAG, "onCreate: check globalSearchSession " + globalSearchSessionAppSearchResult.isSuccess());
                        globalSearchSessionAppSearchResult.getResultValue().search(text.toString(), searchSpec).getNextPage(mExecutor, listAppSearchResult -> {

                            listAppSearchResultIterable = listAppSearchResult.getResultValue();
                            for (int searchItem = 0; searchItem < listAppSearchResultIterable.size(); searchItem++) {

                                searchResultsDoc = listAppSearchResult.getResultValue().get(searchItem).getGenericDocument();
                                if (searchResultsDoc.getSchemaType().equals("Shortcut")) {
                                    try {
                                        if (searchResultsDoc.getPropertyString("shortLabel") != null & searchResultsDoc.getPropertyString("intents") != null) {

                                            shortcutIcon = AppUtils.getShortcutIconFromIconResId(context, listAppSearchResultIterable, searchItem);
                                            appName = AppUtils.getAppNameFromPkgName(context, listAppSearchResultIterable.get(searchItem).getGenericDocument().getNamespace());
                                            appIcon = AppUtils.getAppIconFromPkgName(context, listAppSearchResultIterable.get(searchItem).getGenericDocument().getNamespace());

                                            if (searchItem >= 1) {
                                                if (appName == AppUtils.getAppNameFromPkgName(context, listAppSearchResultIterable.get(searchItem - 1).getGenericDocument().getNamespace())) {
                                                    appName = "";
                                                    appIcon = null;
                                                }
                                            }

                                            elementsearchList.add(new AppSearchShortcut(appIcon, searchResultsDoc.getPropertyString("shortLabel"), shortcutIcon, searchResultsDoc.getPropertyStringArray("intents"), appName));
                                        }

                                    } catch (PackageManager.NameNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                    adapter.notifyDataSetChanged();


                                } else if (searchResultsDoc.getSchemaType().equals("builtin:Person")) {
                                    Log.e(TAG, "SchemaType(builtin:Person) : " + searchResultsDoc.getSchemaType());

                                    try {
                                        elementsearchList.add(new AppSearchPerson(searchResultsDoc.getPropertyString("name"), searchResultsDoc.getPropertyString("imageUri"), searchResultsDoc.getPropertyString("externalUri")));
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else if (searchResultsDoc.getSchemaType().equals("builtin:ContactPoint")) {
                                    Log.e(TAG, "SchemaType(builtin:ContactPoint) : " + searchResultsDoc.getSchemaType());


                                } else {
                                    Log.e(TAG, "SchemaType(Unknown) :  Not yet added to the structure of the program|==> " + searchResultsDoc.getSchemaType());
                                }
                            }
                        });
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable text) {
                adapter.notifyDataSetChanged();
                elementsearchList.clear();
            }
        });
    }
}