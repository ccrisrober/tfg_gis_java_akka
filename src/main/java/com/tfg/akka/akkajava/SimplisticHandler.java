// Copyright (c) 2015, maldicion069 (Cristian Rodríguez) <ccrisrober@gmail.con>
//
// Permission to use, copy, modify, and/or distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.package com.example

package com.tfg.akka.akkajava;

import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.io.TcpMessage;
import akka.util.ByteString;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class SimplisticHandler extends UntypedActor {

    public SimplisticHandler(int id, Tcp.Connected conn, boolean isGame) {
        this.id = id;
        this.conn = conn;
        this.isGame = isGame;
        parser = new JSONParser();
    }
    protected int id;
    protected Tcp.Connected conn;
    protected boolean isGame;

    protected static JSONParser parser;

    protected float getFloatFromObject(Object o) throws Exception {
        return Float.parseFloat(o.toString());
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Received) {
            final ByteString data = ((Received) msg).data();
            String str = new String(data.toArray(), "UTF-8");
            str = str.replaceAll("(\\r|\\n)", "");
            //str = str.concat(System.getProperty("line.separator"));
            System.out.println("RECIBO " + str + " -- " + id);
            JSONObject json = null;
            try {
                json = (JSONObject) parser.parse(str);
            } catch (Exception e) {
                //System.out.println("JSON " + e + " -- " + id);
            }
            while(json == null) {
                System.out.println(id + "=> ES NULL");
                try {
                    json = (JSONObject) parser.parse(str);
                } catch (Exception e) {
                    System.out.println("JSON " + e + " -- " + id);
                }
            }
            Object aux = json.get("Action");
            if (aux != null) {
                String action = (String) aux;
                switch (action) {
                    case "initWName":
                        try {
                            ObjectUser ou = new ObjectUser(id, 5 * 64, 5 * 64);
                            JSONObject sendNewMap = new JSONObject();
                            sendNewMap.put("Action", "sendMap");
                            sendNewMap.put("Map", SingletonAgent.map.get(ou.getMap()));
                            sendNewMap.put("X", ou.getPosX());
                            sendNewMap.put("Y", ou.getPosY());
                            sendNewMap.put("Id", ou.getId());
                            sendNewMap.put("Users", SingletonAgent.positions);
                            System.out.println(sendNewMap.toJSONString());
                            SingletonAgent.positions.put(id + "", ou);

                            getSender().tell(TcpMessage.write(
                                    new ByteString.ByteString1C(
                                            (sendNewMap.toJSONString() + "\n").getBytes())), getSelf());

                            JSONObject newUserToAnotherClients = new JSONObject();
                            newUserToAnotherClients.put("Action", "new");
                            newUserToAnotherClients.put("Id", id);
                            newUserToAnotherClients.put("PosX", ou.getPosX());
                            newUserToAnotherClients.put("PosY", ou.getPosY());
                            if (isGame) {
                                ByteString bs = new ByteString.ByteString1(newUserToAnotherClients.toJSONString().getBytes());
                                SingletonAgent.clients.parallelStream().forEach(sk -> {
                                    if (sk.id != this.id && !sk.conn.isTerminated()) {
                                        System.out.println(new String(bs.toArray()));
                                        sk.conn.tell(TcpMessage.write(bs), getSelf());
                                    }
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("INIT " + e);
                        }
                        break;
                    case "move":
                        try {
                            ObjectUser user = SingletonAgent.positions.get(this.id + "");
                            user.setPosX(getFloatFromObject(((JSONObject) json.get("Pos")).get("X")));
                            user.setPosY(getFloatFromObject(((JSONObject) json.get("Pos")).get("X")));
                            SingletonAgent.positions.put(this.id + "", user);
                            if (isGame) {
                                SingletonAgent.clients.parallelStream().forEach(sk -> {
                                    if (sk.id != this.id && !sk.conn.isTerminated()) {
                                        sk.conn.tell(TcpMessage.write(data), getSelf());
                                    }
                                });
                            } else {
                                getSender().tell(TcpMessage.write(data), getSelf());
                            }
                        } catch (Exception e) {
                            System.out.println("MOVE " + e);
                        }
                        break;
                    case "exit":
                        try {
                            SingletonAgent.positions.remove(this.id + "");
                            //SingletonAgent.clients.remove(this.id);
                            JSONObject exitToClient = new JSONObject();
                            exitToClient.put("Action", "exit");
                            if (isGame) {
                                exitToClient.put("Id", json.get("Id"));
                                SingletonAgent.clients.parallelStream().forEach(sk -> {
                                    if (sk.id != this.id && !sk.conn.isTerminated()) {
                                        sk.conn.tell(TcpMessage.write(data), getSelf());
                                    }
                                });
                            } else {
                                exitToClient.put("Id", "Me");
                                getSender().tell(TcpMessage.write(
                                        new ByteString.ByteString1C(
                                                (exitToClient.toJSONString() + "\n").getBytes())), getSelf());
                                getContext().stop(getSelf());
                            }
                        } catch (Exception e) {
                            System.out.println("EXIT " + e);
                        }
                        break;
                }
            }

            //getSender().tell(TcpMessage.write(data), getSelf());
        } else if (msg instanceof ConnectionClosed) {
            //Thread.sleep(1000);
            getContext().stop(getSelf());
        }
    }
}
