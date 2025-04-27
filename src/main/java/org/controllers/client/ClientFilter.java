package org.controllers.client;

import org.models.Person;

public class ClientFilter {
    public static boolean filterByCin(Person client, String cin) {
        if (cin == null || cin.isEmpty()) {
            return true;
        }

        String cinClient = String.valueOf(client.getCin());

        if (cinClient.contains(cin.toLowerCase())) {
            return true;
        }

        else return false;

    }
}
