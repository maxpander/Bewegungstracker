package com.example.dagri.googlemapsapi;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by DaGri on 01.06.16.
 */
public class LocationStore {

    // ATTRIBUTES

    /**
     * The ArrayList of LatLng to store the LatLng values.
     */
    private ArrayList<LatLng> latlon = new ArrayList<>();

    /**
     * The ArrayList of Strings to store the time stamps.
     */
    private ArrayList<String> times = new ArrayList<>();

    /**
     * The Logger used to log.
     */
    Logger LOG = Logger.getLogger(this.getClass().getCanonicalName());

    // CONSTRUCTORS

    /**
     * Empty constructor for a LocationStore.
     */
    public void LocationStore(){

    };

    // METHODS

    public void addLocation(double lat, double lon, String time){
        this.getLatLng().add(new LatLng(lat, lon));
        this.getTimes().add(time);
    }

    public void addLocation(LatLng latLng, String time){
        this.getLatLng().add(latLng);
        this.getTimes().add(time);
    }

    /**
     * Converts the internal stores locations to a XML structure.
     */
    public void toXML(){
        try {
            File ergXml = new File("LocationXML"+System.currentTimeMillis() + ".xml");
            FileWriter fw = new FileWriter(ergXml);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");

            bw.write("<userid>" + "useridnull" + "</userid>");
            bw.write("<locations>");
            for(int a=0;a<this.getLatLng().size();a++){
                bw.write("<location>");
                bw.write("<lat>" + this.getLatLng().get(a).latitude+"</lat>");
                bw.write("<lon>" + this.getLatLng().get(a).longitude+"</lon>");
                bw.write("<time>" + this.getTimes().get(a).toString()+"</lat>");
                bw.write("</location>");
            }
            bw.write("</locations>");
            bw.close();
        }
        catch (IOException e){
            LOG.severe("COULD NOT PARSE XML!");
        }
    }

    // GETTERS AND SETTERS

    public ArrayList<LatLng> getLatLng(){
        return this.latlon;
    }

    public ArrayList<String> getTimes(){
        return this.times;
    }

    // OTHER

}
