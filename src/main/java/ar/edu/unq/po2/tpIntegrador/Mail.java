package ar.edu.unq.po2.tpIntegrador;

public class Mail {
    private String de;
    private String a;
    private String asunto;
    private String mensaje;
    private Object adjunto;

    public Mail(String de, String a, String asunto, String mensaje) {
        this.de = de;
        this.a = a;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    public void agregarArchivoAdjunto(Object o){
        adjunto = o;
    }

    public String getDe() {
        return de;
    }

    public String getA() {
        return a;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public void setA(String a) {
        this.a = a;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getAdjunto() {
        return adjunto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Exitoso envio de mail de: ").append(de)
                .append(". Para: ").append(a)
                .append(". Asunto: ").append(asunto)
                .append(". Mensaje: ").append(mensaje).append(".");
        if (adjunto != null) {
            sb.append(". Archivo adjunto: ").append(adjunto);
        }
        return sb.toString();
    }

    public static String enviar(Mail mail) {
        return mail.toString();
    }
}