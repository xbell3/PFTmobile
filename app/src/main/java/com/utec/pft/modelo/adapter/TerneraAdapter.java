package com.utec.pft.modelo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.utec.pft.R;
import com.utec.pft.controlador.ConstructorFecha;
import com.utec.pft.modelo.Ternera.TerneraDTO;

import java.util.List;

public class TerneraAdapter extends ArrayAdapter<TerneraDTO> {

    private Context conext;
    private List<TerneraDTO> terneras;

    public TerneraAdapter(@NonNull Context context, int resource, @NonNull List<TerneraDTO> terneras) {
        super(context, resource, terneras);
        this.conext = context;
        this.terneras = terneras;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) conext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_list_item, parent, false);

        TextView txtidLocal = (TextView) row.findViewById(R.id.txtListIdLocal);
        TextView txtSNIG = (TextView) row.findViewById(R.id.txtListSNIG);
        TextView txtRaza = (TextView) row.findViewById(R.id.txtListRaza);
        TextView txtFecNac = (TextView) row.findViewById(R.id.txtListFecNac);
        TextView txtPesoNac = (TextView) row.findViewById(R.id.txtListPesoNac);
        TextView txtTipoParto = (TextView) row.findViewById(R.id.txtListTipoParto);
        TextView txtSnigMadre = (TextView) row.findViewById(R.id.txtListSnigMadre);
        TextView txtSnigPadre = (TextView) row.findViewById(R.id.txtListSnigPadre);

        txtidLocal.setText("ID Local: " + terneras.get(position).getIdLocal());
        txtSNIG.setText("SNIG: " + terneras.get(position).getSnig());
        txtRaza.setText(conext.getString(R.string.ternera_raza) + terneras.get(position).getRaza().getRaza());
        txtFecNac.setText(conext.getString(R.string.ternera_fecNac) + ConstructorFecha.dateToString(terneras.get(position).getFecNac()));
        txtPesoNac.setText(conext.getString(R.string.ternera_pesoNac) + terneras.get(position).getPesoNac());
        txtTipoParto.setText(conext.getString(R.string.ternera_tipoParto) + terneras.get(position).getTipoParto().getTipoParto());
        txtSnigMadre.setText(conext.getString(R.string.ternera_snigMother) + terneras.get(position).getSnigMadre());
        try{
            if (terneras.get(position).getSnigPadre() != 0) {
                txtSnigPadre.setText(conext.getString(R.string.ternera_snigFather) + terneras.get(position).getSnigPadre());
            } else {
                txtSnigPadre.setText(conext.getString(R.string.ternera_snigFather) + conext.getString(R.string.desconocido));
            }
        }catch (Exception e){
            txtSnigPadre.setText(conext.getString(R.string.ternera_snigFather) + conext.getString(R.string.desconocido));
        }


        return row;
    }
}
