package com.bac.code.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bac.code.R;
import com.bac.code.db.Product_Database;
import com.bac.code.model.Product;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText title, price, url, location;
    Button save;

    Product p;
    Product_Database db;


    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        db = new Product_Database(getContext());


        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_product, container, false);

        title = view.findViewById(R.id.title);
        location = view.findViewById(R.id.location);
        url = view.findViewById(R.id.webPage);
        price = view.findViewById(R.id.price);

        save = view.findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNew();
            }
        });


        return  view;
    }

    public  void CreateNew(){

        String _title = title.getText().toString();
        String _location = location.getText().toString();
        String _url = url.getText().toString();
        String _price =price.getText().toString();



        if(_title.isEmpty() || _location.isEmpty() || _url.isEmpty() || _price.isEmpty())
        {
            Toast.makeText(getContext(), "All Fields are Required", Toast.LENGTH_LONG).show();

        }else
        {


            p = new Product();
            p.setWebPage(_url);
            p.setLocation(_location);
            p.setTitle(_title);
            p.setPrice(Double.parseDouble(_price));


            long success = db.save_Product(p);

            if(success > 0)
            {

                Toast.makeText(getContext(), "Item Added Successfully", Toast.LENGTH_LONG).show();

            }

        }


    }

}