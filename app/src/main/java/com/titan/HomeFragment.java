package com.titan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeFragment extends Fragment {

    public HomeFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        String[]menuItems = new String[]{"Kevlton Plugpoint","Kevilton Adapters","Kevlton MainSwitch","Kevlton TripSwitch",
                "Kevlton PowerCodes","Kevlton Breaker","Kevilton JunkBoxes","Keviltion Plastic Enclosures","Keviltion Trailer Sockets",
        "Kelani Panel Wires","Kelani Indoor Wires","Kelani Overhead Wires","Kevilton Lamp Holders"};
        int[]items =new int[]{R.drawable.item1,R.drawable.item2};

        ListView listView = (ListView)view.findViewById(R.id.mainMenu);
        ArrayAdapter<String>listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        );

        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    Intent intent = new Intent(getActivity(),Detail.class);
                    startActivity(intent);
                }
                if(position == 1)
                {
                    Intent intent = new Intent(getActivity(),Detail2.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
