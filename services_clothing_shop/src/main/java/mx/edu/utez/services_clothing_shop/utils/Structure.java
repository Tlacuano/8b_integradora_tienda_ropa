package mx.edu.utez.services_clothing_shop.utils;

public class Structure {
    // These are literally stupid, but I'm going to use them to reduce the amount of technical debt sonarqube is going to tell me
    private static final String MARGIN = "                    margin: 0;";
    private static final String END_BRACKET = "                }";
    private static final String SPACE = "            ";
    private static final String TEXT_ALIGN_CENTER = "                    text-align: center;";
    private static final String END_DIV = "                </div>";

    public static String email(String title, String message, String blodMessage) {
        String structure = "<!DOCTYPE html>" +
                "            <html lang=\"en\">" +
                "            <head>" +
                "                <meta charset=\"UTF-8\">" +
                "                <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "                <title>Código de Verificación</title>" +
                "                <style>" +
                "                /* Estilos generales */" +
                "                body {" +
                "                    font-family: Arial, sans-serif;" +
                "                    line-height: 1.5;" +
                MARGIN +
                "                    padding: 0;" +
                END_BRACKET +
                SPACE +
                "                /* Contenedor principal */" +
                "                .container {" +
                "                    max-width: 600px;" +
                "                    margin: 0 auto;" +
                "                    padding: 20px;" +
                END_BRACKET +
                SPACE +
                "                /* Encabezado */" +
                "                .header {" +
                TEXT_ALIGN_CENTER +
                "                    margin-bottom: 20px;" +
                END_BRACKET +
                SPACE +
                "                .header h1 {" +
                "                    color: #333;  " +
                END_BRACKET +
                SPACE +
                "                /* Código de verificación */" +
                "                .verification-code {" +
                "                    background-color: #f2f2f2;" +
                "                    padding: 10px;" +
                TEXT_ALIGN_CENTER +
                "                    margin-bottom: 20px;" +
                END_BRACKET +
                SPACE +
                "                .verification-code h2 {" +
                "                    color: #333;" +
                "                    font-size: 24px;" +
                MARGIN +
                END_BRACKET +
                SPACE +
                "                .verification-code p {" +
                "                    color: #666;" +
                "                    font-size: 18px;" +
                MARGIN +
                END_BRACKET +
                SPACE +
                "                /* Pie de página */" +
                "                .footer {" +
                "                    background-color: #f2f2f2;" +
                "                    padding: 10px;" +
                TEXT_ALIGN_CENTER +
                END_BRACKET +
                SPACE +
                "                .footer p {" +
                "                    color: #666;" +
                "                    font-size: 14px;" +
                MARGIN +
                END_BRACKET +
                "                </style>" +
                "            </head>" +
                "            <body>" +
                "                <div class=\"container\">" +
                "                <div class=\"header\">" +
                "                    <h1>##TITLE##</h1>" +
                END_DIV +
                SPACE +
                "                <div class=\"verification-code\">" +
                "                    <p>##MESSAGE##</p>" +
                "                    <p><strong>##BLOD_MESSAGE##</strong></p>" +
                END_DIV +
                SPACE +
                "                <div class=\"footer\">" +
                "                    <p>Este correo ha sido generado automáticamente. Por favor, no respondas a este correo.</p>" +
                END_DIV +
                END_DIV +
                "            </body>" +
                "            </html>";

        structure = structure.replace("##TITLE##", title);
        structure = structure.replace("##MESSAGE##", message);
        structure = structure.replace("##BLOD_MESSAGE##", blodMessage);


        return structure;
    }
}
