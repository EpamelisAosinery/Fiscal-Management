module emu.edu.ema {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens emu.edu.fiscal to javafx.fxml;
    exports emu.edu.fiscal;
    exports emu.edu.fiscal.controller;
    opens emu.edu.fiscal.controller to javafx.fxml;
}