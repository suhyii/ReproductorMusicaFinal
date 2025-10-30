package com.example.reproductormusicafinal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class AdaptadorCancion extends BaseAdapter {

    private Context context;
    private List<Cancion> listaCanciones;

    public AdaptadorCancion(Context context, List<Cancion> listaCanciones) {
        this.context = context;
        this.listaCanciones = listaCanciones;
    }

    @Override
    public int getCount() {
        return listaCanciones.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCanciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cancion, parent, false);
        }

        Cancion c = listaCanciones.get(position);

        ImageView img = convertView.findViewById(R.id.imgPortada);
        TextView txt = convertView.findViewById(R.id.txtTitulo);

        img.setImageResource(c.getPortada());
        txt.setText(c.getTitulo());
        txt.setTextColor(context.getResources().getColorStateList(R.color.selector_color));

        return convertView;
    }
}
