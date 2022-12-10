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
import com.utec.pft.modelo.Enfermerdad.EnfermedadHistoricaDTO;

import java.util.List;

public class HistoricoAdapter extends ArrayAdapter<EnfermedadHistoricaDTO> {

    private Context conext;
    private List<EnfermedadHistoricaDTO> historicos;

    public HistoricoAdapter(@NonNull Context context, int resource, @NonNull List<EnfermedadHistoricaDTO> historicos) {
        super(context, resource, historicos);
        this.conext = context;
        this.historicos = historicos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {


            LayoutInflater inflater = (LayoutInflater) conext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.activity_list_historicos, parent, false);

            TextView txtSNIG = (TextView) row.findViewById(R.id.txtListHistoSNIG);
            TextView txtNombreEnfermedad = (TextView) row.findViewById(R.id.txtListNombreEnfermedad);
            TextView txtVariante = (TextView) row.findViewById(R.id.txtListVariante);
            TextView txtGravedad = (TextView) row.findViewById(R.id.txtListGravedad);
            TextView txtFecDeteccion = (TextView) row.findViewById(R.id.txtListFecDeteccion);

            txtSNIG.setText("SNIG:" + historicos.get(position).getSnig());
            txtNombreEnfermedad.setText(conext.getString(R.string.enfermedad_nombre) + historicos.get(position).getNombreEnfermedad());
            txtVariante.setText(conext.getString(R.string.enfermedad_variante) + historicos.get(position).getVariante());
            txtGravedad.setText(conext.getString(R.string.enfermedad_gravedad) + historicos.get(position).getSeveridad());
            txtFecDeteccion.setText(conext.getString(R.string.enfermedad_fecDeteccion) + ConstructorFecha.dateToString(historicos.get(position).getFecDeteccion()));

            return row;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
