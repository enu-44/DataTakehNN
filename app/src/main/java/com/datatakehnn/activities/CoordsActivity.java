package com.datatakehnn.activities;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.services.coords.CoordsService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CoordsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coords);
        ButterKnife.bind(this);


        if (savedInstanceState == null) {
            PlaceholderFragment newFragment = new PlaceholderFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, newFragment)
                    .commit();
        }
    }


    public static class PlaceholderFragment extends Fragment {

        @BindView(R.id.ivCoordPoste)
        ImageView ivCoordPoste;
        Unbinder unbinder;

        double latitud;
        double longitud;

        Location location;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ///  return super.onCreateView(inflater, container, savedInstanceState);

            View rootView = inflater.inflate(R.layout.fragment_coords, container, false);

            //Llama la instancia del servicio
            CoordsService servicio = new CoordsService(getActivity());

            //Setea el texto de las coordenadas
            servicio.setView(rootView.findViewById(R.id.textCoordenada));

            //Guarda en un location la ubicación
            location = servicio.getUbicacion();

            unbinder = ButterKnife.bind(this, rootView);

            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            unbinder.unbind();
        }

        @OnClick(R.id.ivCoordPoste)
        public void onCoordClicked() {
            latitud = location.getLatitude();
            longitud = location.getLongitude();
            Toast.makeText(getActivity(), "latitud : " + latitud, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
