package br.com.dp6.datascience;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 *
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CronogramaFragment extends ListFragment {
    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CronogramaFragment() {
    }

    // TODO: Rename and change types of parameters
    public static CronogramaFragment newInstance() {
        CronogramaFragment fragment = new CronogramaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        String[] horarios = {
                "08:00 am - Café da Manhã",
                "09:00 am - Abertura",
                "09:30 am - Palestra: Atribuição Algorítmica",
                "10:15 am - Debate: Atribuição Algorítmica",
                "10:45 am - Coffee Break",
                "11:15 am - Palestra: Hiper-Personalização",
                "12:00 pm - Debate: Hiper-Personalização",
                "12:30 pm - Almoço"
        };

        String[] programacao = {
                "Início",
                "Tiago Turini (DP6)",
                "Paul Dodd (Google)",
                "Google e DP6",
                "Intervalo",
                "Jon Tehero (Adobe)",
                "Adobe e DP6",
                "Encerramento"
        };

        for (int i = 0; i < programacao.length; i++) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("title", horarios[i]);
            datum.put("subtitle", programacao[i]);
            data.add(datum);
        }

        this.setListAdapter(new SimpleAdapter(
                this.getContext(),
                data,
                R.layout.custom_listview,
                new String[]{"title", "subtitle"},
                new int[]{R.id.text1, R.id.text2}
        ));

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
        void onFragmentInteraction(String id);
    }

}
