package com.example.googlemaps.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.googlemaps.Activities.MainActivity;
import com.example.googlemaps.Activities.NyttHusActivity;
import com.example.googlemaps.Model.Hus;
import com.example.googlemaps.R;
import com.example.googlemaps.ServerRepository.HusRepository;
import com.example.googlemaps.util.PolyUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener {
    private static final int COLOR_GREEN_ARGB = 0x7FCC5E24;
    private static final int COLOR_BUSY = 0x7F72a3ec;

    public List<Marker> list;
    private static final int COLOR_STROKE = 0xffCC5E24;
    private static final int COLOR_STROKE_BUSY = 0xff72a3ec;
    Polygon p35, p32, pp35, pp33, p40, p42, p46, p50, p48, p44, p52, stensberggata29, falbesgate5, stensberggata26_28;
    public GoogleMap googleMap2;
    ArrayList<Hus> hus;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maps, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap2 = googleMap;

            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style));


            list = new ArrayList<>();
            // Pilesteredet område:
            //10.735188,59.921054
            googleMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.921054, 10.735188), 16.0f));

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.setSelected((int) marker.getTag());
                    return true;
                }
            });
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.setSelected(-1);
                }
            });


            LatLngBounds adelaideBounds = new LatLngBounds(
                    //10.731282,59.918323,10.739222,59.923700
                    new LatLng(59.918323, 10.731282), // SW bounds
                    new LatLng(59.923700, 10.739222)  // NE bounds
            );
            googleMap.setMinZoomPreference(16.0f);
            googleMap.setMaxZoomPreference(21.0f);
