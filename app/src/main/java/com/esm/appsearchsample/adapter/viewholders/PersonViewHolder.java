package com.esm.appsearchsample.adapter.viewholders;


import android.content.Intent;
import android.net.Uri;
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

public class PersonViewHolder extends AbstractViewHolder<AppSearchPerson> {

    public static final int LAYOUT = R.layout.viewholder_person;

    public ImageView imgPersonIcon = (ImageView) itemView.findViewById(R.id.img_person_icon);
    public TextView tvPersonName = (TextView) itemView.findViewById(R.id.tv_person_name);
    public CardView cvPerson = (CardView) itemView.findViewById(R.id.cv_person);
    public ImageView ivPersonPhone = (ImageView) itemView.findViewById(R.id.iv_phone);
    public ImageView ivPersonContact = (ImageView) itemView.findViewById(R.id.iv_contact);

    public LinearLayout layoutPersonMain = (LinearLayout) itemView.findViewById(R.id.layout_main_person);

    public PersonViewHolder(@NonNull View view) {
        super(view);
    }

    @Override
    public void bind(AppSearchPerson element) {

        tvPersonName.setText(element.getPersonName());
        cvPerson.setBackgroundResource(R.drawable.lmo_preference_background);
        if (element.getPersonIcon() != null) {
            imgPersonIcon.setVisibility(View.VISIBLE);
            imgPersonIcon.setImageURI(Uri.parse(element.getPersonIcon()));
        } else {
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
        ivPersonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri number1 = Uri.parse("tel:" + element.getTelephoneNum());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number1);
                v.getContext().startActivity(callIntent);


            }
        });

        ivPersonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + element.getTelephoneNum());
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                v.getContext().startActivity(intent);
            }
        });


    }

}
