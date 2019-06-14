package com.example.tab1;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class userFragment extends Fragment {
    private View vista;
    private EditText nombreET;
    private EditText idET;
    private EditText telefonoET;
    private EditText correoInstitucionalET;
    private EditText montoQRET;
    private ListView autosLV;
    private Button guardarPerfilBtn;
    private Button generarQRBtn;
    private Button agregarAutoBtn;
    private ImageView codigoQR;

    public userFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        vista=inflater.inflate(R.layout.fragment_user, container, false);
        nombreET=vista.findViewById(R.id.nombreUserET);
        idET=vista.findViewById(R.id.idUET);
        telefonoET=vista.findViewById(R.id.telefonoUET);
        correoInstitucionalET=vista.findViewById(R.id.correoUET);
        montoQRET=vista.findViewById(R.id.montoUET);
        guardarPerfilBtn=vista.findViewById(R.id.guardarPerfilBtn);
        generarQRBtn=vista.findViewById(R.id.qrCodeBtn);
        agregarAutoBtn=vista.findViewById(R.id.addCarrosUBtn);
        codigoQR=vista.findViewById(R.id.image);
        //Agregar autos
        agregarAutoBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent irAgregarAuto= new Intent(getActivity(),AgregarAuto.class);
                irAgregarAuto.putExtra("idKey",MenuBottom.getIdUser());
                startActivity(irAgregarAuto);
            }
        });
        //Guardar Perfil
        guardarPerfilBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
        //Crear Codigo
        generarQRBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String text2Qr = montoQRET.getText().toString().trim()+"\n"+MenuBottom.getIdUser();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    codigoQR.setImageBitmap(bitmap);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });
        return vista;
    }
}
