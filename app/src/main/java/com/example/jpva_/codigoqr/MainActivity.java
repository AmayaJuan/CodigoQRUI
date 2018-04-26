package com.example.jpva_.codigoqr;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.scannerQR);
        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                switch (menuButton.getId()) {
                    case R.id.image:
                        showMessage("Image");
                        break;
                    case R.id.pdf:
                        showMessage("Pdf");
                        break;
                    case R.id.play:
                        showMessage("Play");
                        break;
                }
            }
        });

        circleMenu.setStateUpdateListener(new CircleMenu.OnStateUpdateListener() {
            @Override
            public void onMenuExpanded() {
                Log.d("CircleMenuStatus", "Expanded");
            }

            @Override
            public void onMenuCollapsed() {
                Log.d("CircleMenuStatus", "Collapsed");
            }
        });

    }
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
       super.onPause();
       scannerView.stopCamera();
    }
   @Override
    public void handleResult(Result result) {
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle("Scanner Result");
       builder.setMessage("Result "+ result.getText()+"\n" + "Format "+ result.getBarcodeFormat());
       AlertDialog alertDialog = builder.create();
       alertDialog.show();
       scannerView.resumeCameraPreview(this);
    }

    public void scanner(View view){
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
  }
}
