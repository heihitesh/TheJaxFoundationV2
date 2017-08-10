package com.itshiteshverma.thejaxfoundationv2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nilesh Verma on 7/30/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    final Context context = null;
    private String[] titles = {"Yash Sharma",
            "Manish Kumar",
            "Plaza Rani", "Kriti Singhal", "Priyanka Gupta"};

    private String[] designation = {"Director",
            "Director & General",
            "PR & HR", "Executive Director Administrator", "Executive Director Culture and Marketing Dept"};

    private String[] emailId = {"yash_sharma1993@yahoo.co.in",
            "manishvitalbiology@gmail.com", "null", "kriti.jaxhealthcarefoundation@gmail.com", "priyankasun001@gmail.com"};

    private String[] phoneNo = {"+91-9811800357",
            "+91-7053946682", "+91-9810836922", "+91-8447511826", "91-9899710354"};

    private int[] images = {R.drawable.images,
            R.drawable.images,
            R.drawable.images,
            R.drawable.images,
            R.drawable.images};

    class ViewHolder extends RecyclerView.ViewHolder {

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDesignation;
        public TextView itemPhoneNo;
        public TextView itemEmail;

        public ViewHolder(final View itemView) {
            super(itemView);

            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemEmail = (TextView) itemView.findViewById(R.id.item_email);
            itemPhoneNo = (TextView) itemView.findViewById(R.id.item_phone);
            itemDesignation =
                    (TextView) itemView.findViewById(R.id.item_designation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final int position = getAdapterPosition();

                    Snackbar snackbar = Snackbar
                            .make(v, titles[position], Snackbar.LENGTH_LONG)
                            .setAction("CALL", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent callIntent1 = new Intent(Intent.ACTION_CALL);
                                    String call1 = phoneNo[position];
                                    callIntent1.setData(Uri.parse("tel:" + call1));
                                    if (ActivityCompat.checkSelfPermission(itemView.getContext().getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        Toast.makeText(itemView.getContext().getApplicationContext(),"NOTworing",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    itemView.getContext().startActivity(callIntent1);
                                }
                            });

                    snackbar.show();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemEmail.setText(emailId[i]);
        viewHolder.itemPhoneNo.setText(phoneNo[i]);
        viewHolder.itemDesignation.setText(designation[i]);
        viewHolder.itemImage.setImageResource(images[i]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
