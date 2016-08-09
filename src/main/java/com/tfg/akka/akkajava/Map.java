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

import static com.tfg.akka.akkajava.RealObjects.Objects;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Map implements JSONAware {

    public Map(int id, String mapFields, int w, int h, java.util.Map<String, KeyObject> ks) {
        this.id = id;
        this.mapFields = mapFields;
        this.width = w;
        this.height = h;
        this.keyObjects = ks;
    }

    private int id;
    private String mapFields;
    private int width;
    private int height;
    private java.util.Map<String, KeyObject> keyObjects;

    public KeyObject addKeyObject(int idx, float x, float y) {
        KeyObject obj = RealObjects.Objects.get(idx);
        obj.setPosition(x, y);
        RealObjects.Objects.put(idx, obj);
        keyObjects.put(idx + "", obj);
        return obj;
    }

    public KeyObject removeKey(int idx) {
        keyObjects.remove(idx + "");
        return RealObjects.Objects.get(idx);
    }

    @Override
    public String toString() {
        return "(" + mapFields + ")";
    }

    @Override
    public String toJSONString() {
        StringBuffer sb = new StringBuffer();

        sb.append("{");

        sb.append("\"" + JSONObject.escape("Id") + "\"");
        sb.append(":");
        sb.append(id);
        sb.append(",");

        sb.append("\"" + JSONObject.escape("MapFields") + "\"");
        sb.append(":");
        sb.append("\"" + JSONObject.escape(mapFields) + "\"");
        sb.append(",");

        sb.append("\"" + JSONObject.escape("Width") + "\"");
        sb.append(":");
        sb.append(width);
        sb.append(",");

        sb.append("\"" + JSONObject.escape("Height") + "\"");
        sb.append(":");
        sb.append(height);
        sb.append(",");

        sb.append("\"" + JSONObject.escape("KeyObjects") + "\"");
        sb.append(":");
        sb.append(JSONObject.toJSONString(keyObjects));

        sb.append("}");

        return sb.toString();
    }
}
