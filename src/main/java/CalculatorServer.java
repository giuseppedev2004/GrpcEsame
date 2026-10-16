package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        // 1. COSTRUISCO il server: porta 50051 + il servizio che sa fare i calcoli
        Server server = ServerBuilder.forPort(50051)
                .addService(new CalculatorServiceImpl())
                .build();

        // 2. LO ACCENDO
        server.start();
        System.out.println("Server avviato sulla porta 50051");

        // 3. HOOK DI SHUTDOWN: quando chiudi il programma, spegne il server in modo ordinato
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Spegnimento del server...");
            server.shutdown();
        }));

        // 4. RESTO IN ATTESA di richieste, finché qualcuno non mi spegne
        server.awaitTermination();
    }
}
