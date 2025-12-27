package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.view.GaTauPanel;

public class GaTauController {
    private static GaTauPanel view;

    public GaTauController() {
        view = new GaTauPanel();
        view.addGaTau(new add_gatau());
        view.editGaTau(new edit_gatau());
        view.removeGaTau(new remove_gatau());
        view.setVisible(true);
    }

}
