package com.esm.appsearchsample;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.SearchResult;
import android.app.appsearch.SearchSpec;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Executor mExecutor;
    private Context context;
    private SearchSpec searchSpec;
    private AppSearchManager appSearchManager;
    private SearchResult searchResults;
    private String queryExpression = "Test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get searchResults by createGlobalSearchSession from AppSearchManager
        searchSpec = new SearchSpec.Builder().build();
        context =this;
        mExecutor = context.getMainExecutor();
        appSearchManager = context.getSystemService(AppSearchManager.class);

        appSearchManager.createGlobalSearchSession(mExecutor, globalSearchSessionAppSearchResult -> {
            Log.e(TAG, "onCreate: check globalSearchSession " + globalSearchSessionAppSearchResult.isSuccess());

            globalSearchSessionAppSearchResult.
                    getResultValue().
                    search(queryExpression, searchSpec).
                    getNextPage(mExecutor, listAppSearchResult -> {
                        for (int searchItem = 0; searchItem < listAppSearchResult.getResultValue().size(); searchItem++) {
                            searchResults = listAppSearchResult.getResultValue().get(searchItem);

                            Log.e(TAG, "SearchResult GenericDocument :" + searchResults.getGenericDocument());
                            Log.e(TAG, "SearchResult SchemaType      :" + searchResults.getGenericDocument().getSchemaType());
                            Log.e(TAG, "SearchResult Namespace       :" + searchResults.getGenericDocument().getNamespace());
                            //Currently get all property from  GenericDocument Class and etc
                        }
                    });
        });

    }
}