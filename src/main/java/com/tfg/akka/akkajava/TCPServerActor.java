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

import java.net.InetSocketAddress;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.Tcp.Bound;
import akka.io.Tcp.CommandFailed;
import akka.io.Tcp.Connected;
import akka.io.TcpMessage;

//http://doc.akka.io/docs/akka/snapshot/java/io-tcp.html
public class TCPServerActor extends UntypedActor {

    final ActorRef manager;
    protected int port;
    protected boolean isGame;

    public static Props props(ActorRef manager, int port, boolean isGame) {
        return Props.create(TCPServerActor.class, manager, port, isGame);
    }

    public TCPServerActor(ActorRef manager, int port, boolean isGame) {
        this.manager = manager;
        this.port = port;
        this.isGame = isGame;
        System.out.println(this.getSelf().path());
    }

    @Override
    public void preStart() throws Exception {
        final ActorRef tcp = Tcp.get(getContext().system()).manager();
        tcp.tell(TcpMessage.bind(getSelf(),
                new InetSocketAddress("0.0.0.0", port), 100), getSelf());
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Bound) {
            manager.tell(msg, getSelf());

        } else if (msg instanceof CommandFailed) {
            getContext().stop(getSelf());

        } else if (msg instanceof Connected) {
            final Connected conn = (Connected) msg;
            manager.tell(conn, getSelf());
            final ActorRef handler = getContext().actorOf(
                    Props.create(SimplisticHandler.class, conn.remoteAddress().getPort(), conn, this.isGame)
            );
            SingletonAgent.clients.add(new SingletonAgent.Client(handler, conn.remoteAddress().getPort()));
            getSender().tell(TcpMessage.register(handler), getSelf());
        }
    }
}
