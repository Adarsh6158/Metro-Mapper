package com.example.metromapper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity4 extends AppCompatActivity {

    EditText et_from, et_to;
    Button btn_route;
    ListView listView;

    ArrayList<Station> stationArrayList = new ArrayList<>(5);
    ArrayAdapter<String> adapterString;
    MyAdapter customAdapter;
    ArrayAdapter<Station> adapterStation;
    Station[] stationArray = new Station[5];

    // a string array of stations in route
    String[] routeNameArray;
    int[] routeNodeArray;

    Graph metroMap = new Graph(11);
    String[] stationNameList = new String[]{"Sector 55 56", "Sector 54 Chowk", "Sector 53 54",
            "Sector 42 43", "Phase 1", " Sikandarpur", "Phase 2", "Vodafone Belvedere Towers",
            "IndusInd Bank Cyber City", "Micromax Moulsari Avenue", "Phase 3"};



    int[] stationNodeList = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    int[] stationColorCode = new int[]{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};

    boolean sourceEntered = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search4);
        et_from = findViewById(R.id.et_from);
        et_to = findViewById(R.id.et_to);
        btn_route = findViewById(R.id.btn_route);
        listView = findViewById(R.id.listView);


        Station[] stationArray = new Station[11];
        for(int i = 0; i<11; i++){
            stationArray[i] = new Station(stationNameList[i], stationNodeList[i], stationColorCode[i]);
        }

        // addEdges to the graph: metroMap
        addEdgesToMetroMap();

        // adding stations to the stationArrayList
        fillStationArrayList(stationArray);

        // populating the list view of all the stations

        populateListView();

        // populating the list view of all the stations (with logo) (does not work, has bugs)
//        populateListViewWithLogo();


        //Suggestions for user in listview based on the letter they typed to enter station for source
        et_from.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //populateListView();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Triggered when text changed at et_to edittext
                Log.d("Listview","Is this list view working");
                SearchActivity4.this.adapterString.getFilter().filter(s);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("SearchActivity","Item clicked at location"+position);
                        String elemt=adapterString.getItem(position);
                        et_from.setText(elemt);
                        et_to.requestFocus();
                        populateListView();

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Suggestions for user in listview based on the letter they typed to enter station for destination
        et_to.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Listview","Is this triggered");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Triggered when text changed at et_from edittext
                Log.d("Listview","Is this list view working");
                SearchActivity4.this.adapterString.getFilter().filter(s);
                listView.setVisibility(View.VISIBLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("SearchActivity","Item clicked at location"+position);
                        String elemt=adapterString.getItem(position);
                        et_to.setText(elemt);

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!sourceEntered){
                    et_from.setText(stationArrayList.get(position).getStationName());
                    sourceEntered = true;
                } else {
                    et_to.setText(stationArrayList.get(position).getStationName());
                    sourceEntered = false;
                }
            }
        });

        btn_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This intent starts the cardview
