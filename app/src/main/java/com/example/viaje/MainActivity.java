package com.example.viaje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etCodigoViaje,etCiudadDestino,etCantidadPersonas,etValorPersona;
    Button btnGuardar,btnConsultar,btnAnular,btnCancelar;
    String codigoViaje;
    int sw;
    long resp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        etCodigoViaje = findViewById(R.id.etcodviaje);
        etCiudadDestino = findViewById(R.id.etciudaddestino);
        etCantidadPersonas = findViewById(R.id.etcantpersonas);
        etValorPersona = findViewById(R.id.etvalorpersona);
        btnAnular = findViewById(R.id.btnanular);
        btnCancelar = findViewById(R.id.btncancelar);
        btnConsultar=findViewById(R.id.btnconsultar);
        btnGuardar = findViewById(R.id.btnadicionar);
        sw=0;

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){Registrar();}
        });
        btnConsultar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){consultarRegistro();}
        });
        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){limpiarCampos();}
        });
        btnAnular.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){anular();}
        });
    }

    public void Registrar(){
        String ciudadDestino,cantidadPersonas,valorPersona;
        codigoViaje=etCodigoViaje.getText().toString();
        ciudadDestino=etCiudadDestino.getText().toString();
        cantidadPersonas=etCantidadPersonas.getText().toString();
        valorPersona=etValorPersona.getText().toString();
        if (codigoViaje.isEmpty()||ciudadDestino.isEmpty()||cantidadPersonas.isEmpty()||valorPersona.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            etCodigoViaje.requestFocus();
        }else{
            Conexion_Viajes admin = new Conexion_Viajes(this,"registro.bd",null,1);
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues dato = new ContentValues();
            dato.put("codigo",codigoViaje);
            dato.put("ciudad",ciudadDestino);
            dato.put("personas",cantidadPersonas);
            dato.put("costo",valorPersona);
            if (sw==0){
                resp=db.insert("TblRegistro",null,dato);
            }else{
                sw=0;
                resp=db.update("TblRegistro",dato,"codigo='"+codigoViaje+"'",null);
            }
            if (resp>0){
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }else{
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();

            }
            db.close();
        }
    }
    public void limpiarCampos(){
        sw=0;
        etCodigoViaje.setText("");
        etCantidadPersonas.setText("");
        etCiudadDestino.setText("");
        etValorPersona.setText("");
    }

    public void consultarRegistro(){
        codigoViaje=etCodigoViaje.getText().toString();
        if (codigoViaje.isEmpty()){
            Toast.makeText(this, "Codigo del viaje requerido", Toast.LENGTH_SHORT).show();
            etCodigoViaje.requestFocus();
        }else{
            Conexion_Viajes admin = new Conexion_Viajes(this,"registro.bd",null,1);
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery("Select * from TblRegistro where codigo='"+codigoViaje+"'",null);
            if (fila.moveToNext()){
                sw=1;
                Toast.makeText(this, "Registro Encontrado", Toast.LENGTH_SHORT).show();
                etCiudadDestino.setText(fila.getString(1));
                etCantidadPersonas.setText(fila.getString(2));
                etValorPersona.setText(fila.getString(3));
            }else{
                Toast.makeText(this, "Registro no existe ", Toast.LENGTH_SHORT).show();
            }
            db.close();

        }
    }

    public void anular(){
        if (sw==1){
            Conexion_Viajes admin = new Conexion_Viajes(this,"registro.bd",null,1);
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues dato = new ContentValues();
            dato.put("codigo",codigoViaje);
            dato.put("activo","no");
            resp=db.update("TblRegistro",dato,"codigo='"+codigoViaje+"'",null);
            if (resp>0){
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }else{
                Toast.makeText(this, "Error al anular registro", Toast.LENGTH_SHORT).show();
            }db.close();
        }
    }

}