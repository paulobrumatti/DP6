package br.com.dp6.datascience;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.dp6.datascience.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 *
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CronogramaFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static CronogramaFragment newInstance() {
        CronogramaFragment fragment = new CronogramaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CronogramaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Change Adapter to display your content
        //setListAdapter(new SimpleAdapter(this.getContext(), eventoList));
    }

    HashMap<String,Integer> maps = new HashMap<String,Integer>();

    public Evento[] eventoList = {
            new Evento("8:00 am", "Café da Manhã"),
            new Evento("9:00 am", "Abertura do Evento"),
            new Evento("9:30 am", "Atribuição Algorítmica")
    };

    public class Evento {
        public String time;
        public String title;
        public Evento(String time, String title) {
            this.time = time;
            this.title = title;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * See the Android Training lesson Communicating with Other Fragments for more information.
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
