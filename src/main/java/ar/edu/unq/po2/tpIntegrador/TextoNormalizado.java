package ar.edu.unq.po2.tpIntegrador;
import java.text.Normalizer;

public class TextoNormalizado {

    private static void validarTexto(String texto){
        if(texto == null){
            throw new NullPointerException("No fue ingresado correctamente texto, es null");
        }
    }

    public static String normalizarTexto(String texto) {
        validarTexto(texto);

        texto = texto.toUpperCase();
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        texto = texto.replaceAll("[^A-Z]", "");

        return texto;
    }
}
