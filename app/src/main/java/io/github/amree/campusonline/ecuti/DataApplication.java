package io.github.amree.campusonline.ecuti;

public class DataApplication {

    String status;
    String url;
    String name;
    String type;
    String dateTime;

    public DataApplication(String params[]) {

        this.status   = params[0];
        this.url      = params[1];
        this.name     = params[2];
        this.type     = params[3];
        this.dateTime = params[4];
    }
}

