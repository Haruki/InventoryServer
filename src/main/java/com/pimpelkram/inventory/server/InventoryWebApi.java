
package com.pimpelkram.inventory.server;

import spark.Request;
import spark.Response;

public class InventoryWebApi {

    static String getItems(Request request, Response response) {
        return "hallooo moep lalala ALLE";
    }

    static String getItem(Request request, Response response) {
        return "hallooo moep lalala" + request.params(":id");
    }
}
