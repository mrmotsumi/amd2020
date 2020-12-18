package com.bac.code.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bac.code.R;
import com.bac.code.adb.Product_List_Adapter;
import com.bac.code.db.Product_Database;
import com.bac.code.model.Product;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import static com.bac.code.helper.Constants.tb_product;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Product_List_Adapter adapter = null;
    static Product_Database db_helper;
    ArrayList<Product> product_list;
    Product product;
    ListView listView;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        product_list = new ArrayList<>();


        Cursor cursor = db_helper.find_all(
                "SELECT * FROM " + tb_product+ " where status='active'");

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




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.products_listview);


        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                CharSequence[] items = {"Update", "Delete", "Mark as Sold" ,  "Details"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Choose Action");

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {

                            Cursor c = db_helper.queryData("SELECT id FROM  " + tb_product);
                            ArrayList<Integer> ids = new ArrayList<>();

                            while (c.moveToNext()) {

                                ids.add(c.getInt(0));

                            }//show Update dialog

                            UpdateDialog(getActivity(), ids.get(position));

                        }
                        if (i == 1) {

                            Cursor c = db_helper.queryData("SELECT id FROM " +  tb_product);
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));

                        }

                        if (i == 2) {

                            Cursor c = db_helper.queryData("SELECT id FROM " +  tb_product);
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            MarkASPurchased(arrID.get(position));

                        }

                        if (i == 3) {

                            Cursor c = db_helper.queryData("SELECT id FROM " +  tb_product);
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            OpenDetails(arrID.get(position));

                        }
                    }
                });
                builder.show();
            }
        });



        return  view;
    }

    private void MarkASPurchased(Integer integer) {

        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getContext());
        dialogDelete.setTitle("Alert!!");
        dialogDelete.setMessage("Are you sure want to mark as SOLD?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Product p = new Product();
                p.setId(integer);
                try {

                    db_helper.markSold(p);
                    Toast.makeText(getContext(), "Marked successfully", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    UpdateUI();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                UpdateUI();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void OpenDetails(Integer integer) {

        
    }

    private void showDialogDelete(final Integer integer) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Product p = new Product();
                    p.setId(integer);
                    db_helper.delete_Product(p);
                    Toast.makeText(getContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                UpdateUI();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void UpdateDialog(Activity activity, Integer integer) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_layout);
        dialog.setTitle("Update Item");

        final EditText title = (EditText) dialog.findViewById(R.id.title);
        final EditText price = (EditText) dialog.findViewById(R.id.price);
        final EditText webpage = (EditText) dialog.findViewById(R.id.webPage);
        final EditText location = (EditText) dialog.findViewById(R.id.location);

        final Button btnUpdate = (Button) dialog.findViewById(R.id.btnupdate);


        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);

        int height = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1.35);

        dialog.getWindow().setLayout(width, height);

        dialog.show();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Product   p = new Product();
                    p.setId(integer);
                    p.setWebPage(webpage.getText().toString());
                    p.setLocation(location.getText().toString());
                    p.setTitle(title.getText().toString());
                    p.setPrice(Double.parseDouble(price.getText().toString()));

                    db_helper.update_Product(p);

                    adapter.notifyDataSetChanged();

                    Toast.makeText(getContext(), "update successful", Toast.LENGTH_LONG).show();



                }catch (Exception ex){
                    ex.toString();
                }

                UpdateUI();
                dialog.dismiss();

            }
        });




    }

    private void UpdateUI() {


        Cursor cursor= db_helper.queryData("SELECT * FROM " + tb_product + " where status='active'");
        product_list.clear();
        while (cursor.moveToNext()){


            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            double price = cursor.getDouble(2);
            String webpage = cursor.getString(3);
            String location = cursor.getString(4);


            Product product = new Product();
            product.setId(id);
            product.setTitle(title);
            product.setPrice(price);
            product.setWebPage(webpage);
            product.setLocation(location);


            product_list.add(product);
        }
    }
}