package com.example.lwhenyoucai.mysamplebbs.Emqtt;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.hawtdispatch.Dispatch;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.net.URISyntaxException;

/**
 * Created by lwhenyoucai on 2018/8/3.
 */
public class EmqttClient {

    //Mqtt相关变量
    private final static boolean CLEAN_START = true;
    private final static short KEEP_ALIVE = 1;///30
    private static Topic[] topics = {
            new Topic("App_Connection", QoS.AT_LEAST_ONCE),
            new Topic("Pdf_Synchronous", QoS.AT_LEAST_ONCE)
            //new Topic(ServerUrl.Topic_UserGroupInfo,QoS.AT_LEAST_ONCE),
    };
    private CallbackConnection callbackConnection;
    public final static long RECONNECTION_ATTEMPT_MAX = -1;      //重连的最大次数6次
    public final static long RECONNECTION_DELAY = 1000;          //重连时间间隔1秒
    public final static int SEND_BUFFER_SIZE = 2 * 1024 * 1024; //发送的最大缓冲为2M

    EmqttClient(){

    }

    //Mqtt相关功能------------------------------------------------------------------------------------------------------
    public void connectToServer(String host, String clientId) {
        final MQTT mqtt = new MQTT();
        try {
            mqtt.setHost(host);                             //设置连接主机IP
            mqtt.setCleanSession(CLEAN_START);                            //设置不清楚Session连接
            mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);     //设置最大重连次数
            mqtt.setReconnectDelay(RECONNECTION_DELAY);                 //设置重连间隔时间
            mqtt.setKeepAlive(KEEP_ALIVE);                              //设置心跳时间
            mqtt.setSendBufferSize(SEND_BUFFER_SIZE);                   //设置缓冲大小
            mqtt.setClientId(clientId);                                //设置连接的客户端ID
            /*mqtt.setUserName(USER_NAME);                                //设置连接用户名
            mqtt.setPassword(PASSWORD);
            mqtt.setWillTopic("willTopic");// 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息
            mqtt.setWillMessage("willMessage");// 设置“遗嘱”消息的内容，默认是长度为零的消息
            mqtt.setWillQos(QoS.AT_LEAST_ONCE);// 设置“遗嘱”消息的QoS，默认为QoS.ATMOSTONCE
            mqtt.setWillRetain(false);// 若想要在发布“遗嘱”消息时拥有retain选项，则为true
            mqtt.setVersion("3.1.1");*/

            // 失败重连接设置说明
            mqtt.setConnectAttemptsMax(-1);// 客户端首次连接到服务器时，连接的最大重试次数，超出该次数客户端将返回错误。-1意为无重试上限，默认为-1//原示例:10L
            mqtt.setReconnectAttemptsMax(-1);// 客户端已经连接到服务器，但因某种原因连接断开时的最大重试次数，超出该次数客户端将返回错误。-1意为无重试上限，默认为-1//原示例：3L
            mqtt.setReconnectDelay(10L);// 首次重连接间隔毫秒数，默认为10ms
            mqtt.setReconnectDelayMax(1000L);// 重连接间隔毫秒数，默认为30000ms//原示例：30000L
            mqtt.setReconnectBackOffMultiplier(2);// 设置重连接指数回归。设置为1则停用指数回归，默认为2

            // Socket设置说明
            mqtt.setReceiveBufferSize(65536);// 设置socket接收缓冲区大小，默认为65536（64k）
            mqtt.setSendBufferSize(65536);// 设置socket发送缓冲区大小，默认为65536（64k）
            mqtt.setTrafficClass(8);// 设置发送数据包头的流量类型或服务类型字段，默认为8，意为吞吐量最大化传输

            // 带宽限制设置说明
            mqtt.setMaxReadRate(0);// 设置连接的最大接收速率，单位为bytes/s。默认为0，即无限制
            mqtt.setMaxWriteRate(0);// 设置连接的最大发送速率，单位为bytes/s。默认为0，即无限制

            // 选择消息分发队列
            mqtt.setDispatchQueue(Dispatch.createQueue("foo"));// 若没有调用方法setDispatchQueue，客户端将为连接新建一个队列。如果想实现多个连接使用公用的队列，显式地指定队列是一个非常方便的实现方法

            callbackConnection = mqtt.callbackConnection();

            //重连
            callbackConnection.listener(new Listener() {
                @Override
                public void onConnected() {
                    //连接成功并订阅
                    callbackConnection.subscribe(topics, new Callback<byte[]>() {
                        @Override
                        public void onSuccess(byte[] value) {
                            //订阅成功
                        }
                        @Override
                        public void onFailure(Throwable value) {
                            //订阅失败
                        }
                    });
                }

                @Override
                public void onDisconnected() {
                    //连接断开

                }

                @Override
                public void onPublish(UTF8Buffer topic, Buffer body, Runnable ack) {
                    //接收订阅主题发布的消息
                    ack.run();
                }


                @Override
                public void onFailure(Throwable value) {
                    //连接失败

                }
            });
            //第一次连接
            callbackConnection.connect(new Callback<Void>() {
                @Override
                public void onSuccess(Void value) {

                    callbackConnection.subscribe(topics, new Callback<byte[]>() {
                        @Override
                        public void onSuccess(byte[] value) {
                            //订阅成功
                        }

                        @Override
                        public void onFailure(Throwable value) {
                            //订阅失败
                        }
                    });
                }

                @Override
                public void onFailure(Throwable value) {

                }
            });
        } catch (URISyntaxException e) {
            //抛出异常也算网络连接失败
            e.printStackTrace();
        }
    }


}
