package com.bac.code.adb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bac.code.R;
import com.bac.code.model.Product;

import java.util.List;


public class Product_List_Adapter extends BaseAdapter {


    private Context context;
    private List<Product> products;
    private int layout;

    public Product_List_Adapter(Context context, List<Product> products, int layout) {
        this.context = context;
        this.products = products;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View list_row = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if(list_row == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            list_row = inflater.inflate(layout, null);

            viewHolder.title = (TextView) list_row.findViewById(R.id.title);

            viewHolder.price = (TextView) list_row.findViewById(R.id.price);


            list_row.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) list_row.getTag();
        }

        Product product= products.get(i);

        viewHolder.title.setText(product.getTitle());
        viewHolder.price.setText("P " + String.valueOf(product.getPrice()));





        return list_row;
    }


    private  class  ViewHolder{

        TextView title, price;
    }
}
