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

import java.util.HashSet;
import java.util.Set;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class ObjectUser implements JSONAware {

    public ObjectUser(int id, float x, float y) {
        this.id = id;
        this.posX = x;
        this.posY = y;
        this.map = 0;
        this.rollDice = 0;
        this.objects = new HashSet<>();
    }
    protected int id;
    protected float posX;
    protected float posY;
    protected int map;
    protected int rollDice;
    protected Set<Integer> objects;

    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    public void addObject(int idx) {
        this.objects.add(idx);
    }

    public void removeObject(int idx) {
        this.objects.remove(idx);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public int getRollDice() {
        return rollDice;
    }

    public void setRollDice(int rollDice) {
        this.rollDice = rollDice;
    }

    public Set<Integer> getObjects() {
        return objects;
    }

    public void setObjects(Set<Integer> objects) {
        this.objects = objects;
    }
    
    

    @Override
    public String toJSONString() {
        StringBuffer sb = new StringBuffer();

        sb.append("{");

        sb.append("\"" + JSONObject.escape("Id") + "\"");
        sb.append(":");
        sb.append(id);
        sb.append(",");

        sb.append("\"" + JSONObject.escape("PosX") + "\"");
        sb.append(":");
        sb.append(posX);
        sb.append(",");

        sb.append("\"" + JSONObject.escape("PosY") + "\"");
        sb.append(":");
        sb.append(posY);

        sb.append("}");

        return sb.toString();
    }
}
