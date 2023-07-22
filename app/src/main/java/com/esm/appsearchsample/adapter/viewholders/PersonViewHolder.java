package com.esm.appsearchsample.adapter.viewholders;


import static com.esm.appsearchsample.AppUtils.setMargins;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.esm.appsearchsample.R;
import com.esm.appsearchsample.entities.AppSearchPerson;

import java.net.URISyntaxException;

public class PersonViewHolder extends AbstractBetterViewHolder<AppSearchPerson> {

    public static final int LAYOUT = R.layout.viewholder_person;

    public ImageView imgPersonIcon = (ImageView) itemView.findViewById(R.id.img_person_icon);
    public TextView tvPersonName = (TextView) itemView.findViewById(R.id.tv_person_name);
    public CardView cvPerson = (CardView) itemView.findViewById(R.id.cv_person);

    public LinearLayout layoutPersonMain = (LinearLayout) itemView.findViewById(R.id.layout_main_person);

    public PersonViewHolder(@NonNull View view) {
        super(view);
    }

    @Override
    public void bind(AppSearchPerson element) {

        Log.e("ESM", "bind: "+element.getPersonName() );
        tvPersonName.setText(element.getPersonName());

        if (element.getPersonIcon()!= null) {
            imgPersonIcon.setVisibility(View.VISIBLE);
            imgPersonIcon.setImageURI(Uri.parse(element.getPersonIcon()));
        }else {
            imgPersonIcon.setVisibility(View.INVISIBLE);
        }
        cvPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + element.getPersonName(), Toast.LENGTH_LONG).show();
                Intent intent;
                try {
                    intent = Intent.parseUri(element.getExternalUri(), 0);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                view.getContext().startActivity(intent);
            }
        });



    }



}
