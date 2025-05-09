package org.acme.grpc;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;
import org.acme.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.hellops.authserver.proto.AuthGrpc;
import xyz.hellops.authserver.proto.LoginRequest;
import xyz.hellops.authserver.proto.LoginResponse;
import xyz.hellops.authserver.proto.ResponseStatus;

@GrpcService
public class AuthService extends AuthGrpc.AuthImplBase  {
    private final Logger log = LoggerFactory.getLogger(AuthService.class.getName());

    @Inject
    PlayerService playerService;

    @Override
    public void login(LoginRequest req, StreamObserver<LoginResponse> responseObserver) {
        log.debug("Login request from {}", req.getLogin());

        ResponseStatus status = req.getPasswordHash().equals(playerService.getPlayerByLogin(req.getLogin()).passwordHash()) ?
                ResponseStatus.OK : ResponseStatus.FORBIDDEN;

        LoginResponse reply = LoginResponse.newBuilder()
                .setStatus(status)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}