package com.example.hesapmakinesi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.*;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText display;
    Boolean enSonEsiteBasıldı=true;
    TextView sonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //klavyenin açılmasını engelliyor
        display = findViewById(R.id.editText);
        display.setShowSoftInputOnFocus(false);
        sonuc =findViewById(R.id.result);

        //ekrandaki yazı kısmına dokunulduğu zaman default gelen yazının kaldırılması
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getString(R.string.tap_here).equals(display.getText().toString())){
                    display.setText("");
                }
            }
        });

    }

    public void anyButton(View view) {
        if (enSonEsiteBasıldı){
            display.setText("");
            enSonEsiteBasıldı=false;
        }

        switch (view.getId()) {
            case R.id.c:{display.setText(""); sonuc.setText(""); break;}
            case R.id.parantez:addBrackets();break;
            case R.id.ust:updateDisplay("^");break;
            case R.id.bolme:updateDisplay("÷");break;
            case R.id.yedi:updateDisplay("7");break;
            case R.id.sekiz:updateDisplay("8");break;
            case R.id.dokuz:updateDisplay("9");break;
            case R.id.carpı:updateDisplay("x");break;
            case R.id.dort: updateDisplay("4");break;
            case R.id.bes:updateDisplay("5");break;
            case R.id.alti:updateDisplay("6");break;
            case R.id.eksi:updateDisplay("-");break;
            case R.id.bir:updateDisplay("1");break;
            case R.id.iki:updateDisplay("2");break;
            case R.id.uc:updateDisplay("3");break;
            case R.id.arti:updateDisplay("+");break;
            case R.id.ucsifir:updateDisplayTreeZero();break;
            case R.id.sifir:updateDisplay("0");break;
            case R.id.nokta:updateDisplay(".");break;
            case R.id.esittir:calculateTheResult(); break;
            case R.id.sil:backSpace();break;



        }
    }

    private void calculateTheResult() {
        String textDisplay =display.getText().toString();
        String reTextDisplay= textDisplay.replaceAll("÷","/");
        reTextDisplay= textDisplay.replaceAll("x","*");
        Expression ifade=new Expression(reTextDisplay);
        String result =String.valueOf(ifade.calculate()).toString();

        if(!result.equals("NaN")){
            display.setText("");

            sonuc.setText(result);

        }
        else {
            display.setText("error");
        }
        enSonEsiteBasıldı=true;
    }

    private void backSpace() {
        int cursorP= display.getSelectionStart();
        if (cursorP>0){
            String oldDisplay =display.getText().toString();
            String leftDisplay=oldDisplay.substring(0,cursorP-1);
            String rightDisplay=oldDisplay.substring(cursorP);
            String newText =leftDisplay+rightDisplay;
            display.setText(newText);
            display.setSelection(cursorP-1);

        }
    }

    private void updateDisplayTreeZero() {
        int cursorP= display.getSelectionStart();

        if (getString(R.string.tap_here).equals(display.getText().toString())){
            display.setText("000");
        }
        else{
            String oldDisplay =display.getText().toString();
            String leftDisplay=oldDisplay.substring(0,cursorP);
            String rightDisplay=oldDisplay.substring(cursorP);
            String newString =leftDisplay+"000"+rightDisplay;
            display.setText(newString);

        }
        display.setSelection(cursorP+3);
    }

    private void updateDisplay(String addCharToDisplay) {
        int cursorP= display.getSelectionStart();

        if (getString(R.string.tap_here).equals(display.getText().toString())){
            display.setText(addCharToDisplay);
        }
        else{
            String oldDisplay =display.getText().toString();
            String leftDisplay=oldDisplay.substring(0,cursorP);
            String rightDisplay=oldDisplay.substring(cursorP);
            String newString =leftDisplay+addCharToDisplay+rightDisplay;
            display.setText(newString);

        }
        display.setSelection(cursorP+1);
    }

    private void addBrackets() {
        String textDisplay =display.getText().toString();
        int cursorP = display.getSelectionStart();
        int countBrackets=0;
        for (int i =0;i<textDisplay.length();i++){
            if (textDisplay.substring(i,i+1).equalsIgnoreCase("(")) countBrackets++;
            if (textDisplay.substring(i,i+1).equalsIgnoreCase(")")) countBrackets--;
        }
        String lastCharOfTextDsiplay=textDisplay.substring(textDisplay.length()-1);
        if(countBrackets==0 || lastCharOfTextDsiplay.equals("(")) updateDisplay("(");
        else if(countBrackets>0 && !lastCharOfTextDsiplay.equals(")")) updateDisplay(")");



    }
}