// Constrain the camera target to the Adelaide bounds.
            googleMap.setLatLngBoundsForCameraTarget(adelaideBounds);


            /*
59.91991409900141, 10.735346666292775
59.91944626190218, 10.73472439380132
59.91929838098751, 10.734676114039052
59.919094035366484, 10.734611741022695
59.919247294700206, 10.735899201349843
59.919290314736834, 10.73597966762029

             */

            p35 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.91991409900141, 10.735346666292775),
                            new LatLng(59.91944626190218, 10.73472439380132),
                            new LatLng(59.91929838098751, 10.734676114039052),
                            new LatLng(59.919094035366484, 10.734611741022695),
                            new LatLng(59.919247294700206, 10.735899201349843),
                            new LatLng(59.919290314736834, 10.73597966762029)
                    ));

            p35.setFillColor(COLOR_GREEN_ARGB);
            p35.setStrokeWidth(4);
            p35.setStrokeColor(COLOR_STROKE);
            // Position the map's camera near Alice Springs in the center of Australia,
            // and set the zoom factor so most of Australia shows on the screen.


            /*
            59.92029133573247, 10.736273985869023
59.92019051047503, 10.735675853258702
59.919748220059994, 10.73595480299625
59.9197670410488, 10.73645905829105
             */


            p32 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.92029133573247, 10.736273985869023),
                            new LatLng(59.92019051047503, 10.735675853258702),
                            new LatLng(59.919748220059994, 10.73595480299625),
                            new LatLng(59.9197670410488, 10.73645905829105)));

            p32.setFillColor(COLOR_GREEN_ARGB);
            p32.setStrokeWidth(4);
            p32.setStrokeColor(COLOR_STROKE);

            /*
59.92016227934809, 10.736531477934452
59.920209331213016, 10.737263720995518
59.920169001047164, 10.737303954130741
59.920212019889014, 10.73782430267963
59.92006279804257, 10.737875264650913
59.919999613815314, 10.737271767622563
59.919928363372144, 10.737266403204533
59.91989072156686, 10.736619990831944
             */
            pp35 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(

                            new LatLng(59.92016227934809, 10.736531477934452),
                            new LatLng(59.920209331213016, 10.73726372099551),
                            new LatLng(59.920169001047164, 10.73730395413074),
                            new LatLng(59.920212019889014, 10.73782430267963),
                            new LatLng(59.92006279804257, 10.737875264650913),
                            new LatLng(59.919999613815314, 10.73727176762256),
                            new LatLng(59.919928363372144, 10.73726640320453),
                            new LatLng(59.91989072156686, 10.736619990831944)
                    ));
            pp35.setFillColor(COLOR_GREEN_ARGB);
            pp35.setStrokeWidth(4);
            pp35.setStrokeColor(COLOR_STROKE);


                /*
59.92044324520854, 10.737791130073248
59.92047954206005, 10.738330254085241
59.920415014296545, 10.738343665130316
59.92042173594443, 10.738394627101599
59.92033569874869, 10.738424131400762
59.92030612341112, 10.738142499454199
59.920255038675094, 10.738147863872229
59.9202442839838, 10.73784209204453
         */

            pp33 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(

                            new LatLng(59.92044324520854, 10.737791130073248),
                            new LatLng(59.92047954206005, 10.738330254085241),
                            new LatLng(59.920415014296545, 10.738343665130316),
                            new LatLng(59.92042173594443, 10.738394627101599),
                            new LatLng(59.92033569874869, 10.738424131400762),
                            new LatLng(59.92030612341112, 10.738142499454199),
                            new LatLng(59.920255038675094, 10.738147863872229),
                            new LatLng(59.9202442839838, 10.73784209204453)
                    ));
            pp33.setFillColor(COLOR_GREEN_ARGB);
            pp33.setStrokeWidth(4);
            pp33.setStrokeColor(COLOR_STROKE);




            /*
59.92103877742132, 10.733908279403378
59.92118799487926, 10.73424891994827
59.92107104071231, 10.734452767833401
59.92089762515438, 10.734069211944272
59.920943331668475, 10.733959241374661
59.92094602028496, 10.733889503940274
             */

            p40 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.92103877742132, 10.733908279403378),
                            new LatLng(59.92118799487926, 10.73424891994827),
                            new LatLng(59.92107104071231, 10.734452767833401),
                            new LatLng(59.92089762515438, 10.734069211944272),
                            new LatLng(59.920943331668475, 10.733959241374661),
                            new LatLng(59.92094602028496, 10.733889503940274)
                    ));
            p40.setFillColor(COLOR_GREEN_ARGB);
            p40.setStrokeWidth(4);
            p40.setStrokeColor(COLOR_STROKE);



            /*
59.92137648007326, 10.734291004089442
59.92141412019335, 10.734610186962215
59.921224574869115, 10.73474429741296
59.921195000323735, 10.73473893299493
59.921095522114, 10.734505580810634
59.92120172181363, 10.734282957462398
59.92124070642827, 10.73432855501565
             */

            stensberggata29 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.92137648007326, 10.734291004089442),
                            new LatLng(59.92141412019335, 10.734610186962215),
                            new LatLng(59.921224574869115, 10.73474429741296),
                            new LatLng(59.921195000323735, 10.73473893299493),
                            new LatLng(59.921095522114, 10.734505580810634),
                            new LatLng(59.92120172181363, 10.734282957462398),
                            new LatLng(59.92124070642827, 10.73432855501565)
                    ));
            stensberggata29.setFillColor(COLOR_GREEN_ARGB);
            stensberggata29.setStrokeWidth(4);
            stensberggata29.setStrokeColor(COLOR_STROKE);



            /*
59.921818748787786, 10.733722375778285
59.921900749330256, 10.734205173400966
59.92187924101084, 10.734221266655055
59.92188596236215, 10.734277593044368
59.92143831739083, 10.7346075047532
59.921385890107274, 10.734282957462398
59.92135765999721, 10.7341032494584
59.921320019813095, 10.734127389339534
59.92126759234265, 10.733826981929866
             */
            p42 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.921818748787786, 10.733722375778285),
                            new LatLng(59.921900749330256, 10.734205173400966),
                            new LatLng(59.92187924101084, 10.734221266655055),
                            new LatLng(59.92188596236215, 10.734277593044368),
                            new LatLng(59.92143831739083, 10.7346075047532),
                            new LatLng(59.921385890107274, 10.734282957462398),
                            new LatLng(59.92135765999721, 10.7341032494584),
                            new LatLng(59.921320019813095, 10.734127389339534),
                            new LatLng(59.92126759234265, 10.733826981929866)
                    ));
            p42.setFillColor(COLOR_GREEN_ARGB);
            p42.setStrokeWidth(4);
            p42.setStrokeColor(COLOR_STROKE);


            /*
59.92127967536184, 10.733533716678862
59.9212205263429, 10.733573949814085
59.921239346496726, 10.733670509338621
59.92115868861949, 10.73371342468286
59.92112373681181, 10.733595407486204
59.92104711156613, 10.733630276203398
59.921061898908015, 10.73367587375665
59.92101619255725, 10.73371878910089
59.92101888116788, 10.733756340027098
59.92092343535778, 10.733788526535276
59.920866974326806, 10.733488119125608
59.92097451906492, 10.733158207416777
59.92108475205981, 10.732927537441496
59.92118423030184, 10.73282561349893
59.921217837748635, 10.7329463129046
59.921193640390385, 10.732983863830809
             */

            p46 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.92127967536184, 10.733533716678862),
                            new LatLng(59.9212205263429, 10.733573949814085),
                            new LatLng(59.921239346496726, 10.733670509338621),
                            new LatLng(59.92115868861949, 10.73371342468286),
                            new LatLng(59.92112373681181, 10.733595407486204),
                            new LatLng(59.92104711156613, 10.733630276203398),
                            new LatLng(59.921061898908015, 10.73367587375665),
                            new LatLng(59.92101619255725, 10.73371878910089),
                            new LatLng(59.92101888116788, 10.733756340027098),
                            new LatLng(59.92092343535778, 10.733788526535276),
                            new LatLng(59.920866974326806, 10.733488119125608),
                            new LatLng(59.92097451906492, 10.733158207416777),
                            new LatLng(59.92108475205981, 10.732927537441496),
                            new LatLng(59.92118423030184, 10.73282561349893),
                            new LatLng(59.921217837748635, 10.7329463129046),
                            new LatLng(59.921193640390385, 10.732983863830809)
                    ));
            p46.setFillColor(COLOR_GREEN_ARGB);
            p46.setStrokeWidth(4);
            p46.setStrokeColor(COLOR_STROKE);



            /*
59.921619780174694, 10.73256275701547
59.92172732247316, 10.733265495777372
59.92167623992484, 10.73330841112161
59.92168833843024, 10.733383512974028
59.92159558310949, 10.733437157154325
59.92155659891181, 10.733222580433134
59.92148266323868, 10.733254766941313
59.9214974503865, 10.733391559601072
59.9214046945322, 10.733474708080534
59.92136705440143, 10.733265495777372
59.921326725642515, 10.733276224613432
59.921260855230884, 10.732881939888243
59.92123396931101, 10.732903397560362
59.921212460559424, 10.732790744781736
59.92130924983175, 10.732653952121977
59.921571386026685, 10.732495701790098
59.92159155026364, 10.732572144747023

             */

            p48 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.921619780174694, 10.73256275701547),
                            new LatLng(59.92172732247316, 10.733265495777372),
                            new LatLng(59.92167623992484, 10.73330841112161),
                            new LatLng(59.92168833843024, 10.733383512974028),
                            new LatLng(59.92159558310949, 10.733437157154325),
                            new LatLng(59.92155659891181, 10.733222580433134),
                            new LatLng(59.92148266323868, 10.733254766941313),
                            new LatLng(59.9214974503865, 10.733391559601072),
                            new LatLng(59.9214046945322, 10.733474708080534),
                            new LatLng(59.92136705440143, 10.733265495777372),
                            new LatLng(59.921326725642515, 10.733276224613432),
                            new LatLng(59.921260855230884, 10.732881939888243),
                            new LatLng(59.92123396931101, 10.732903397560362),
                            new LatLng(59.921212460559424, 10.732790744781736),
                            new LatLng(59.92130924983175, 10.732653952121977),
                            new LatLng(59.921571386026685, 10.732495701790098),
                            new LatLng(59.92159155026364, 10.732572144747023)
                    ));
            p48.setFillColor(COLOR_GREEN_ARGB);
            p48.setStrokeWidth(4);
            p48.setStrokeColor(COLOR_STROKE);

            /*
59.92215412753389, 10.73366279632967
59.922215963402365, 10.733999413561039
59.92220520934656, 10.734010142397098
59.922217979787455, 10.734113407444172
59.92197063563718, 10.734279704403095
59.9219545044329, 10.734180462669544
59.92193299614835, 10.734179121565036
59.921920897732164, 10.734148276161365
59.921920897732164, 10.734110725235157
59.92194979949686, 10.734091949772052
59.92188124178149, 10.733743262600116
             */
            p44 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.92215412753389, 10.73366279632967),
                            new LatLng(59.922215963402365, 10.733999413561039),
                            new LatLng(59.92220520934656, 10.734010142397098),
                            new LatLng(59.922217979787455, 10.734113407444172),
                            new LatLng(59.92197063563718, 10.734279704403095),
                            new LatLng(59.9219545044329, 10.734180462669544),
                            new LatLng(59.92193299614835, 10.734179121565036),
                            new LatLng(59.921920897732164, 10.734148276161365),
                            new LatLng(59.921920897732164, 10.734110725235157),
                            new LatLng(59.92194979949686, 10.734091949772052),
                            new LatLng(59.92188124178149, 10.733743262600116)
                    ));
            p44.setFillColor(COLOR_GREEN_ARGB);
            p44.setStrokeWidth(4);
            p44.setStrokeColor(COLOR_STROKE);


            /*
59.92200430262242, 10.73222512504433
59.92216023703458, 10.733145122736438
59.92188600706233, 10.733346288412555
59.92182148203361, 10.732943957060321
59.92176367825554, 10.73298687240456
59.92169377587776, 10.732555036753162
59.92165344751589, 10.732571130007251
59.92163731615744, 10.732439701765522

             */

            p50 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.92200430262242, 10.73222512504433),
                            new LatLng(59.92216023703458, 10.733145122736438),
                            new LatLng(59.92188600706233, 10.733346288412555),
                            new LatLng(59.92182148203361, 10.732943957060321),
                            new LatLng(59.92176367825554, 10.73298687240456),
                            new LatLng(59.92169377587776, 10.732555036753162),
                            new LatLng(59.92165344751589, 10.732571130007251),
                            new LatLng(59.92163731615744, 10.732439701765522)
                    ));
            p50.setFillColor(COLOR_GREEN_ARGB);
            p50.setStrokeWidth(4);
            p50.setStrokeColor(COLOR_STROKE);


            /*
59.92289306092961, 10.732048923939942
59.92294414160478, 10.73336320635724
59.92277208006948, 10.733406121701478
59.92244946228602, 10.733304197758912
59.922444085296405, 10.733180816144227
59.92239300385143, 10.733196909398316
59.92234729933405, 10.732461984128236
59.92214028396692, 10.732520992726563
59.92214028396692, 10.732156212300538
             */


            p52 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.92289306092961, 10.732048923939942),
                            new LatLng(59.92294414160478, 10.73336320635724),
                            new LatLng(59.92277208006948, 10.733406121701478),
                            new LatLng(59.92244946228602, 10.733304197758912),
                            new LatLng(59.922444085296405, 10.733180816144227),
                            new LatLng(59.92239300385143, 10.733196909398316),
                            new LatLng(59.92234729933405, 10.732461984128236),
                            new LatLng(59.92214028396692, 10.732520992726563),
                            new LatLng(59.92214028396692, 10.732156212300538)
                    ));
            p52.setFillColor(COLOR_GREEN_ARGB);
            p52.setStrokeWidth(4);
            p52.setStrokeColor(COLOR_STROKE);


            /*
59.92189293923753, 10.734548742741822
59.92200585771227, 10.73511737105298
59.92169667525998, 10.73534267661023
59.92158106716828, 10.734731132954835
             */
            falbesgate5 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.92189293923753, 10.734548742741822),
                            new LatLng(59.92200585771227, 10.73511737105298),
                            new LatLng(59.92169667525998, 10.73534267661023),
                            new LatLng(59.92158106716828, 10.734731132954835)
                    ));
            falbesgate5.setFillColor(COLOR_GREEN_ARGB);
            falbesgate5.setStrokeWidth(4);
            falbesgate5.setStrokeColor(COLOR_STROKE);


            /*
59.92106351437858, 10.735648448437928
59.921206010237746, 10.735593463153123
59.921165681283, 10.735152239770173
59.92071130500743, 10.73408203837323
59.92056611837705, 10.734521920651673
59.920669631132206, 10.734645302266358
59.92072205954741, 10.73448705193448
59.920812128682584, 10.734610433549165
59.92077179924932, 10.73471235749173
59.92101041435013, 10.735239411563157
             */


            stensberggata26_28 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(59.92106351437858, 10.735648448437928),
                            new LatLng(59.921206010237746, 10.735593463153123),
                            new LatLng(59.921165681283, 10.735152239770173),
                            new LatLng(59.92071130500743, 10.73408203837323),
                            new LatLng(59.92056611837705, 10.734521920651673),
                            new LatLng(59.920669631132206, 10.734645302266358),
                            new LatLng(59.92072205954741, 10.73448705193448),
                            new LatLng(59.920812128682584, 10.734610433549165),
                            new LatLng(59.92077179924932, 10.73471235749173),
                            new LatLng(59.92101041435013, 10.735239411563157)
                    ));
            stensberggata26_28.setFillColor(COLOR_GREEN_ARGB);
            stensberggata26_28.setStrokeWidth(4);
            stensberggata26_28.setStrokeColor(COLOR_STROKE);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  Marker stensberggata26_28_Marker = addText(getContext(),googleMap,getCenterPointInPolygon(stensberggata26_28),"S26-28",2,10);
