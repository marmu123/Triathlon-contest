package grpc;

import domain.Arbitru;
import domain.Participant;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import domain.Rezultat;
import proto.GRPCServicesGrpc;
import proto.Request;
import proto.Response;
import service.IServices;
import utils.TipProba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GrpcImpl extends GRPCServicesGrpc.GRPCServicesImplBase {
    private static final Logger logger = LogManager.getLogger(GrpcImpl.class.getName());
    private Map<String,StreamObserver<Response>> observerMap=new HashMap<>();
    private IServices services;

    @Override
    public void getAllParticipanti(Request request, StreamObserver<Response> responseObserver) {
        if(request.getRequestType().equals(Request.RequestType.GET_ALL_PARTICIPANTI)){
            logger.info("Request:GET ALL PARTICIPANTI");
            Iterable<Participant> participants = null;
            try{
                 participants=services.findAllParticipanti();
            }
            catch (Exception e){
                responseObserver.onNext(Response.newBuilder().setResponseType(Response.ResponseType.ERROR).build());
                responseObserver.onCompleted();
            }
            Response.Builder builder=Response.newBuilder().setResponseType(Response.ResponseType.SENT_ALL_PARTICIPANTI);
            List<proto.Participant> partProto=StreamSupport.stream(participants.spliterator(),false)
                    .map(GrpcImpl::participantDomainToProto)
                    .collect(Collectors.toList());
            builder.addAllParticipanti(partProto);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void findAllForAProbe(Request request, StreamObserver<Response> responseObserver) {
        if(request.getRequestType().equals(Request.RequestType.FIND_ALL_FOR_A_PROBE)){
            logger.info("Request:GET ALL PARTICIPANTI FOR A PROBE");
            Iterable<Participant> participants = null;
            try{
                participants=services.findAllForAProbe(TipProba.valueOf(request.getProba(0).getTipProba().toString()));
            }
            catch (Exception e){
                responseObserver.onNext(Response.newBuilder().setResponseType(Response.ResponseType.ERROR).build());
                responseObserver.onCompleted();
            }
            Response.Builder builder=Response.newBuilder().setResponseType(Response.ResponseType.SENT_ALL_FOR_A_PROBE);
            List<proto.Participant> partProto=StreamSupport.stream(participants.spliterator(),false)
                    .map(GrpcImpl::participantDomainToProto)
                    .collect(Collectors.toList());
            builder.addAllParticipanti(partProto);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void findArbitru(Request request, StreamObserver<Response> responseObserver) {
        if(request.getRequestType().equals(Request.RequestType.FIND_ARBITRU)){
            logger.info("Request:GET ARBITRU WITH NAME"+request.getArbitru(0).getName());
            Arbitru arbitru=null;
            try{
                arbitru=services.findArbitru(request.getArbitru(0).getName());
            }
            catch (Exception e){
                responseObserver.onNext(Response.newBuilder().setResponseType(Response.ResponseType.ERROR).build());
                responseObserver.onCompleted();
            }
            Response.Builder builder=Response.newBuilder().setResponseType(Response.ResponseType.SENT_ARBITRU);
            proto.Arbitru arbitru1=proto.Arbitru.newBuilder().setName(arbitru.getName()).setPassword(arbitru.getPassword()).build();
            List<proto.Arbitru> arbitruList=new ArrayList<>();
            arbitruList.add(arbitru1);
            builder.addAllArbitru(arbitruList);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getPunctajTotalParticipant(Request request, StreamObserver<Response> responseObserver) {
        if(request.getRequestType().equals(Request.RequestType.GET_PUNCTAJ_TOTAL_PARTICIPANT)){
            logger.info("Request:GET PUNCTAJ TOTAL FOR"+request.getParticipant(0).getName());
            double punctaj=0;
            try{
                punctaj=services.getPunctajTotalParticipant(request.getParticipant(0).getName());
            }
            catch (Exception e){
                responseObserver.onNext(Response.newBuilder().setResponseType(Response.ResponseType.ERROR).build());
                responseObserver.onCompleted();
            }
            Response.Builder builder=Response.newBuilder().setResponseType(Response.ResponseType.SENT_PUNCTAJ_TOTAL_PARTICIPANT);
            List<Double> dlist=new ArrayList<>();
            dlist.add(punctaj);
            builder.addAllTotal(dlist);
           // builder.addAllParticipanti(partProto);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getPunctajParticipantPentruOProba(Request request, StreamObserver<Response> responseObserver) {
        if(request.getRequestType().equals(Request.RequestType.GET_PUNCTAJ_PARTICIPANT_PROBA)){
            logger.info("Request:GET PUNCTAJ PENTRU O PROBA FOR"+request.getParticipant(0).getName());
            double punctaj=0;
            try{
                punctaj=services.getPunctajParticipantPentruOProba(request.getParticipant(0).getName(),request.getProba(0).getTipProba().toString());
            }
            catch (Exception e){
                responseObserver.onNext(Response.newBuilder().setResponseType(Response.ResponseType.ERROR).build());
                responseObserver.onCompleted();
            }
            Response.Builder builder=Response.newBuilder().setResponseType(Response.ResponseType.SENT_PUNCTAJ_PARTICIPANT_PROBA);
            List<Double> dlist=new ArrayList<>();
            dlist.add(punctaj);
            builder.addAllTotal(dlist);
            // builder.addAllParticipanti(partProto);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }

    public GrpcImpl(IServices services) {
        this.services = services;
    }

    @Override
    public void addRezultat(Request request, StreamObserver<Response> responseObserver){
        if(request.getRequestType().equals(Request.RequestType.ADAUGARE_REZULTAT)){
            logger.info("Request: ADAUGARE_REZULTAT");

            try{
                services.addRezultat(request.getParticipant(0).getName(),request.getArbitru(0).getName(), TipProba.valueOf(request.getProba(0).getTipProba().toString()),request.getRezultat(0).getNumarPuncte());

            }
            catch (Exception e){
                responseObserver.onNext(Response.newBuilder().setResponseType(Response.ResponseType.ERROR).build());
                responseObserver.onCompleted();
                return;
            }
            logger.info("REZULTAT: "+request.getRezultat(0).toString()+" ADAUGAT");
            responseObserver.onNext(Response.newBuilder().setResponseType(Response.ResponseType.OK).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void login(Request request, StreamObserver<Response> responseObserver) {
        if(request.getRequestType().equals(Request.RequestType.LOGIN)){
            logger.info("ARBITRU: "+request.getArbitru(0).getName()+"TRIES TO LOGIN");
            Arbitru arbitru=new Arbitru(
                    request.getArbitru(0).getName(),
                    request.getArbitru(0).getPassword()
            );
            try{
                this.services.login(arbitru,null);
                observerMap.put(request.getArbitru(0).getName(),responseObserver);
            }
            catch (Exception e){
                responseObserver.onNext(Response.newBuilder().setResponseType(Response.ResponseType.ERROR).build());
                responseObserver.onCompleted();
                return;
            }
            logger.info("LOGIN SUCCESSFUL FOR: "+request.getArbitru(0).getName());
            responseObserver.onNext(Response.newBuilder().setResponseType(Response.ResponseType.OK).build());
            responseObserver.onCompleted();
        }
    }
    @Override
    public void getAllRezultate(Request request, StreamObserver<Response> responseObserver) {
        //todo
    }
    public void notifyObservers(Response.ResponseType responseType){
        Response response=Response.newBuilder().setResponseType(responseType).build();
        observerMap.values().forEach(observer->{
            observer.onNext(response);
        });
    }
    static proto.Participant participantDomainToProto(Participant participant){
        return proto.Participant.newBuilder()
        .setName(participant.getNume()).build();
    }
    static proto.Rezultat rezultatDomainToProto(Rezultat rezultat){
        return proto.Rezultat.newBuilder()
                .setIdProba(rezultat.getIdProba())
                .setNumarPuncte(rezultat.getNumarPuncte())
                .setNumeParticipant(rezultat.getNumeParticipant())
                .build();
    }
}
