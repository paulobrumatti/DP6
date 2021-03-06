package br.com.dp6.datascience;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class PalestrantesRVAdapter extends RecyclerView.Adapter<PalestrantesRVAdapter.PersonViewHolder> {

    private List<Palestrante> persons;

    PalestrantesRVAdapter(List<Palestrante> persons) {
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_cards_palestrantes, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(persons.get(i).nome);
        personViewHolder.personCompany.setText(persons.get(i).empresa);
        personViewHolder.personBio.setText(persons.get(i).bio);
        personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personBio;
        TextView personCompany;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.palestrantesCardView);
            personName = (TextView) itemView.findViewById(R.id.palestranteName);
            personCompany = (TextView) itemView.findViewById(R.id.palestranteCompany);
            personBio = (TextView) itemView.findViewById(R.id.palestranteBio);
            personPhoto = (ImageView) itemView.findViewById(R.id.palestrantePhoto);
        }
    }
}