/*
final Context context, final GoogleMap map,
                    final LatLng location, final String text, final int padding,
                    final int fontSize)
 */


            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {


                @Override
                public void onMapLongClick(LatLng latLng) {

                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getContext(), Locale.getDefault());
                    boolean inPolygonp35 = PolyUtil.containsLocation(latLng, p35.getPoints(), p35.isGeodesic());
                    boolean inPolygonp50 = PolyUtil.containsLocation(latLng, p50.getPoints(), p50.isGeodesic());
                    boolean inPolygonp32 = PolyUtil.containsLocation(latLng, p32.getPoints(), p32.isGeodesic());
                    boolean inPolygonpp35 = PolyUtil.containsLocation(latLng, pp35.getPoints(), pp35.isGeodesic());
                    boolean inPolygonpp33 = PolyUtil.containsLocation(latLng, pp33.getPoints(), pp33.isGeodesic());
                    boolean inPolygonp40 = PolyUtil.containsLocation(latLng, p40.getPoints(), p40.isGeodesic());
                    boolean inPolygonp42 = PolyUtil.containsLocation(latLng, p42.getPoints(), p42.isGeodesic());
                    boolean inPolygonp46 = PolyUtil.containsLocation(latLng, p46.getPoints(), p46.isGeodesic());
                    boolean inPolygonp48 = PolyUtil.containsLocation(latLng, p48.getPoints(), p48.isGeodesic());
                    boolean inPolygonp44 = PolyUtil.containsLocation(latLng, p44.getPoints(), p44.isGeodesic());
                    boolean inPolygonp52 = PolyUtil.containsLocation(latLng, p52.getPoints(), p52.isGeodesic());
                    boolean inPolygonstensberggata29 = PolyUtil.containsLocation(latLng, stensberggata29.getPoints(), stensberggata29.isGeodesic());
                    boolean inPolygonstensberggata26_28 = PolyUtil.containsLocation(latLng, stensberggata26_28.getPoints(), stensberggata26_28.isGeodesic());
                    boolean inPolygonfalbesgate5 = PolyUtil.containsLocation(latLng, falbesgate5.getPoints(), falbesgate5.isGeodesic());


                    if (inPolygonp35) {
                        if (p35.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(p35);
                        }
                    }
                    if (inPolygonp50) {
                        if (p50.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(p50);
                        }
                    }
                    if (inPolygonp32) {
                        if (p32.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(p32);
                        }
                    }
                    if (inPolygonpp35) {
                        if (pp35.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(pp35);
                        }
                    }
                    if (inPolygonpp33) {
                        if (pp33.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(pp33);
                        }
                    }
                    if (inPolygonp40) {
                        if (p40.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(p40);
                        }
                    }
                    if (inPolygonp42) {
                        if (p42.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(p42);
                        }
                    }
                    if (inPolygonp46) {
                        if (p46.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(p46);
                        }
                    }
                    if (inPolygonp48) {
                        if (p48.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(p48);
                        }
                    }
                    if (inPolygonp44) {
                        if (p44.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(p44);
                        }
                    }
                    if (inPolygonp52) {
                        if (p52.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(p52);
                        }
                    }
                    if (inPolygonstensberggata29) {
                        if (stensberggata29.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(stensberggata29);
                        }
                    }
                    if (inPolygonstensberggata26_28) {
                        if (stensberggata26_28.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(stensberggata26_28);
                        }
                    }
                    if (inPolygonfalbesgate5) {
                        if (falbesgate5.getTag() == "Busy") {
                            latLng = null;
                        } else {
                            latLng = getCenterPointInPolygon(falbesgate5);
                        }
                    }


                    if (inPolygonp35 || inPolygonp50 || inPolygonp32 || inPolygonpp35 || inPolygonpp33 || inPolygonp40 ||
                            inPolygonp42 || inPolygonp46 || inPolygonp48 || inPolygonp44 || inPolygonp52 ||
                            inPolygonstensberggata29 || inPolygonstensberggata26_28 || inPolygonfalbesgate5) {
                        try {
                            if (latLng == null) {
                                Toast toast = Toast.makeText(getContext(),
                                        "Du prøver å lagre et hus som allerede finnes",
                                        Toast.LENGTH_SHORT);

                                toast.show();
                            } else {
                                Intent i = new Intent(getContext(), NyttHusActivity.class);
                                i.putExtra("lat", latLng.latitude);
                                i.putExtra("long", latLng.longitude);
                                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                                String address = addresses.get(0).getAddressLine(0);
                                i.putExtra("adresse", address);
                                startActivityForResult(i, 101);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast toast = Toast.makeText(getContext(),
                                "Du prøver å lagre et hus i et område utenfor oslomet sine bygg",
                                Toast.LENGTH_SHORT);

                        toast.show();
                    }
                }
            });
            settAlleHusMarker(googleMap);
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 23) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        System.out.println("marker clicked");
        return true;
    }


    void settAlleHusMarker(GoogleMap googleMap) {

        //TODO app crasjer når ingen hus registerert!

        HusRepository task = new HusRepository();
        hus = task.hentAlleHus();

        LatLng pilestredet;
        if (hus != null) {
            for (Hus hus2 : hus) {
                System.out.println(hus2.toString());

                String[] str = hus2.getGps().split(",");

                pilestredet = new LatLng(Double.parseDouble(str[0]), Double.parseDouble(str[1]));

                //    Polygon p35,p32,pp35,pp33,p40,p42,p46,p50,p48,p44,p52,stensberggata29,falbesgate5,stensberggata26_28;
                boolean inPolygonp35 = PolyUtil.containsLocation(pilestredet, p35.getPoints(), p35.isGeodesic());
                boolean inPolygonp50 = PolyUtil.containsLocation(pilestredet, p50.getPoints(), p50.isGeodesic());
                boolean inPolygonp32 = PolyUtil.containsLocation(pilestredet, p32.getPoints(), p32.isGeodesic());
                boolean inPolygonpp35 = PolyUtil.containsLocation(pilestredet, pp35.getPoints(), pp35.isGeodesic());
                boolean inPolygonpp33 = PolyUtil.containsLocation(pilestredet, pp33.getPoints(), pp33.isGeodesic());
                boolean inPolygonp40 = PolyUtil.containsLocation(pilestredet, p40.getPoints(), p40.isGeodesic());
                boolean inPolygonp42 = PolyUtil.containsLocation(pilestredet, p42.getPoints(), p42.isGeodesic());
                boolean inPolygonp46 = PolyUtil.containsLocation(pilestredet, p46.getPoints(), p46.isGeodesic());
                boolean inPolygonp48 = PolyUtil.containsLocation(pilestredet, p48.getPoints(), p48.isGeodesic());
                boolean inPolygonp44 = PolyUtil.containsLocation(pilestredet, p44.getPoints(), p44.isGeodesic());
                boolean inPolygonp52 = PolyUtil.containsLocation(pilestredet, p52.getPoints(), p52.isGeodesic());
                boolean inPolygonstensberggata29 = PolyUtil.containsLocation(pilestredet, stensberggata29.getPoints(), stensberggata29.isGeodesic());
                boolean inPolygonstensberggata26_28 = PolyUtil.containsLocation(pilestredet, stensberggata26_28.getPoints(), stensberggata26_28.isGeodesic());
                boolean inPolygonfalbesgate5 = PolyUtil.containsLocation(pilestredet, falbesgate5.getPoints(), falbesgate5.isGeodesic());

                if (inPolygonp35 || inPolygonp50 || inPolygonp32 || inPolygonpp35 || inPolygonpp33 || inPolygonp40 ||
                        inPolygonp42 || inPolygonp46 || inPolygonp48 || inPolygonp44 || inPolygonp52 ||
                        inPolygonstensberggata29 || inPolygonstensberggata26_28 || inPolygonfalbesgate5) {

                    int diameter = 60;
                    Bitmap bm = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_4444);
                    Canvas canvas = new Canvas(bm);
                    Paint txtpaint = new Paint();
                    Paint circlePaint = new Paint();
                    txtpaint.setColor(Color.WHITE);
                    txtpaint.setTextSize(35f);
                    txtpaint.setFakeBoldText(true);
                    txtpaint.setAntiAlias(true);
                    txtpaint.setTextAlign(Paint.Align.CENTER);
                    String text = Integer.toString(hus2.getEtasjer());
                    Rect bounds = new Rect();
                    txtpaint.getTextBounds(text, 0, text.length(), bounds);
                    int cl = ContextCompat.getColor(getContext(), R.color.orange);
                    circlePaint.setColor(cl);
                    circlePaint.setAntiAlias(true);
                    int xPos = (canvas.getWidth() / 2);
                    int yPos = (int) ((canvas.getHeight() / 2) - ((txtpaint.descent() + txtpaint.ascent()) / 2));
                    canvas.drawCircle(diameter / 2, diameter / 2, diameter / 2, circlePaint);
                    canvas.drawText(text, xPos, yPos, txtpaint);

                    String snippet = hus2.getGateadresse().split(",")[0];
                    Marker mark = googleMap.addMarker(new MarkerOptions()
                            .position(pilestredet).icon(BitmapDescriptorFactory.fromBitmap(bm))
                            .title(snippet));
                    mark.setTag(hus2.getId());
                    list.add(mark);
                    System.out.println(mark.getTag() + "-----------------------------------------------------------------------------------------------------------------------------------");
                }
                if (inPolygonp35) {
                    p35.setFillColor(COLOR_BUSY);
                    p35.setStrokeColor(COLOR_STROKE_BUSY);
                    p35.setTag("Busy");
                }
                if (inPolygonp50) {
                    p50.setFillColor(COLOR_BUSY);
                    p50.setStrokeColor(COLOR_STROKE_BUSY);
                    p50.setTag("Busy");
                }
                if (inPolygonp32) {
                    p32.setFillColor(COLOR_BUSY);
                    p32.setStrokeColor(COLOR_STROKE_BUSY);
                    p32.setTag("Busy");
                }
                if (inPolygonpp35) {
                    pp35.setFillColor(COLOR_BUSY);
                    pp35.setStrokeColor(COLOR_STROKE_BUSY);
                    pp35.setTag("Busy");
                }
                if (inPolygonpp33) {
                    pp33.setFillColor(COLOR_BUSY);
                    pp33.setStrokeColor(COLOR_STROKE_BUSY);
                    pp33.setTag("Busy");
                }
                if (inPolygonp40) {
                    p40.setFillColor(COLOR_BUSY);
                    p40.setStrokeColor(COLOR_STROKE_BUSY);
                    p40.setTag("Busy");
                }
                if (inPolygonp42) {
                    p42.setFillColor(COLOR_BUSY);
                    p42.setStrokeColor(COLOR_STROKE_BUSY);
                    p42.setTag("Busy");
                }
                if (inPolygonp46) {
                    p46.setFillColor(COLOR_BUSY);
                    p46.setStrokeColor(COLOR_STROKE_BUSY);
                    p46.setTag("Busy");
                }
                if (inPolygonp48) {
                    p48.setFillColor(COLOR_BUSY);
                    p48.setStrokeColor(COLOR_STROKE_BUSY);
                    p48.setTag("Busy");
                }
                if (inPolygonp44) {
                    p44.setFillColor(COLOR_BUSY);
                    p44.setStrokeColor(COLOR_STROKE_BUSY);
                    p44.setTag("Busy");
                }
                if (inPolygonp52) {
                    p52.setFillColor(COLOR_BUSY);
                    p52.setStrokeColor(COLOR_STROKE_BUSY);
                    p52.setTag("Busy");
                }
                if (inPolygonstensberggata29) {
                    stensberggata29.setFillColor(COLOR_BUSY);
                    stensberggata29.setStrokeColor(COLOR_STROKE_BUSY);
                    stensberggata29.setTag("Busy");
                }
                if (inPolygonstensberggata26_28) {
                    stensberggata26_28.setFillColor(COLOR_BUSY);
                    stensberggata26_28.setStrokeColor(COLOR_STROKE_BUSY);
                    stensberggata26_28.setTag("Busy");
                }
                if (inPolygonfalbesgate5) {
                    falbesgate5.setFillColor(COLOR_BUSY);
                    falbesgate5.setStrokeColor(COLOR_STROKE_BUSY);
                    falbesgate5.setTag("Busy");
                }


            }
        } else {
            Toast toast = Toast.makeText(getContext(),
                    "Du har ingen registrerte hus\"",
                    Toast.LENGTH_SHORT);

            toast.show();
        }

    }

    Marker addText(final Context context, final GoogleMap map,
                   final LatLng location, final String text, final int padding,
                   final int fontSize) {
        Marker marker = null;

        if (context == null || map == null || location == null || text == null
                || fontSize <= 0) {
            return marker;
        }

        final TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(fontSize);

        final Paint paintText = textView.getPaint();

        final Rect boundsText = new Rect();
        paintText.getTextBounds(text, 0, textView.length(), boundsText);
        paintText.setTextAlign(Paint.Align.CENTER);

        final Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        final Bitmap bmpText = Bitmap.createBitmap(boundsText.width() + 2
                * padding, boundsText.height() + 2 * padding, conf);

        final Canvas canvasText = new Canvas(bmpText);
        paintText.setColor(Color.BLACK);

        canvasText.drawText(text, canvasText.getWidth() / 2,
                canvasText.getHeight() - padding - boundsText.bottom, paintText);

        final MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .icon(BitmapDescriptorFactory.fromBitmap(bmpText))
                .anchor(0.5f, 1);
        marker = map.addMarker(markerOptions);

        return marker;
    }


    public void activateMarker(int id) {
        for (Marker mark : list) {
            if ((int) mark.getTag() == id) {
                mark.showInfoWindow();
                googleMap2.animateCamera(CameraUpdateFactory.newLatLng(mark.getPosition()));
                break;
            }
        }
    }

    public void unactivate() {
        for (Marker mark : list) {
            mark.hideInfoWindow();
            break;
        }
    }


    LatLng getCenterPointInPolygon(Polygon polygon) {
        LatLngBounds.Builder latLngBounds = LatLngBounds.builder();
        for (LatLng latLng : polygon.getPoints()) {
            latLngBounds.include(latLng);
        }
        return latLngBounds.build().getCenter();
    }

}

