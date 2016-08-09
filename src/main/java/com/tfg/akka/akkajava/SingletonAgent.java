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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tfg.akka.akkajava;

import akka.actor.ActorRef;
import akka.io.Tcp;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Cristian
 */
public class SingletonAgent {

    public static class Client {

        public Client(ActorRef conn, int id) {
            this.conn = conn;
            this.id = id;
        }

        public ActorRef getConn() {
            return conn;
        }

        public void setConn(ActorRef conn) {
            this.conn = conn;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        protected ActorRef conn;
        protected int id;

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 83 * hash + this.id;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Client other = (Client) obj;
            if (this.id != other.id) {
                return false;
            }
            return true;
        }
        
    }

    public static List<Client> clients = new LinkedList<>();
    public static java.util.Map<String, ObjectUser> positions = new ConcurrentHashMap<>();
    public static List<Map> map = new LinkedList<>();
    //public static List<Client> sockets = new LinkedList<>();
    public static java.util.Map<String, KeyObject> keysMap = new ConcurrentHashMap<>();

    static {
        keysMap.put("Red", new KeyObject(1, 5 * 64, 5 * 64, "Red"));
        keysMap.put("Blue", new KeyObject(2, 6 * 64, 5 * 64, "Blue"));
        keysMap.put("Yellow", new KeyObject(3, 7 * 64, 5 * 64, "Yellow"));
        keysMap.put("Green", new KeyObject(4, 8 * 64, 5 * 64, "Green"));
        map.add(new Map(1,
                "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 4, 0, 0, 0, 1,"
                + "1, 1, 0, 0, 0, 0, 0, 0, 0, 5, 5, 7, 5, 5, 5, 5, 1, 1, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 5, 5, 1,"
                + "1, 1, 0, 0, 4, 6, 5, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 4, 0, 5, 5, 5, 0, 8, 8, 8, 0, 0, 1,"
                + "1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 4, 0, 0, 0, 1, 1, 0, 5, 5, 5, 0, 0, 0, 8, 8, 8, 4, 0, 1,"
                + "1, 0, 1, 0, 0, 0, 0, 4, 0, 0, 1, 1, 1, 1, 4, 0, 0, 0, 1, 0, 5, 0, 4, 4, 0, 0, 8, 8, 8, 1, 4, 1,"
                + "4, 0, 1, 0, 0, 0, 0, 4, 4, 0, 1, 1, 1, 1, 1, 1, 4, 0, 1, 0, 5, 0, 4, 4, 4, 0, 8, 8, 8, 1, 1, 1,"
                + "1, 0, 1, 0, 0, 4, 4, 4, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 5, 4, 4, 4, 0, 0, 0, 0, 1, 1, 1, 1,"
                + "1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 4, 0, 0, 0, 1,"
                + "1, 1, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5,"
                + "1, 1, 0, 0, 4, 0, 5, 5, 5, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 4, 0, 5, 5, 5, 0, 1, 1, 1, 0, 0, 1,"
                + "1, 1, 1, 0, 5, 5, 5, 0, 0, 0, 1, 1, 1, 4, 0, 0, 0, 1, 1, 0, 5, 5, 5, 0, 0, 0, 1, 1, 1, 4, 0, 1,"
                + "1, 0, 1, 0, 5, 0, 4, 4, 0, 0, 1, 1, 1, 1, 4, 0, 0, 0, 1, 0, 5, 0, 4, 4, 0, 0, 1, 1, 1, 1, 4, 1,"
                + "4, 0, 1, 0, 5, 0, 4, 4, 4, 0, 1, 1, 1, 1, 1, 1, 4, 0, 1, 0, 5, 0, 4, 4, 4, 0, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,"
                + "1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,",
                32, 25, keysMap));
    }
}
