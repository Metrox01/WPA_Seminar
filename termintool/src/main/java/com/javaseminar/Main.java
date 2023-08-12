package com.javaseminar;

import com.javaseminar.controller.AppointmentController;
import com.javaseminar.view.AppointmentView;

public class Main {
    public static void main(String[] args) {
        AppointmentController controller = new AppointmentController();
        new AppointmentView(controller);
    }
}

