package com.bac.code.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bac.code.R;
import com.bac.code.adb.Product_List_Adapter;
import com.bac.code.db.Product_Database;
import com.bac.code.model.Product;

import java.util.ArrayList;

import static com.bac.code.helper.Constants.tb_product;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoldProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoldProductsFragment extends Fragment {

    Product_List_Adapter adapter = null;
    static Product_Database db_helper;
    ArrayList<Product> product_list;
    Product product;
    ListView listView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SoldProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SoldProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoldProductsFragment newInstance(String param1, String param2) {
        SoldProductsFragment fragment = new SoldProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db_helper = new Product_Database(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sold_products, container, false);


        listView = view.findViewById(R.id.products_listview);



        product_list = new ArrayList<>();


        Cursor cursor = db_helper.find_all(
                "SELECT * FROM " + tb_product+ " where status='SOLD'");

        product_list.clear();
        while (cursor.moveToNext()) {


            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            double price = cursor.getDouble(2);
            String webpage = cursor.getString(3);
            String location = cursor.getString(4);

            product = new Product();
            product.setId(id);
            product.setTitle(title);
            product.setPrice(price);
            product.setLocation(location);
            product.setWebPage(webpage);




            product_list.add(product);

        }

        adapter = new Product_List_Adapter(getContext(), product_list, R.layout.product_list_row);
        listView.setAdapter(adapter);


        return view;
    }
}