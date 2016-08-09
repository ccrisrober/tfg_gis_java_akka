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

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.io.IOException;
import java.util.Scanner;

public class ApplicationMain {

    public static void main(String[] args) throws IOException {

        ActorSystem system = ActorSystem.create("MyActorSystem");

        ActorRef managerActor = system.actorOf(ManagerActor.props(), "managerActor");

        Scanner scanner = new Scanner(System.in);
        System.out.println("[S/s] System Mode / [_] Test Mode");
        boolean isGame = false;
        /*String mode = scanner.next().toLowerCase();
        if(mode.compareTo("s") == 0) {
            isGame = true;
        }*/
        
        ActorRef tcpServer = system.actorOf(TCPServerActor.props(managerActor, 8090, isGame), "tcpServer");
        System.out.println("INIT SERVER");
    }

}
