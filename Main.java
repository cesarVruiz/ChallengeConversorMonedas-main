//Challenge Conversor de Monedas por Cesar Isaac Velazquez Ruiz

import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.Scanner;
import java.net.HttpURLConnection;Challenge
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

class ApiResponse {
    @SerializedName("conversion_rates")
    private Map<String, Double> conversionRates;

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(Map<String, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }
}

public class Main {
    public static void main(String[] args) {
        int opcion = 0;
        double cambio = 0;
        ApiResponse response = null;

        try {
            URL url = new URL("https://v6.exchangerate-api.com/v6/927e21f550d572344230bef8/latest/USD");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();
                System.out.println("Respuesta de la API: " + informationString.toString());

                Gson gson = new Gson();
                try {
                    response = gson.fromJson(informationString.toString(), ApiResponse.class);
                    if (response.getConversionRates() == null) {
                        System.out.println("Conversion rates are null.");
                        return;
                    }
                } catch (JsonSyntaxException e) {
                    System.err.println("Error parsing JSON: " + e.getMessage());
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Menu de Usuario
        Scanner teclado = new Scanner(System.in);
        double cantidad = 0;
        String menu = """
                 \n
                 Creado por : Antonio Richaud \n
                 
                 Bienvenido al Conversor de Monedas \n
                 1 - Dolar -> Peso Mexicano
                 2 - Peso Mexicano -> Dolar
                 3 - Dolar -> Peso Argentino
                 4 - Peso Argentino -> Dolar
                 5 - Dolar -> Peso Colombiano
                 6 - Peso Colombiano -> Dolar 
                 7 - Salir
                """;

        while (opcion != 7) {
            System.out.println(menu);
            while (!teclado.hasNextInt()) {
                System.out.println("Ingrese un número válido para la opción.");
                teclado.next();
            }
            opcion = teclado.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.println("Ingrese la cantidad:");
                while (!teclado.hasNextDouble()) {
                    System.out.println("Ingrese un número válido para la cantidad.");
                    teclado.next(); // Limpiamos el buffer de entrada
                }
                cantidad = teclado.nextDouble();
            }


            if (response != null && response.getConversionRates() != null) {
                switch (opcion) {
                    case 1: // Dólar a Peso Mexicano
                        if (response.getConversionRates().containsKey("MXN")) {
                            cambio = cantidad * response.getConversionRates().get("MXN");
                            System.out.println("La cantidad de Pesos Mexicanos es: $" + cambio);
                        } else {
                            System.out.println("Tasa de cambio a MXN no disponible.");
                        }
                        break;
                    case 2: // Peso Mexicano a Dólar
                        if (response.getConversionRates().containsKey("MXN")) {
                            cambio = cantidad / response.getConversionRates().get("MXN");
                            System.out.println("La cantidad de Dólares es: $" + cambio);
                        } else {
                            System.out.println("Tasa de cambio a MXN no disponible.");
                        }
                        break;
                    case 3: // Dólar a Peso Argentino
                        if (response.getConversionRates().containsKey("ARS")) {
                            cambio = cantidad * response.getConversionRates().get("ARS");
                            System.out.println("La cantidad de Pesos Argentinos es: $" + cambio);
                        } else {
                            System.out.println("Tasa de cambio a ARS no disponible.");
                        }
                        break;
                    case 4: // Peso Argentino a Dólar
                        if (response.getConversionRates().containsKey("ARS")) {
                            cambio = cantidad / response.getConversionRates().get("ARS");
                            System.out.println("La cantidad de Dólares es: $" + cambio);
                        } else {
                            System.out.println("Tasa de cambio a ARS no disponible.");
                        }
                        break;
                    case 5: // Dólar a Peso Colombiano
                        if (response.getConversionRates().containsKey("COP")) {
                            cambio = cantidad * response.getConversionRates().get("COP");
                            System.out.println("La cantidad de Pesos Colombianos es: $" + cambio);
                        } else {
                            System.out.println("Tasa de cambio a COP no disponible.");
                        }
                        break;
                    case 6: // Peso Colombiano a Dólar
                        if (response.getConversionRates().containsKey("COP")) {
                            cambio = cantidad / response.getConversionRates().get("COP");
                            System.out.println("La cantidad de Dólares es: $" + cambio);
                        } else {
                            System.out.println("Tasa de cambio a COP no disponible.");
                        }
                        break;
                    case 7: // Salir del programa
                        System.out.println("Saliendo del programa.");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente de nuevo.");
                        break;
                }
            } else {
                System.out.println("No se pudo obtener las tasas de cambio de la API.");
            }
        }
    }
}