package com.engefoto;

import com.engefoto.kml.GenerateKMLMap;
import com.engefoto.view.Gui;

public class Main {
    public static void main(String argv[]) {
        GenerateKMLMap controllerKML = new GenerateKMLMap();
        new Gui(controllerKML);
    }
}
