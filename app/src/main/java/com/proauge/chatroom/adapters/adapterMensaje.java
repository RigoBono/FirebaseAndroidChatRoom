package com.proauge.chatroom.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proauge.chatroom.R;
import com.proauge.chatroom.models.mensaje;

import org.w3c.dom.Text;

import java.util.Vector;

/**
 * Created by rigobono on 14/10/16.
 */

public class adapterMensaje extends RecyclerView.Adapter<adapterMensaje.MensajeViewHolder> {

    Vector<mensaje> mensajes;

    public void setMensajes(Vector<mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public MensajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensaje_cardview, parent, false);
        MensajeViewHolder mvh = new MensajeViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MensajeViewHolder holder, int position) {
        holder.mensaje.setText(mensajes.elementAt(position).getMensaje());
        holder.usuarioSender.setText(mensajes.elementAt(position).getUsuario());
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    public class MensajeViewHolder extends RecyclerView.ViewHolder{

        ImageView imagenUsuarioSender;
        TextView usuarioSender;
        TextView mensaje;
        public MensajeViewHolder(View itemView) {
            super(itemView);
            mensaje=(TextView)itemView.findViewById(R.id.mensajeSender);
            usuarioSender=(TextView)itemView.findViewById(R.id.usuarioSender);
            imagenUsuarioSender=(ImageView)itemView.findViewById(R.id.imagenUsuarioSender);
        }
    }
}
