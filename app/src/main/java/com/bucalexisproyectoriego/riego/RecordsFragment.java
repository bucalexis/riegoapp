package com.bucalexisproyectoriego.riego;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class RecordsFragment extends Fragment implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final int REQUEST_CODE_GET_CAR = 1;
    public RecordsFragment() {
    }
    private SearchView search;
    RecordsAdapter recordsAdapter;
    ArrayList<Record> searchingList;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_records, container, false);
        searchingList = new ArrayList<Record>();
       // MyDBHandler dbHandler = new MyDBHandler(getActivity(), null, null, 1);

        //searchingList=dbHandler.findSongs("","",1,"");
        searchingList.add(new Record("Pedro Gonzalez","27/01/2017",1,2,3,4,5,0,0,"Lote 65"));
        searchingList.add(new Record("Pedro Gonzalez","27/01/2017",1,2,3,4,5,0,0,"Lote 65"));


        list = (ListView) rootView.findViewById(R.id.listOfRecords);

        // Inicializar el adaptador con la fuente de datos.
        RecordsAdapter recordsAdapter= new RecordsAdapter(getActivity(),searchingList);


        //Relacionando la lista con el adaptador
        list.setAdapter(recordsAdapter);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) rootView.findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

        //search.setQueryHint(getResources().getString(R.string.searching_view_text));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Record record=new Record();
                record=(Record) parent.getAdapter().getItem(position);
                Log.d("Click:",record.getName());
               // Log.e("Click:",s.getNumber() + "");
                //FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                //  ExampleFragment fragment1 = new ExampleFragment ();
                // ft.replace(R.id.content_frame, fragment1);

                // ft.addToBackStack(null);

                // fragment1.setArguments();
                //ft.commit();
                Intent intent = new Intent(getActivity(), RecordDetailsActivity.class);
                intent.putExtra("recordName",record.getName());
                startActivity(intent);

            }
        });



        return rootView;
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Query:","Hola");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
       // MyDBHandler dbHandler = new MyDBHandler(getActivity(), null, null, 1);
        Log.d("Query:",newText);
      //  searchingList.clear();
       // searchingList.addAll(dbHandler.findSongs(newText,"",1,""));
       // songsAdapter.notifyDataSetChanged();


        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the activity result was received from the "Get Car" request
        if (REQUEST_CODE_GET_CAR == requestCode) {

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
