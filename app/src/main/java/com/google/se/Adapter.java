package com.google.se;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<String>
{

    private Context context;
    private List<String> strings;
    private List<Integer> ticks;
    private  List<Integer> lines;

    public Adapter(Context context, List<String> strings,List<Integer> tick,List<Integer> line)
    {
        super(context, R.layout.list_view_items, strings);
        this.context = context;
        this.strings = new ArrayList<String>();
        this.ticks = new ArrayList<Integer>();
        this.lines = new ArrayList<Integer>();
        this.strings = strings;
        this.ticks = tick;
        this.lines = line;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_view_items, parent, false);

        TextView foodName = (TextView) rowView.findViewById(R.id.food_name);
        ImageView tick = (ImageView) rowView.findViewById(R.id.tick);
        ImageView line = (ImageView) rowView.findViewById(R.id.line);


        foodName.setText(strings.get(position));
        tick.setImageResource(ticks.get(position));
        line.setImageResource(lines.get(position));//Instead of the same value use position + 1, or something appropriate

        return rowView;
    }
}