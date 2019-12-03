package br.com.maccommerce.authservice;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;

import java.io.IOException;

class DatabaseMock {

    private final EmbeddedPostgres embeddedPostgres;

    DatabaseMock() throws IOException {
        this.embeddedPostgres = EmbeddedPostgres.builder().setPort(5433).start();
    }

    void stopServer() throws IOException {
        embeddedPostgres.close();
    }

}
