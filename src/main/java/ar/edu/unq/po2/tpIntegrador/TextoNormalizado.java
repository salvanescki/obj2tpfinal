package ar.edu.unq.po2.tpIntegrador;
import java.text.Normalizer;

public class TextoNormalizado {

    public static String normalizarTexto(String texto) {
        if (texto == null) return null;


        texto = texto.toLowerCase();

        texto = texto.trim().replaceAll("\\s+", " ");

        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return texto;
    }
}
