/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auxiliares;

import java.util.List;

/**
 *
 * @author jeja1
 */
public final class Utilidades {

    private static int _Codigo = 0;

    public static String TablaHTML(List pDatos) {
        StringBuilder html = new StringBuilder("<!DOCTYPE html>"
                + "<html><table style=\"width:100%\">");
        html.append("<tr>");
        for (int i = 0; i < ((List) pDatos.get(0)).size(); i++) {
            html.append("<th>" + ((List) pDatos.get(0)).get(i).toString() + "</th>");
        }
        html.append("</tr>");
        for (int i = 1; i < pDatos.size(); i++) {
            html.append("<tr>");
            for (int j = 0; j < ((List) pDatos.get(i)).size(); j++) {
                html.append("<td>" + ((List) pDatos.get(i)).get(j).toString() + "</td>");
            }
            html.append("</tr>");
        }
        html.append("</table></html>");
        return html.toString();
    }

    public static int generadorDeCodigos() {
        int saliente = _Codigo;
        _Codigo = +101;
        if (_Codigo > 9999) {
            _Codigo -= 9999;
        }
        return saliente;
    }
}
