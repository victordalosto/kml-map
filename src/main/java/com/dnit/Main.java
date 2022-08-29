package com.dnit;

import com.dnit.kml.GenerateKMLMap;
import com.dnit.view.Gui;

public class Main {
    public static void main(String argv[]) {
        GenerateKMLMap controllerKML = new GenerateKMLMap();
        new Gui(controllerKML);
    }
}
