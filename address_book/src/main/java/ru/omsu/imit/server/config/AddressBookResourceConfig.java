package ru.omsu.imit.server.config;

import org.glassfish.jersey.server.ResourceConfig;

public class AddressBookResourceConfig extends ResourceConfig {
    public AddressBookResourceConfig() {
        packages("ru.omsu.imit.resources", "ru.omsu.imit.rest.mappers");
    }
}
