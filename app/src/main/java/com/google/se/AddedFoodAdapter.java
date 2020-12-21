package com.google.se;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;


public class AddedFoodAdapter  extends RecyclerView.Adapter<AddedFoodAdapter.MyviewHolder>
        implements RecyclerView.OnClickListener {

    Context context;
    DailyIDataDBHelper dailyIDataDBHelper;
    List<FoodModel> foods;


    public AddedFoodAdapter(Context context, List<FoodModel> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.added_food_list_adapter, parent, false);
        MyviewHolder holder = new MyviewHolder(view);
        dailyIDataDBHelper=new DailyIDataDBHelper(context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, final int position) {
        final FoodModel food = foods.get(position);
        holder.setFood(food);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deletDialog(food,holder);

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    @Override
    public void onClick(View v) {


    }


    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView nametxt, mealtxt, coloritxt;

        public MyviewHolder(View itemView) {
            super(itemView);
            nametxt = itemView.findViewById(R.id.name);
            mealtxt = itemView.findViewById(R.id.meal);
            coloritxt = itemView.findViewById(R.id.colori);
        }

        public void setFood(final FoodModel food) {
     //       Toast.makeText(context, ""+food.getName(), Toast.LENGTH_SHORT).show();
            nametxt.setText(food.getName());
            mealtxt.setText(food.getMeal());
            coloritxt.setText(String.valueOf(food.getColorie()));
        }
    }


    public void deletDialog(final FoodModel food, final MyviewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
        builder.setTitle("حذف");
        builder.setMessage("مطمئنی میخوای حذف شه؟");
        builder.setPositiveButton("بله مطمئنم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dailyIDataDBHelper.DeletDailyFood(food.getName(),HomePage.getdate());
                holder.itemView.setVisibility(View.GONE);
                int p=(int)food.getColorie()/10;
                if (Integer.parseInt(HomePage.useColori.getText().toString())<
                        Integer.parseInt(HomePage.Maincolori.getText().toString())) {
                        HomePage.calPoint(-p,context);


                }
                else {
                    HomePage.calPoint(p,context);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("نه", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
