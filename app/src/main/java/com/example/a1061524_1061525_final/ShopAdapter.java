package com.example.a1061524_1061525_final;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<item> allItemData;
    private Context activity;

    public ShopAdapter(Context a, List<item> all) {
        allItemData = all;
        activity = a;
    }

    // 建立ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        // 宣告元件
        private TextView item_name;
        private TextView item_price;

        ViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.text_name);
            item_price = itemView.findViewById(R.id.text_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getReference();
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    FirebaseUser user = auth.getCurrentUser();
                                    while (user.getEmail() == null)
                                        user = auth.getCurrentUser();
                                    String email = user.getEmail();
                                    for(int i = 0; i < (int) dataSnapshot.child("User").getChildrenCount(); i++)
                                        if (dataSnapshot.child("User").child("user" + i).child("user_email").getValue().equals(email)) {
                                            int myPoint = Integer.parseInt(dataSnapshot.child("User").child("user" + i).child("user_point").getValue().toString());
                                            int price = Integer.parseInt(dataSnapshot.child("Shop").child("discount"+ getAdapterPosition()).child("discount_price").getValue().toString());
                                            int sum = myPoint - price;
                                            if(sum >= 0) {
                                                myRef.child("User").child("user" + i).child("user_point").setValue(sum);
                                                DialogInterface.OnClickListener goBack = new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(activity, main_page.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                        activity.startActivity(intent);
                                                    }
                                                };
                                                new AlertDialog.Builder(activity)
                                                        .setMessage("兌換成功!")
                                                        .setPositiveButton("OK", goBack)
                                                        .show();
                                            }
                                            else
                                                new AlertDialog.Builder(activity)
                                                        .setMessage("Point不足")
                                                        .setPositiveButton("OK", null)
                                                        .show();
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {}
                            });
                        }
                    };
                    new AlertDialog.Builder(activity)
                            .setMessage("確定兌換嗎?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", OkClick)
                            .show();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 連結項目布局檔list_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_list_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 設置txtItem要顯示的內容
        holder.item_name.setText(allItemData.get(position).getName());
        holder.item_price.setText(allItemData.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return allItemData.size();
    }
}