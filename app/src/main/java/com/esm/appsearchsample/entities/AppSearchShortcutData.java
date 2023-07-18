package com.esm.appsearchsample.entities;


import android.app.Person;
import android.content.ComponentName;
import android.content.Intent;
import android.content.LocusId;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.ArraySet;

import com.esm.appsearchsample.adapter.TypeFactory;
import com.esm.appsearchsample.adapter.Visitable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a shortcutData that can be published via GlobalSearchSession (by AppSearch).
 * Encapsulates a (SCHEMA_TYPE = "Shortcut")  that represent an ShortcutDataObject .
 * <p>
 * Converts this {GenericDocument} object into { ShortcutInfo} to read the information.
 */

public class AppSearchShortcutData implements Visitable {

    private String namespace;
    private String schemaType;
    private ArrayList<Intent> intents;
    private String shortLabel;
    private int iconResId;

    public AppSearchShortcutData(String namespace,
                                 String schemaType,
                                 ArrayList<Intent> intents,
                                 String shortLabel,
                                 int iconResId) {
        this.namespace = namespace;
        this.schemaType = schemaType;
        this.intents = intents;
        this.shortLabel = shortLabel;
        this.iconResId = iconResId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public void setSchemaType(String schemaType) {
        this.schemaType = schemaType;
    }

    public ArrayList<Intent> getIntents() {
        return intents;
    }

    public void setIntents(ArrayList<Intent> intents) {
        this.intents = intents;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }


    @Override
    public int type(TypeFactory typeFactory) {
        return  typeFactory.type(this);
    }


    //Currently, the following items are not needed and are not used.
    private String id;
    private int score;
    private long creationTimestampMillis;
    private long timeToLiveMillis;
    //Property
    private String activityString;
    private ComponentName activity;
    private ArrayList<String> categories;
    private ArraySet<String> categoriesSet;
    private String disabledMessage;
    private int disabledReason;
    private String disabledReasonString;
    private int flags;
    private String locusIdString;
    private LocusId locusId;
    private String iconResName;
    private String iconUri;
    private String intentPersistableExtras;
    private ArrayList<String> intentsStrings;
    private ArrayList<ArrayList<Byte>> intentExtrasesBytes;
    private ArrayList<Bundle> intentExtrases;
    private String longLabel;
    private ArrayList<Person> persons;
    private ArrayList<Byte> extrasByte;
    private PersistableBundle extras;
    private Map<String, Map<String, List<String>>> capabilityBindings;


}