//                Intent intent=new Intent(getApplicationContext(),cardview.class);
//                startActivity(intent);
                String from = et_from.getText().toString();
                String to = et_to.getText().toString();

                if(from.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter boarding station", Toast.LENGTH_SHORT).show();
                    et_from.requestFocus();
                } else if(to.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter destination station", Toast.LENGTH_SHORT).show();
                    et_to.requestFocus();
                }
                else{
                    showPath(from, to);
                }
            }
        });
    }



    // functions

    public void fillStationArrayList(Station stationArray[]){
        for(int i = 0; i < stationArray.length; i++){
            stationArrayList.add(stationArray[i]);
        }
    }

    public void fillStationArray(){
        for (int i = 0; i < stationArray.length; i++){
            stationArray[i].addStationValues(stationNameList[i], stationNodeList[i], stationColorCode[i]);
        }
    }

    public void populateListView(){
        adapterString = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stationNameList);
        listView.setAdapter(adapterString);
    }

    public void populateListViewWithLogo(){

        // adding colored logos
        for(int i=0; i<stationColorCode.length; i++){
            stationColorCode[i] = colorCodeToImageResource(stationColorCode[i]);
        }

        customAdapter = new MyAdapter(getApplicationContext(), stationNameList, stationNodeList, stationColorCode);
        listView.setAdapter(customAdapter);
    }


    public int colorCodeToImageResource(int colorCode){
        int red = R.drawable.delhi_metro_logo1;
        int blue = R.drawable.delhi_metro_logo2;
        int green = R.drawable.delhi_metro_logo3;
        int yellow = R.drawable.delhi_metro_logo4;
        int pink = R.drawable.delhi_metro_logo5;
        int purple = R.drawable.delhi_metro_logo6;
        int magenta = R.drawable.delhi_metro_logo7;
        int violet = R.drawable.delhi_metro_logo8;
        int orange = R.drawable.delhi_metro_logo9;
        int black = R.drawable.delhi_metro;

        int logoColor;

        switch(colorCode){
            case 1: logoColor = red; break;
            case 2: logoColor = blue; break;
            case 3: logoColor = green; break;
            case 4: logoColor = yellow; break;
            case 5: logoColor = pink; break;
            case 6: logoColor = purple; break;
            case 7: logoColor = magenta; break;
            case 8: logoColor = violet; break;
            case 9: logoColor = orange; break;
            default: logoColor = black;
        }
        return logoColor;
    }


    public void addEdgesToMetroMap(){

        //  Sector 55 56
        metroMap.addEdge(0, 1);

        //Sector 54 Chowk
        metroMap.addEdge(1, 0);
        metroMap.addEdge(1, 2);

        //Sector 53 54
        metroMap.addEdge(2, 1);
        metroMap.addEdge(2, 3);

        // Sector 42 43
        metroMap.addEdge(3, 2);
        metroMap.addEdge(3, 4);

        //  Phase 1
        metroMap.addEdge(4, 3);
        metroMap.addEdge(4, 5);

        // Sikandarpur
        metroMap.addEdge(5, 4);
        metroMap.addEdge(5, 6);

        // Phase 2
        metroMap.addEdge(6, 5);
        metroMap.addEdge(6, 7);

        // Vodafone Belvedere Towers
        metroMap.addEdge(7, 6);
        metroMap.addEdge(7, 8);

        // IndusInd Bank Cyber City
        metroMap.addEdge(8, 7);
        metroMap.addEdge(8, 9);

        //  Micromax Moulsari Avenue
        metroMap.addEdge(9, 8);
        metroMap.addEdge(9, 10);

        // Phase 3
        metroMap.addEdge(10, 9);


    }




    public void showPath(String source, String destination) {
        int src, dest;
        src = search(source);
        dest = search(destination);

        // use something like: metroMap.get(position).getStationGraphNode;
        metroMap.printAllPaths(src, dest);
        ArrayList <ArrayList <Integer> > allRouteArrayList = new ArrayList <ArrayList<Integer>>();

        allRouteArrayList = metroMap.getAllPaths();

        Log.d("okok", "All paths:" + allRouteArrayList);
        // passing arrayList to intent
        Intent intent = new Intent(getApplicationContext(), RouteActivity4.class);

        // passing the number of paths
        intent.putExtra("noOfArrayLists", allRouteArrayList.size());

        // passing individual paths

        for(int i=0; i<allRouteArrayList.size(); i++) {
            intent.putExtra("pathArrayList" + i, allRouteArrayList.get(i));
        }
        Log.d("route", "starting intent");
        startActivity(intent);
    }

    public int search(String s){
        int index=0;
        for(int i=0; i<stationNameList.length; i++)
            if(s.equals(stationNameList[i]))
                index = i;
        Log.d("route", "inside search, index: " + index );
        return index;
    }

    public String[] nodeToStation(ArrayList<Integer> route, int length){
        String[] stationName = new String[length];
        for(int i = 0; i < length; i++){
            stationName[i] = stationNameList[route.get(i)];
        }
        return stationName;
    }

    public int[] nodeArrayListToNodeArray(ArrayList<Integer> route, int length){
        int[] stationNode = new int[length];
        for(int i=0; i<length; i++){
            stationNode[i] = route.get(i);
        }
        return stationNode;
    }

    public

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String stationName[];
        int stationNode[];
        int lineColor[];

        MyAdapter (Context c, String name[], int node[], int img[]) {
            super(c, R.layout.station_row_final, R.id.tv_stationName, name);
            this.context = c;
            this.lineColor = img;
            this.stationName = name;
            this.stationNode = node;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.station_row_final, parent, false);
            TextView sName = row.findViewById(R.id.tv_stationName);
            TextView sNode = row.findViewById(R.id.tv_stationNode);
            ImageView sColor = row.findViewById(R.id.imageView);

            // now set our resources on views
            sName.setText(stationName[position]);
            sNode.setText("Node number " + stationNode[position]);
            sColor.setImageResource(lineColor[position]);



            return row;
        }
    }
}
