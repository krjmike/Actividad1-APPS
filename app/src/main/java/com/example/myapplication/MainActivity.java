package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private EditText salarioBaseEditText;
    private EditText horasExtrasEditText;
    private CheckBox bonificacionesCheckBox;
    private Button calcularButton;
    private TextView resultadoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salarioBaseEditText = findViewById(R.id.salarioBaseEditText);
        horasExtrasEditText = findViewById(R.id.horasExtrasEditText);
        bonificacionesCheckBox = findViewById(R.id.bonificacionesCheckBox);
        calcularButton = findViewById(R.id.calcularButton);
        resultadoTextView = findViewById(R.id.resultadoTextView);

        calcularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularPagoEmpleado();
            }
        });
    }

    private void calcularPagoEmpleado() {
        String salarioBaseStr = salarioBaseEditText.getText().toString().trim();
        String horasExtrasStr = horasExtrasEditText.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (salarioBaseStr.isEmpty()) {
            salarioBaseStr = "0";
        }

        if (horasExtrasStr.isEmpty()) {
            horasExtrasStr = "0";
        }
        double salarioBase = Double.parseDouble(salarioBaseStr);
        int horasExtras = Integer.parseInt(horasExtrasStr);

        boolean tieneBonificaciones = bonificacionesCheckBox.isChecked();

        double valorHoraNormal = salarioBase / 192;
        double valorHorasExtras = horasExtras * valorHoraNormal * 1.25;
        double bonificaciones = tieneBonificaciones ? salarioBase * 0.05 : 0;
        double salarioTotalAntesDescuentos = salarioBase + valorHorasExtras + bonificaciones;

        double descuentoSalud = salarioTotalAntesDescuentos * 0.035;
        double descuentoPension = salarioTotalAntesDescuentos * 0.04;
        double descuentoCajaCompensacion = salarioTotalAntesDescuentos * 0.01;

        double salarioFinal = salarioTotalAntesDescuentos - descuentoSalud - descuentoPension - descuentoCajaCompensacion;

        resultadoTextView.setText(String.format("%.2f", salarioFinal));
    }
}