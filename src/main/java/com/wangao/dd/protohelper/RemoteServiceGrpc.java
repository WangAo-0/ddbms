package com.wangao.dd.protohelper;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.11.0)",
    comments = "Source: remotedb.proto")
public final class RemoteServiceGrpc {

  private RemoteServiceGrpc() {}

  public static final String SERVICE_NAME = "RemoteService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.wangao.dd.protohelper.RemoteRequest,
      com.wangao.dd.protohelper.RemoteResponse> METHOD_GET = getGetMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.wangao.dd.protohelper.RemoteRequest,
      com.wangao.dd.protohelper.RemoteResponse> getGetMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.wangao.dd.protohelper.RemoteRequest,
      com.wangao.dd.protohelper.RemoteResponse> getGetMethod() {
    return getGetMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.wangao.dd.protohelper.RemoteRequest,
      com.wangao.dd.protohelper.RemoteResponse> getGetMethodHelper() {
    io.grpc.MethodDescriptor<com.wangao.dd.protohelper.RemoteRequest, com.wangao.dd.protohelper.RemoteResponse> getGetMethod;
    if ((getGetMethod = RemoteServiceGrpc.getGetMethod) == null) {
      synchronized (RemoteServiceGrpc.class) {
        if ((getGetMethod = RemoteServiceGrpc.getGetMethod) == null) {
          RemoteServiceGrpc.getGetMethod = getGetMethod = 
              io.grpc.MethodDescriptor.<com.wangao.dd.protohelper.RemoteRequest, com.wangao.dd.protohelper.RemoteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "RemoteService", "Get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wangao.dd.protohelper.RemoteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wangao.dd.protohelper.RemoteResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RemoteServiceMethodDescriptorSupplier("Get"))
                  .build();
          }
        }
     }
     return getGetMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RemoteServiceStub newStub(io.grpc.Channel channel) {
    return new RemoteServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RemoteServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RemoteServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RemoteServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RemoteServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class RemoteServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void get(com.wangao.dd.protohelper.RemoteRequest request,
        io.grpc.stub.StreamObserver<com.wangao.dd.protohelper.RemoteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.wangao.dd.protohelper.RemoteRequest,
                com.wangao.dd.protohelper.RemoteResponse>(
                  this, METHODID_GET)))
          .build();
    }
  }

  /**
   */
  public static final class RemoteServiceStub extends io.grpc.stub.AbstractStub<RemoteServiceStub> {
    private RemoteServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RemoteServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RemoteServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RemoteServiceStub(channel, callOptions);
    }

    /**
     */
    public void get(com.wangao.dd.protohelper.RemoteRequest request,
        io.grpc.stub.StreamObserver<com.wangao.dd.protohelper.RemoteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RemoteServiceBlockingStub extends io.grpc.stub.AbstractStub<RemoteServiceBlockingStub> {
    private RemoteServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RemoteServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RemoteServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RemoteServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.wangao.dd.protohelper.RemoteResponse get(com.wangao.dd.protohelper.RemoteRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RemoteServiceFutureStub extends io.grpc.stub.AbstractStub<RemoteServiceFutureStub> {
    private RemoteServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RemoteServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RemoteServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RemoteServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wangao.dd.protohelper.RemoteResponse> get(
        com.wangao.dd.protohelper.RemoteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RemoteServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RemoteServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET:
          serviceImpl.get((com.wangao.dd.protohelper.RemoteRequest) request,
              (io.grpc.stub.StreamObserver<com.wangao.dd.protohelper.RemoteResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class RemoteServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RemoteServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.wangao.dd.protohelper.Remotedb.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RemoteService");
    }
  }

  private static final class RemoteServiceFileDescriptorSupplier
      extends RemoteServiceBaseDescriptorSupplier {
    RemoteServiceFileDescriptorSupplier() {}
  }

  private static final class RemoteServiceMethodDescriptorSupplier
      extends RemoteServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RemoteServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RemoteServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RemoteServiceFileDescriptorSupplier())
              .addMethod(getGetMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
