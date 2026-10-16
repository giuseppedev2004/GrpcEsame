package org.example;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.univ.grpclab.CalculatorGrpc;
import it.univ.grpclab.OperationRequest;
import it.univ.grpclab.OperationResponse;

public class CalculatorServiceImpl extends CalculatorGrpc.CalculatorImplBase {

    @Override
    public void add(OperationRequest request, StreamObserver<OperationResponse> responseObserver) {
        double a = request.getOperandA();
        double b = request.getOperandB();
        double result = a + b;

        OperationResponse response = OperationResponse.newBuilder()
                .setResult(result)
                .setDescription(a + " + " + b + " = " + result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void subtract(OperationRequest request, StreamObserver<OperationResponse> responseObserver) {
        double a = request.getOperandA();
        double b = request.getOperandB();
        double result = a - b;

        OperationResponse response = OperationResponse.newBuilder()
                .setResult(result)
                .setDescription(a + " - " + b + " = " + result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void multiply(OperationRequest request, StreamObserver<OperationResponse> responseObserver) {
        double a = request.getOperandA();
        double b = request.getOperandB();
        double result = a * b;

        OperationResponse response = OperationResponse.newBuilder()
                .setResult(result)
                .setDescription(a + " * " + b + " = " + result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void divide(OperationRequest request, StreamObserver<OperationResponse> responseObserver) {
        double a = request.getOperandA();
        double b = request.getOperandB();

        if (b == 0) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("Divisione per zero non consentita")
                            .asRuntimeException());
            return;
        }

        double result = a / b;

        OperationResponse response = OperationResponse.newBuilder()
                .setResult(result)
                .setDescription(a + " / " + b + " = " + result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}