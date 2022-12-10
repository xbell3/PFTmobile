package com.utec.pft.controlador.enfermedad;

import android.content.Context;
import android.widget.Toast;

import com.utec.pft.api.ServiceApiEnfermedad;
import com.utec.pft.controlador.ConstructorFecha;
import com.utec.pft.modelo.Enfermerdad.EnfermedadDTO;
import com.utec.pft.modelo.Enfermerdad.EnfermedadHistoricaDTO;
import com.utec.pft.util.ConnectionRest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LeerExcel {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat decimalFormatter = new DecimalFormat("#");
    List<EnfermedadDTO> enfermedades = new ArrayList<>();
    private Context mContext;

    public LeerExcel(Context context) {
        mContext = context;//Al crear una instancia de esta clase se necesita pasarle el context para generar mensajes
    }

    public boolean cargar(File archivo) throws IOException {//Metodo para cargar xls a BD
        FileInputStream fis = new FileInputStream(archivo);
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);
        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        HashMap<String, String> mapaDeteccion = new HashMap<>();
        List<EnfermedadHistoricaDTO> enfermedades = new ArrayList<>();
        try {
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {//Se recorre el xls y se agregan a la lista de EnfemermedadesHistoricasDTO
                EnfermedadHistoricaDTO enfermedadHistorica = new EnfermedadHistoricaDTO();
                enfermedadHistorica.setIdTernera((int) sheet.getRow(i).getCell(0).getNumericCellValue());
                enfermedadHistorica.setNombreEnfermedad(sheet.getRow(i).getCell(1).getStringCellValue());
                enfermedadHistorica.setVariante(sheet.getRow(i).getCell(2).getStringCellValue());
                enfermedadHistorica.setSeveridad(Integer.valueOf((int) sheet.getRow(i).getCell(3).getNumericCellValue()).toString());
                String fecRegistro = dateFormat.format(sheet.getRow(i).getCell(4).getDateCellValue());
                enfermedadHistorica.setFecDeteccion(ConstructorFecha.stringToDate(fecRegistro));
                enfermedades.add(enfermedadHistorica);
            }
            ServiceApiEnfermedad api = ConnectionRest.getConnection().create(ServiceApiEnfermedad.class);
            Call<List<EnfermedadHistoricaDTO>> call = api.cargaHistoricos(enfermedades);//Se envia al servidor la lista
            call.enqueue(new Callback<List<EnfermedadHistoricaDTO>>() {
                @Override
                public void onResponse(Call<List<EnfermedadHistoricaDTO>> call, Response<List<EnfermedadHistoricaDTO>> response) {
                    if (response.body() != null) {//Si devuelve algo significa que hubo respuesta de la BD
                        Toast.makeText(mContext, "Se cargo el archivo exitosamente", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "Error al cargar archivo", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<EnfermedadHistoricaDTO>> call, Throwable t) {
                    Toast.makeText(mContext, "Error al cargar archivo", Toast.LENGTH_LONG).show();
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
