package com.proauge.chatroom;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proauge.chatroom.adapters.adapterMensaje;
import com.proauge.chatroom.models.mensaje;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    RecyclerView mensajesRV;
    Vector<mensaje> mensajes;
    TextInputEditText inputMensaje;
    Button botonSend;
    adapterMensaje adapterMensaje;
    int numMensaje=0;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference().child("mensajes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mensajesRV=(RecyclerView)findViewById(R.id.mensajes);
        inputMensaje=(TextInputEditText)findViewById(R.id.inputMensajes);
        botonSend=(Button)findViewById(R.id.botonSend);
        mensajes=new Vector<mensaje>();
        adapterMensaje=new adapterMensaje();
        adapterMensaje.setMensajes(mensajes);


        mensajesRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        mensajesRV.setLayoutManager(llm);
        mensajesRV.setHasFixedSize(true);
        mensajesRV.setItemViewCacheSize(1);
        mensajesRV.setDrawingCacheEnabled(true);
        mensajesRV.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mensajesRV.setAdapter(adapterMensaje);


        botonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaMensaje();
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try{
                    if(dataSnapshot.hasChild("Usuario") && dataSnapshot.hasChild("Mensaje")){
                        numMensaje++;
                        mensaje msj=new mensaje(dataSnapshot.child("Usuario").getValue(String.class),dataSnapshot.child("Mensaje").getValue(String.class));
                        mensajes.add(msj);
                        adapterMensaje.setMensajes(mensajes);
                        mensajesRV.setAdapter(adapterMensaje);
                        adapterMensaje.notifyDataSetChanged();
                        mensajesRV.scrollToPosition(mensajes.size()-1);
                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                numMensaje++;
                Log.i("Anadiendo",dataSnapshot.child("Usuario").getValue(String.class)+" "+dataSnapshot.child("Mensaje").getValue(String.class));
                mensaje msj=new mensaje(dataSnapshot.child("Usuario").getValue(String.class),dataSnapshot.child("Mensaje").getValue(String.class));
                mensajes.add(msj);
                adapterMensaje.setMensajes(mensajes);
                mensajesRV.setAdapter(adapterMensaje);
                adapterMensaje.notifyDataSetChanged();
                mensajesRV.scrollToPosition(mensajes.size()-1);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void enviaMensaje(){
        numMensaje++;
        reference.child("Mensaje"+Integer.toString(numMensaje)).child("Mensaje").setValue(inputMensaje.getText().toString());
        reference.child("Mensaje"+Integer.toString(numMensaje)).child("Usuario").setValue("Rigo");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
