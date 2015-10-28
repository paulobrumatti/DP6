package br.com.dp6.datascience;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class PalestrantesFragment extends Fragment {

    private List<Person> pessoas;
    private RecyclerView rv;

    public PalestrantesFragment() {
        // Required empty public constructor
    }

    public static PalestrantesFragment newInstance() {
        return new PalestrantesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_palestrantes, container, false);
        initializeRecycler(v);
        return v;
    }

    public void initializeRecycler(View v) {
        rv = (RecyclerView) v.findViewById(R.id.palestrantesRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        initializeData();
        initializeAdapter();
    }

    private void initializeData() {
        pessoas = new ArrayList<>();
        pessoas.add(new Person("Jon Tehero", "Adobe", "Senior Product Manager da Adobe, trabalha para evangelizar a prática da personalização de conteúdo, testes iterativos e automação da jornada dos consumidores no mundo do marketing digital. Tehero é Mestre em Sistemas Interativos pela Brigham Young University e atuou como consultor para algumas das maiores empresas norte-americanas de forma independente e pela KPMG. ", R.drawable.foto_tehero));
        pessoas.add(new Person("Paul Dodd", "Google", "Head of Sales do Google, foi VP of World Wide Sales da Adometry, empresa especializada em atribuição algorítmica multi-canal adquirida pelo Google. Anteriormente, Paul trabalhou como VP de Vendas no Bazaarvoice e como executivo na Dell, TestChip Technologies e Advanced Micro Devices, além de ser Mestre em Administração pela Baylor University", R.drawable.foto_dodd));
        pessoas.add(new Person("Tiago Turini", "DP6", "Sócio-Fundador e CEO da DP6, engenheiro mecatrônico pela Escola Politécnica (USP), trabalhou em empresas como Booz Allen, Promon, Microsoft, American Express e AgênciaClick nas áreas de estratégia, planejamento financeiro, Business Intelligence, gerenciamento de projetos entre outros.", R.drawable.foto_turini));
    }

    private void initializeAdapter() {
        PalestrantesRVAdapter adapter = new PalestrantesRVAdapter(pessoas);
        rv.setAdapter(adapter);
    }
}
