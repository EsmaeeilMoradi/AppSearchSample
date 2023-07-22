package com.esm.appsearchsample.entities;

import com.esm.appsearchsample.adapter.TypeFactory;
import com.esm.appsearchsample.adapter.Visitable;

public class AppSearchPerson implements Visitable {

    private String personName;

    private String personIcon;

    private String externalUri;


    public AppSearchPerson(String personName, String personIcon, String externalUri) {
        this.personName = personName;
        this.personIcon = personIcon;
        this.externalUri = externalUri;
    }


    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonIcon() {
        return personIcon;
    }

    public void setPersonIcon(String personIcon) {
        this.personIcon = personIcon;
    }


    public String getExternalUri() {
        return externalUri;
    }

    public void setExternalUri(String externalUri) {
        this.externalUri = externalUri;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
