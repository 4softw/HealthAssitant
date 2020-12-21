package com.google.se;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;

public class AddnewFoodAdapter  extends RecyclerView.Adapter<AddnewFoodAdapter.MyviewHolder>
        implements RecyclerView.OnClickListener {

    Context context;
    List<FoodModel> foods;
    DailyIDataDBHelper dailyIDataDBHelper;
    AddNewFoodDBHelper addNewFoodDBHelper;
    boolean check=true;


    public AddnewFoodAdapter(Context context, List<FoodModel> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_food_item_list, parent, false);
        final MyviewHolder holder = new MyviewHolder(view);
        dailyIDataDBHelper=new DailyIDataDBHelper(context);
        addNewFoodDBHelper=new AddNewFoodDBHelper(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, final int position) {
        final FoodModel food = foods.get(position);
        holder.setFood(food);
        final PersianCalendar persianCalendar=new PersianCalendar();

     //   Toast.makeText(context, persianCalendar.getPersianLongDateAndTime(), Toast.LENGTH_SHORT).show();
        food.setMeal(FoodPage.mealtxt);
        android.widget.DatePicker datePicker1=new android.widget.DatePicker(context);
      //  Toast.makeText(context,String.valueOf(datePicker1.getDayOfMonth()), Toast.LENGTH_SHORT).show();
        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<dailyIDataDBHelper.GetTodayFood("").size();i++) {
                    if (dailyIDataDBHelper.GetTodayFood("").get(i).getName().equals(food.getName())&&
                            dailyIDataDBHelper.GetTodayFood("").get(i).getMeal().equals(food.getMeal())) {
                        Toast.makeText(context, "قبلا این غذارو  تو این وعده انتخاب  کردی", Toast.LENGTH_SHORT).show();
                       check=false;
                    }
                }
                if (check==true){
                    holder.tick.setImageResource(R.drawable.oktic);
                    food.setMeal(FoodPage.mealtxt);
                    dailyIDataDBHelper.InsertFood(food);
                    Toast.makeText(context, "اضافه شد", Toast.LENGTH_SHORT).show();
                    int p=(int)food.getColorie()/10;
                    if (Integer.parseInt(HomePage.useColori.getText().toString())<
                            Integer.parseInt(HomePage.Maincolori.getText().toString())) {
                        HomePage.calPoint(p,context);

                    }
                    else {
                        HomePage.calPoint(-p,context);
                    }
                    setDelay(1000,holder.tick);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadyForInfoPage(position);
                Intent intent = new Intent(context, FoodInfo.class);
                context.startActivity(intent);
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

        TextView nametxt, coloritxt;
        ImageView tick;

        public MyviewHolder(View itemView) {
            super(itemView);
            nametxt = itemView.findViewById(R.id.name);
            coloritxt = itemView.findViewById(R.id.colori);
            tick=itemView.findViewById(R.id.tick);
        }

        public void setFood(final FoodModel food) {
            //       Toast.makeText(context, ""+food.getName(), Toast.LENGTH_SHORT).show();
            nametxt.setText(food.getName());
            coloritxt.setText(String.valueOf(food.getColorie()));
        }
    }

    void setDelay(int time, final ImageView imageView){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(R.drawable.tic);
          //      context.getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            }
        }, time);
    }

    void ReadyForInfoPage(int position){
        FoodPage.nameOffood=addNewFoodDBHelper.GetAllNewFoods().get(position).getName().toString();
        FoodPage.coloriOfffod=addNewFoodDBHelper.GetAllNewFoods().get(position).getColorie();
        FoodPage.gr=100;
        FoodPage.cPersent=addNewFoodDBHelper.GetAllNewFoods().get(position).getCarbohidrat();
        FoodPage.pPersent=addNewFoodDBHelper.GetAllNewFoods().get(position).getProtoein();
        FoodPage.fpercent=addNewFoodDBHelper.GetAllNewFoods().get(position).getFat();
    }
}

