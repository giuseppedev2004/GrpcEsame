package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import it.univ.grpclab.CalculatorGrpc;
import it.univ.grpclab.OperationRequest;
import it.univ.grpclab.OperationResponse;

public class CalculatorClient {

    public static void main(String[] args) {

        // 1. CHANNEL: la "linea telefonica" verso il server
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()          // niente crittografia: va bene per i test
                .build();

        // 2. STUB: il "centralinista" che ha gli stessi metodi del server
        CalculatorGrpc.CalculatorBlockingStub stub = CalculatorGrpc.newBlockingStub(channel);

        // 3. LA RICHIESTA: i due numeri da mandare
        OperationRequest request = OperationRequest.newBuilder()
                .setOperandA(15)
                .setOperandB(4)
                .build();

        // 4. LE 4 CHIAMATE: sembrano locali, ma vanno al server
        try {
            OperationResponse r1 = stub.add(request);
            System.out.println(r1.getDescription());

            OperationResponse r2 = stub.subtract(request);
            System.out.println(r2.getDescription());

            OperationResponse r3 = stub.multiply(request);
            System.out.println(r3.getDescription());

            OperationResponse r4 = stub.divide(request);
            System.out.println(r4.getDescription());

        } catch (StatusRuntimeException e) {
            System.out.println("Errore: " + e.getStatus().getCode());
        }

        // 5. DIVISIONE PER ZERO: deve arrivare l'errore dal server
        OperationRequest richiestaZero = OperationRequest.newBuilder()
                .setOperandA(10)
                .setOperandB(0)
                .build();

        try {
            OperationResponse r5 = stub.divide(richiestaZero);
            System.out.println(r5.getDescription());
        } catch (StatusRuntimeException e) {
            System.out.println("Errore: " + e.getStatus().getCode()
                    + " - " + e.getStatus().getDescription());
        }

        // 6. CHIUDO la linea
        channel.shutdown();
    }
}
